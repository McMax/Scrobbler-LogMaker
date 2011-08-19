import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import mseries.Calendar.MDateSelector;
import mseries.Calendar.MDefaultPullDownConstraints;


public class TablePanel extends JPanel implements MouseListener{
	public static final String[] columnNames = {
		"Artist", "Album", "Title", "Track", "Duration", "Timestamp",""
	};
	
	protected JTable table;
	protected JScrollPane scroller;
	protected ScrobTableModel tableModel;
	SimpleDateFormat epoch;
	
	public TablePanel(){init();}
	
	public void init(){
		tableModel = new ScrobTableModel(columnNames);
		tableModel.addTableModelListener(new TablePanel.TablePanelModelListener());
		table = new JTable();
		table.setModel(tableModel);
		table.setSurrendersFocusOnKeystroke(true);
		table.addMouseListener(this);
		if (!tableModel.hasEmptyRow()){tableModel.addEmptyRow();}
		scroller = new JScrollPane(table);
		table.setPreferredScrollableViewportSize(new java.awt.Dimension(500,300));
		TableColumn hidden = table.getColumnModel().getColumn(ScrobTableModel.HIDDEN_INDEX);
        hidden.setMinWidth(2);
        hidden.setPreferredWidth(2);
        hidden.setMaxWidth(2);
        hidden.setCellRenderer(new InteractiveRenderer(ScrobTableModel.HIDDEN_INDEX));

		setLayout(new BorderLayout());
		add(scroller, BorderLayout.CENTER);
	}
	
	 public void highlightLastRow(int row) {
         int lastrow = tableModel.getRowCount();
         if (row == lastrow - 1) {
             table.setRowSelectionInterval(lastrow - 1, lastrow - 1);
         } else {
             table.setRowSelectionInterval(row + 1, row + 1);
         }

         table.setColumnSelectionInterval(0, 0);
     }


	
	@SuppressWarnings("static-access")
	public void insert(String artist, String album, String title, int track, int duration){
		int curRow = tableModel.getRowCount()-1;
		tableModel.setValueAt(artist, curRow, tableModel.ARTIST_INDEX);
		tableModel.setValueAt(album, curRow, tableModel.ALBUM_INDEX);
		tableModel.setValueAt(title, curRow, tableModel.TITLE_INDEX);
		tableModel.setValueAt(track, curRow,tableModel.TRACK_INDEX);
		tableModel.setValueAt(duration, curRow, tableModel.DURATION_INDEX);
		tableModel.addEmptyRow();
		
	}
	
	public void add_empty()
	{
		tableModel.addEmptyRow();
	}
	
	public void delete(){
		if(table.getSelectedRowCount()>0){
			tableModel.removeRow(table.getSelectedRows(),table.getSelectedRowCount());
		}
		if(table.getRowCount()==0){tableModel.addEmptyRow();}
	}
	
	@SuppressWarnings("static-access")
	public void apply_timestamp(){
		String source;
		epoch = new java.text.SimpleDateFormat ("dd/MM/yyyy HH:mm:ss");
		long unix=0L;
		int i,T,n,tstmp,dur;
		int[] rows;
		for(i=0;i<table.getSelectedRowCount();i++){
			rows = table.getSelectedRows();
			
			if(!(tableModel.getValueAt(rows[i], tableModel.TIMESTAMP_INDEX).toString().trim().equals(""))){
				T=rows[i];
				n=table.getSelectedRowCount()-1;
				source = tableModel.getValueAt(rows[i], tableModel.TIMESTAMP_INDEX).toString();
				try {
					unix = epoch.parse(source).getTime()/1000;
				} 
				catch (ParseException e) 
				{
					//continue;
					//e.printStackTrace();
					JOptionPane.showMessageDialog(Global.okno, "�le wpisany czas. Powinien by� w formacie\ndd/MM/rrrr gg:mm:ss", "B��d", JOptionPane.ERROR_MESSAGE);
					return;
				}
				tableModel.setValueAt(Long.toString(unix), rows[i], tableModel.TIMESTAMP_INDEX);
				for(i=T-1;i>=rows[0];i--){
					tstmp = Integer.parseInt((tableModel.getValueAt(i+1, tableModel.TIMESTAMP_INDEX).toString()));
					dur = Integer.parseInt(tableModel.getValueAt(i, tableModel.DURATION_INDEX).toString());
					tableModel.setValueAt(String.valueOf(tstmp-dur), i, tableModel.TIMESTAMP_INDEX);
				}
				for(i=T+1;i<=rows[n];i++){
					tstmp = Integer.parseInt(tableModel.getValueAt(i-1, tableModel.TIMESTAMP_INDEX).toString());
					dur = Integer.parseInt(tableModel.getValueAt(i-1, tableModel.DURATION_INDEX).toString());
					tableModel.setValueAt(String.valueOf(tstmp+dur), i, tableModel.TIMESTAMP_INDEX);
				}
				break;
			}
		}
	}
	
