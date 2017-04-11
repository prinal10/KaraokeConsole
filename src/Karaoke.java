import com.teamtreehouse.model.KaraokeMachine;
import com.teamtreehouse.model.SongBook;


public class Karaoke {

    public static void main(String[] args) {

        // Song song = new Song("Michael Jackson","Beat It","https://www.youtube.com/watch?v=oRdxUFDoQe0");
        SongBook songBook = new SongBook();
        //System.out.println("\nAdding the "+song);
        //songBook.addSong(song);

        // System.out.println("There are "+songBook.getSongCount() +" in the Song Book.");
        songBook.importFrom("songs.txt");
        KaraokeMachine machine = new KaraokeMachine(songBook);
        machine.run();
        System.out.println("Saving Book.......");
        songBook.exportTo("songs.txt");

    }

}