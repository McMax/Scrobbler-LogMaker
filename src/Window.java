import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;


public class Window extends JFrame implements MouseListener
{

	JFrame frame = this;
	JFileChooser chooser = new JFileChooser();
	File fileout;
	FileOutputStream fout = null;
	File[] src;
	Action open = new OpenAction();
	Action add_man_action = new AddAction();
	Action eraseaction = new EraseAction();
	Action apply = new ApplyAction();
	Action make = new MakeAction();
	mp3Record record = new mp3Record();
	TablePanel tablepanel = new TablePanel();
	JButton add, help;
	JPopupMenu popup1;
	JMenuItem menuItem;
	
	public Window()
	{
		setTitle("Scrobbler Logmaker v1.4 by McMax");
		setLayout(new BorderLayout());
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(1,4));
		
		popup1 = new JPopupMenu();
		menuItem = new JMenuItem(open);
		menuItem.setText("Add from mp3");
		popup1.add(menuItem);
		menuItem = new JMenuItem(add_man_action);
		menuItem.setText("Add manually");
		popup1.add(menuItem);
				
		JButton erase, apply_tstmp, makefile;
		add = new JButton(add_man_action);
		erase = new JButton(eraseaction);
		apply_tstmp = new JButton(apply);
		makefile = new JButton(make);
		help = new JButton();
		add.setText("Add");
		add.addMouseListener(this);
		erase.setText("Erase");
		apply_tstmp.setText("Apply timestamp");
		makefile.setText("Make Log");
		help.setText("Help");
		help.addMouseListener(this);
		buttons.add(add);
		buttons.add(erase);
		buttons.add(apply_tstmp);
		buttons.add(makefile);
		buttons.add(help);
		
		tablepanel.addMouseListener(this);

		try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            frame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent evt) {
                    System.exit(0);
                }
            });
            frame.getContentPane().add(buttons,BorderLayout.NORTH);
            frame.getContentPane().add(tablepanel,BorderLayout.CENTER);
            frame.pack();
            frame.setVisible(true);
            Global.okno = frame;
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	
	public static void main(String[] args)
	{
		new Window();
	}
	
	class OpenAction extends AbstractAction
	{
		private static final long serialVersionUID = 7753974583652812292L;

		public OpenAction()
		{
			super("Open file");
		}
		
		public void actionPerformed(ActionEvent e)
		{
			chooser.setMultiSelectionEnabled(true);
			
			if(chooser.showOpenDialog(frame)==JFileChooser.APPROVE_OPTION){
				src=chooser.getSelectedFiles();
				int i=0;
				TagGrabber tag = new TagGrabber();
				try{
					while(src[i].exists()){
						record = tag.grabTag(src[i]);
						tablepanel.insert(record.getArtist(), record.getAlbum(),
						record.getTitle(), record.getTrack(), record.getDuration());
						i++;
					}
				}
				catch(ArrayIndexOutOfBoundsException e1){}
				
			}
			
		}
	}
	
	class AddAction extends AbstractAction
	{
		public AddAction()
		{
			super();
		}

		public void actionPerformed(ActionEvent arg0) 
		{
			tablepanel.add_empty();
		}
	}
	
	class EraseAction extends AbstractAction{
		public EraseAction(){
			super("Delete");
		}

		public void actionPerformed(ActionEvent arg0) {
			tablepanel.delete();
			
		}
	}
	
	class ApplyAction extends AbstractAction{
		public ApplyAction(){
			super("Apply Timestamp");
		}

		public void actionPerformed(ActionEvent arg0) {
			tablepanel.apply_timestamp();
			
		}
		
	}
	
	class MakeAction extends AbstractAction{
		
		BufferedWriter outp;
		public MakeAction(){
			super("Make logfile");
		}

		public void actionPerformed(ActionEvent arg0) {
			chooser.setCurrentDirectory(new File("C:\\"));
			chooser.setSelectedFile(new File(".scrobbler.log"));
			if(chooser.showSaveDialog(frame)==JFileChooser.APPROVE_OPTION){
				fileout = chooser.getSelectedFile();
				try {
					outp = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileout),"UTF-8"));
				} catch (FileNotFoundException e1) {//FIXME Przy polskich znakach jest �le
					e1.printStackTrace();
					System.out.print("Brak pliku");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				
				String[][] out;
				String tmp;
				int i=0,j;
				out = tablepanel.makefile();
				try{
					while(true){
						for(j=0;j<6;j++){
							tmp=out[i][j];
							if(tmp.equals("null")) break;
							if(j==5)
								{
									outp.write("L" + '\t' + tmp);
									break;
								}
							outp.write(tmp + '\t');
						}
						outp.write("\n");
						i++;
					}
				}
				catch(ArrayIndexOutOfBoundsException e){}
				catch(NullPointerException e){}
				catch(IOException e){}
				try {
					outp.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				JOptionPane.showMessageDialog(frame, "Log saved");
			}
		}
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
		if(arg0.getComponent()==help)
			new Help(arg0.getPoint());
		
		else if(arg0.getComponent()==add)
			if(arg0.isPopupTrigger())
				popup1.show(arg0.getComponent(), arg0.getX(), arg0.getY());	
	}
}
