import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;


public class Help extends JFrame
{
	public Help(Point p)
	{
		setLayout(new BorderLayout());
		setBounds(p.x, p.y, 500, 500);
		JScrollPane scrollpane;
		JEditorPane editorpane = new JEditorPane();
		editorpane.setContentType("text/plain");
		String tekst = "Scrobbler Logmaker\n\n" +
						"Aplikacja do generowania logów z muzyki ods³uchanej na urz¹dzeniach, które nie posiadaj¹ wbudowanego" +
						" generatora logów.\nAplikacja nie zosta³a stworzona do generacji fa³szywie ods³uchanych utworów. U¿ywaj jej uczciwie!\n\n" +
						"How to:\n" +
						"Musisz znaæ czas rozpoczêcia któregokolwiek z utworów, które przes³ucha³eœ\n\n" +
						"1. Kliknij na Add i wybierz ods³uchane pliki muzyczne. Zalecam wybraæ tylko pliki o rozszerzeniu mp3. " +
						"£adowanie mo¿e zostaæ przerwane podczas próby wczytania np. obrazka lub playlisty.\n" +
						"W tabeli nie ma mo¿liwoœci przestawiania kolejnoœci utworów. Jeœli s³ucha³eœ czegoœ w kolejnoœci losowej, to ¿eby zachowaæ tê kolejnoœæ," +
						" bêdziesz musia³ wczytywaæ utwory pojedynczo w przes³uchanej kolejnoœci.\n" +
						"2. Wpisz datê i czas w formacie\n\t\t dd/MM/rrrr gg:mm:ss\n w komórkê w kolumnie Timestamp odpowiadaj¹c¹ zapamiêtanemu momentowi rozpoczêcia s³uchania utworu.\n" +
						"3. Zaznacz wiersze z utworami, które przes³uchiwa³eœ jeden po drugim bez przerw.\n" +
						"4. Kliknij na Apply Timestamp. Wartoœci w kolumnie Timestamp powinny przyj¹æ 10-cyfrowe dodatnie wartoœci.\n" +
						"5. Kliknij na Make Log. Zapisz plik '.scrobbler.log'.\n" +
						"6. Plik ten mo¿e zostaæ wys³any na serwer Last.fm np. za pomoc¹ witryny dap-scrob: http://scrob.paulstead.com/ \n\n";
		editorpane.setText(tekst);
		scrollpane = new JScrollPane(editorpane);
		getContentPane().add(scrollpane,BorderLayout.CENTER);
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent evt) 
			{
                dispose();
            }
		});
		setVisible(true);
	}
}
