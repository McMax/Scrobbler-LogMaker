import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import de.vdheide.mp3.FrameDamagedException;
import de.vdheide.mp3.ID3v2DecompressionException;
import de.vdheide.mp3.ID3v2IllegalVersionException;
import de.vdheide.mp3.ID3v2WrongCRCException;
import de.vdheide.mp3.MP3File;
import de.vdheide.mp3.NoMP3FrameException;
import de.vdheide.mp3.TagContent;

public class TagGrabber extends mp3Record {
	
	protected MP3File mp3file;
	protected TagContent tcartist,tcalbum,tctitle,tctrack;
	protected int tcduration;
	protected mp3Record id3;
	
	public TagGrabber(){
		new mp3Record();
	}
	
	public TagGrabber(File srcfile){
		try {
			mp3file = new MP3File(srcfile.getParent(), srcfile.getName());
			tcartist = mp3file.getArtist();
			tcalbum = mp3file.getAlbum();
			tctitle = mp3file.getTitle();
			tctrack = mp3file.getTrack();
			tcduration = (int)mp3file.getLength();
			
		} 
		catch (ID3v2WrongCRCException e) {blad("Wrong CRC");}
		catch (ID3v2DecompressionException e) {blad("Decompression");}
		catch (ID3v2IllegalVersionException e) {blad("IllegalVersion");}
		catch (IOException e) {blad("IO Error");}
		catch (NoMP3FrameException e) {blad("NoMp3Frame");}
		catch (FrameDamagedException e){blad("Frame Damaged");}
		
		id3 = new mp3Record(tcartist.getTextContent(), 
				tcalbum.getTextContent(),tctitle.getTextContent(),
				Integer.parseInt(tctrack.getTextContent()),
				tcduration,"");
	}
	
	public mp3Record grabTag(File srcfile){
		try {
			mp3file = new MP3File(srcfile.getParent(), srcfile.getName());
			artist = mp3file.getArtist().getTextContent();
			album = mp3file.getAlbum().getTextContent();
			title = mp3file.getTitle().getTextContent();
			track = Integer.parseInt(mp3file.getTrack().getTextContent());
			duration = (int)mp3file.getLength();
			
		} 
		catch (ID3v2WrongCRCException e) {blad("WrongCRC");}
		catch (ID3v2DecompressionException e) {blad("Decompression");}
		catch (ID3v2IllegalVersionException e) {blad("IllegalVersion");}
		catch (IOException e) {blad("IO Error");}
		catch (NoMP3FrameException e) {blad("NoMp3Frame");}
		catch (FrameDamagedException e){blad("Frame Damaged");}
		
		return (mp3Record)this;
	}
	
	public mp3Record getid3(){return id3;}
	
	void blad(String tekst)
	{
		JOptionPane.showMessageDialog(Global.okno, "B³¹d:\n" + tekst, "B³¹d", JOptionPane.ERROR_MESSAGE);
	}
}
