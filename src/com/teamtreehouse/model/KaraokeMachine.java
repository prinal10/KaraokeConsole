package com.teamtreehouse.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class KaraokeMachine {

    private SongBook mSongBook;
    private BufferedReader mReader;
    private Map<String, String> mMenu;
    private Queue<SongRequest> mSongRequestQueue;


    public KaraokeMachine(SongBook songBook) {

        mSongBook = songBook;
        mReader = new BufferedReader(new InputStreamReader(System.in));
        mSongRequestQueue = new ArrayDeque<SongRequest>();
        mMenu = new HashMap<String, String>();
        mMenu.put("add", "Add a new song to Song Book");
        mMenu.put("choose", "Choose your song to sing !!!");
        mMenu.put("play", "Play the next song in the Queue !!");
        mMenu.put("quit", "Give up. Exit the program");
    }

    private String promptAction() throws IOException {

        System.out.println("\n\nThera are: " + mSongBook.getSongCount() + " songs available & " + mSongRequestQueue.size() + " are in the queue. Your Options are: \n");

        for (Map.Entry<String, String> option : mMenu.entrySet()) {

            System.out.println(option.getKey() + " -------> " + option.getValue());
        }

        System.out.print("\n\nWhat do you want to do ? ");
        String choice = mReader.readLine();
        return choice.trim().toLowerCase();
    }


    public void run() {

        String choice = "";
        do {

            try {

                choice = promptAction();
                switch (choice) {

                    case "add":
                        Song song = promptNewSong();
                        mSongBook.addSong(song);
                        System.out.println("\"" + song + "\"  is added !!!!");
                        break;

                    case "choose":
                        String singerName = promptForSingerName();
                        String artist = promptArtist();
                        Song artistSong = promptSongForArtist(artist);
                        SongRequest songRequest = new SongRequest(singerName, artistSong);
                        if (mSongRequestQueue.contains(songRequest)) {
                            System.out.println("\n\nWhoops  " +singerName +" already requested for the : " +artistSong );
                            break;
                        }
                        mSongRequestQueue.add(songRequest);
                        System.out.println("\n\nYou choose : " + artistSong);
                        break;

                    case "play":
                        playNext();
                        break;

                    case "quit":
                        System.out.println("\nThanks for playing!!! ");
                        break;

                    default:
                        System.out.println("\nInvalid choice: " + choice + " Please try again !!\n\n ");

                }
            } catch (IOException ioe) {
                System.out.println("Problem with Input");
                ioe.printStackTrace();
            }
        }
        while (!choice.equals("quit"));
    }

    private String promptForSingerName() throws IOException {

        System.out.print("Enter the Singer Name: ");
        return mReader.readLine();

    }

    private Song promptNewSong() throws IOException {

        System.out.print("\nEnter the Artist Name : ");
        String artist = mReader.readLine();
        System.out.print("\nEnter the song Title : ");
        String title = mReader.readLine();
        System.out.print("\nEnter the Karaoke Video Url : ");
        String videoUrl = mReader.readLine();

        return new Song(artist, title, videoUrl);
    }


    private int promptForIndex(List<String> options) throws IOException {

        int counter = 1;
        for (String option : options) {

            System.out.println(counter + ". " + option + "\n");
            counter++;

        }
        System.out.print("Your choice is : ");
        String optionAsString = mReader.readLine();
        int choice = Integer.parseInt(optionAsString.trim());
        return choice - 1;
    }

    private String promptArtist() throws IOException {

        System.out.print("\nThe Availabe Artist are : \n\n");
        List<String> artists = new ArrayList<>(mSongBook.getArtist());
        int index = promptForIndex(artists);
        return artists.get(index);
    }

    private Song promptSongForArtist(String artist) throws IOException {

        List<Song> songs = mSongBook.getAllSongsForArtist(artist);
        List<String> songTitles = new ArrayList<>();

        for (Song song : songs) {

            //String title = song.getTitle();
            songTitles.add(song.getTitle());
        }
        System.out.println("\n\nThe Available songs for : " + artist + "\n");
        int index = promptForIndex(songTitles);

        return songs.get(index);
    }

    public void playNext() {

        SongRequest songRequest = mSongRequestQueue.poll();
        if (songRequest == null) {
            System.out.println("\n\nSorry no songRequest in the queue. Please choose a songRequest from menu to play !!!");
        } else {

            Song song = songRequest.getSong();
            System.out.println("\n\nReady "+songRequest.getSingerName() +" Open \"" + song.getVideoUrl() + "\" to play the songRequest \" " + song.getTitle() + "\" by \"" + song.getArtist() + "\"");
        }
    }

}