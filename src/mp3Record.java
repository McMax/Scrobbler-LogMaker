public class mp3Record 
{
	protected String artist;
	protected String album;
	protected String title;
	protected int track;
	protected int duration;
	protected String timestamp;
	
	public mp3Record(){ //pusty kostruktor
		artist = "";
		album = "";
		title = "";
		track = 0;
		duration = 0;
		timestamp = "";		
	}
	
	public mp3Record(String art, String alb, String tit, //pelny konstruktor
			int tr, int dur, String tstmp)
	{
		artist = art;
		album = alb;
		title = tit;
		track = tr;
		duration = dur;
		timestamp = tstmp;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getTrack() {
		return track;
	}

	public void setTrack(int track) {
		this.track = track;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
}
