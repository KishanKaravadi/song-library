package song_library.app;

public class Song {
	
	private String name;
	private String artist;
	private String album;
	private int year;
	
	public Song() {
		this.setName("");
		this.setArtist("");
		this.setAlbum("");
		this.setYear(2022);
	}
	
	public Song(String name, String artist, String album, int year) {
		this.setName(name);
		this.setArtist(artist);
		this.setAlbum(album);
		this.setYear(year);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the artist
	 */
	public String getArtist() {
		return artist;
	}

	/**
	 * @param artist the artist to set
	 */
	public void setArtist(String artist) {
		this.artist = artist;
	}

	/**
	 * @return the album
	 */
	public String getAlbum() {
		return album;
	}

	/**
	 * @param album the album to set
	 */
	public void setAlbum(String album) {
		this.album = album;
	}

	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}
	
	@Override
	public String toString() {
		return name + ";" + artist + ";"+ album + ";" + year;
		
	}
}
