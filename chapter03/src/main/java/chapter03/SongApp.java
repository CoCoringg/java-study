package chapter03;

public class SongApp {

	public static void main(String[] args) {
//		Song song = new Song();
//		
//		song.setTitle("좋은날");
//		song.setArtist("아이유");
//		song.setAlbum("Real");
//		song.setComposer("이민수");
//		song.setYear(2010);
//		song.setTrack(3);
		
		Song song = new Song("좋은날", "아이유", "Real", "이민수", 2010, 3);
		
		song.show();
		
		Song song2 = new Song("봄여름가을 겨울", "빅뱅");
		song2.show();
		

	}

}
