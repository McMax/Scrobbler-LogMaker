import java.util.Vector;
import javax.swing.table.AbstractTableModel;


public class ScrobTableModel extends AbstractTableModel {
	public static final int ARTIST_INDEX = 0;
	public static final int ALBUM_INDEX = 1;
	public static final int TITLE_INDEX = 2;
	public static final int TRACK_INDEX = 3;
	public static final int DURATION_INDEX = 4;
	public static final int TIMESTAMP_INDEX = 5;
	public static final int HIDDEN_INDEX = 6;
	
	protected String[] columnNames;
	protected Vector dataVector;
	
	public ScrobTableModel(String[] columnNames){
		this.columnNames = columnNames;
		dataVector = new Vector();
	}
	
	public String getColumnName(int column){return columnNames[column];}
	
	public boolean isCellEditable(int row, int column){
		if(column == HIDDEN_INDEX) return false;
		else return true;
		}

	public int getColumnCount(){return columnNames.length;}
	
	public int getRowCount() {return dataVector.size();}

	public Object getValueAt(int rowIndex, int columnIndex){
		mp3Record record = (mp3Record)dataVector.get(rowIndex);
		switch(columnIndex){
		case ARTIST_INDEX:
			return record.getArtist();
		case ALBUM_INDEX:
			return record.getAlbum();
		case TITLE_INDEX:
			return record.getTitle();
		case TRACK_INDEX:
			return record.getTrack();
		case DURATION_INDEX:
			return record.getDuration();
		case TIMESTAMP_INDEX:
			return record.getTimestamp();
		default:
			return new Object();
		}
	}
	
	public void setValueAt(Object value, int rowIndex, int columnIndex){
		mp3Record record = (mp3Record)dataVector.get(rowIndex);
		switch(columnIndex){
		case ARTIST_INDEX:
			record.setArtist((String)value);
			break;
		case ALBUM_INDEX:
			record.setAlbum((String)value);
			break;
		case TITLE_INDEX:
			record.setTitle((String)value);
			break;
		case TRACK_INDEX:
			record.setTrack(Integer.parseInt(value.toString()));
			break;
		case DURATION_INDEX:
			record.setDuration(Integer.parseInt(value.toString()));
			break;
		case TIMESTAMP_INDEX:
			record.setTimestamp((String)value);
			break;
		default:
			System.out.println("Zly indeks");			
		}
		fireTableCellUpdated(rowIndex,columnIndex);
	}
	
	public boolean hasEmptyRow(){
		if(dataVector.size()==0) return false;
		
		mp3Record record = (mp3Record)dataVector.get(dataVector.size()-1);
		if(record.getArtist().trim().equals("") &&
			record.getAlbum().trim().equals("") &&
			record.getTitle().trim().equals("") &&
			(record.getTrack() == 0) && (record.getDuration() == 0) &&
			record.getTimestamp().trim().equals("")) return true;
		else return false;
	}
	
	public void addEmptyRow(){
		dataVector.add(new mp3Record());
		fireTableRowsInserted(dataVector.size()-1, dataVector.size()-1);
	}
	
	public void removeRow(int[] rows, int rowcount){
		int i;
		for(i=0;i<rowcount;i++){
			dataVector.removeElementAt(rows[i]-i);
			//fireTableRowsDeleted(rows[i],rows[i]);
		}
		fireTableRowsDeleted(rows[0],rows[i-1]);
	}
	
}