	public String[][] makefile(){
		int a = tableModel.getRowCount();
		String out[][] = new String[a][6];
		String tmp;
		int i,j;
		for(i=0;i<a;i++){
			for(j=0;j<=tableModel.TIMESTAMP_INDEX;j++){
				tmp = tableModel.getValueAt(i, j).toString();
				if((j==0)&&(tmp.equals(""))) break;
				out[i][j] = tmp;
			}
		}
		return out;
	}
	
	class InteractiveRenderer extends DefaultTableCellRenderer {
         protected int interactiveColumn;

         public InteractiveRenderer(int interactiveColumn) {
             this.interactiveColumn = interactiveColumn;
         }

         public Component getTableCellRendererComponent(JTable table,
            Object value, boolean isSelected, boolean hasFocus, int row,
            int column)
         {
             Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
             if (column == interactiveColumn && hasFocus) {
                 if ((TablePanel.this.tableModel.getRowCount() - 1) == row &&
                    !TablePanel.this.tableModel.hasEmptyRow())
                 {
                	 TablePanel.this.tableModel.addEmptyRow();
                 }

                 highlightLastRow(row);
             }

             return c;
         }
     }


	public class TablePanelModelListener implements TableModelListener{
		public void tableChanged(TableModelEvent arg0) {
			if(arg0.getType() == TableModelEvent.UPDATE){
				int column = arg0.getColumn();
				int row = arg0.getFirstRow();
				//System.out.println("row: " + row + " column: " + column);
				table.setColumnSelectionInterval(column + 1, column + 1);
				table.setRowSelectionInterval(row,row);
			}
			
		}
		
	}
	
	String Popup1(MouseEvent ev)
	{
		Point punkt = ev.getPoint();
		Date data_min = new Date(100,0,0),
			data_max = new Date(System.currentTimeMillis());
		String wyjscie;
		SimpleDateFormat dzien = new SimpleDateFormat("dd/MM/yyyy");
		
		MDateSelector mdate = new MDateSelector(data_min,data_max);
		MDefaultPullDownConstraints c = new MDefaultPullDownConstraints();
		c.firstDay = Calendar.MONDAY;
		mdate.setConstraints(c);
		mdate.show(this, punkt, data_max);
		
		java.util.Date data_wyjsciowa = mdate.getValue();
		
		wyjscie = dzien.format(data_wyjsciowa);
		
		return wyjscie;
	}
	
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent arg0) 
	{
		if(arg0.isPopupTrigger())
		{
			String col_name = "";
			int[] rows;
			int column=-1;
			try
			{
				col_name = table.getColumnName(column = table.getSelectedColumn());
			}
			catch(ArrayIndexOutOfBoundsException ex){}
			
			if((col_name.equals("Artist"))||(col_name.equals("Album")))
			{
				rows = table.getSelectedRows();
				String val = table.getValueAt(rows[0], column).toString();
				
				for(int i=rows[0]; i<rows.length; i++)
					table.setValueAt(val, rows[i], column);
			}
			
			else if(col_name.equals("Track"))
			{
				rows = table.getSelectedRows();
				int val = Integer.parseInt(table.getValueAt(rows[0], column).toString());
				
				for(int i=rows[0], j=0; i<rows.length; i++, j++)
					table.setValueAt(val+j, rows[i], column);
			}
				
			else if(col_name.equals("Track"))
				System.out.println("Track");
			else if(col_name.equals("Timestamp"))
				tableModel.setValueAt(
						Popup1(arg0) + " hh:mm:ss",
						table.rowAtPoint(arg0.getPoint()),
						table.columnAtPoint(arg0.getPoint()));
			
		}
	}
	
}
