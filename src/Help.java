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
						"Aplikacja do generowania log�w z muzyki ods�uchanej na urz�dzeniach, kt�re nie posiadaj� wbudowanego" +
						" generatora log�w.\nAplikacja nie zosta�a stworzona do generacji fa�szywie ods�uchanych utwor�w. U�ywaj jej uczciwie!\n\n" +
						"How to:\n" +
						"Musisz zna� czas rozpocz�cia kt�regokolwiek z utwor�w, kt�re przes�ucha�e�\n\n" +
						"1. Kliknij na Add i wybierz ods�uchane pliki muzyczne. Zalecam wybra� tylko pliki o rozszerzeniu mp3. " +
						"�adowanie mo�e zosta� przerwane podczas pr�by wczytania np. obrazka lub playlisty.\n" +
						"W tabeli nie ma mo�liwo�ci przestawiania kolejno�ci utwor�w. Je�li s�ucha�e� czego� w kolejno�ci losowej, to �eby zachowa� t� kolejno��," +
						" b�dziesz musia� wczytywa� utwory pojedynczo w przes�uchanej kolejno�ci.\n" +
						"2. Wpisz dat� i czas w formacie\n\t\t dd/MM/rrrr gg:mm:ss\n w kom�rk� w kolumnie Timestamp odpowiadaj�c� zapami�tanemu momentowi rozpocz�cia s�uchania utworu.\n" +
						"3. Zaznacz wiersze z utworami, kt�re przes�uchiwa�e� jeden po drugim bez przerw.\n" +
						"4. Kliknij na Apply Timestamp. Warto�ci w kolumnie Timestamp powinny przyj�� 10-cyfrowe dodatnie warto�ci.\n" +
						"5. Kliknij na Make Log. Zapisz plik '.scrobbler.log'.\n" +
						"6. Plik ten mo�e zosta� wys�any na serwer Last.fm np. za pomoc� witryny dap-scrob: http://scrob.paulstead.com/ \n\n";
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
