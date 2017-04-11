package com.teamtreehouse.model;

import java.io.*;
import java.util.*;

public class SongBook {

    private List<Song> mSongs;

    public SongBook() {
        mSongs = new ArrayList<Song>();
    }

    public void exportTo(String fileName) {

        try (
                FileOutputStream fos = new FileOutputStream(fileName);
                PrintWriter writer = new PrintWriter(fos);
        ) {

            for (Song song : mSongs) {
                writer.printf("%s|%s|%s%n", song.getArtist(), song.getTitle(), song.getVideoUrl());
            }
        } catch (IOException ioe) {
            System.out.println("\nProblem Saving file:  " + fileName);
            ioe.printStackTrace();
        }
    }

    public void importFrom(String fileName) {

        try (
                FileInputStream fis = new FileInputStream(fileName);
                BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
        ) {
            String line;
            while ((line = reader.readLine()) != null) {

                String[] args = line.split("\\|");
                addSong(new Song(args[0], args[1], args[2]));
            }
        } catch (IOException ioe) {
            System.out.println("\nProblem in loading file:  " + fileName);
            ioe.printStackTrace();
        }
    }


    public void addSong(Song song) {
        mSongs.add(song);
    }

    public int getSongCount() {
        return mSongs.size();
    }


    //FIXME: This method should be cached!!!
    private Map<String, List<Song>> byArtist() {

        Map<String, List<Song>> byArtist = new TreeMap<>();

        // in some new version of java we can use the above instead of

        // HashMap<String, List<song>>();

        // on the right hand side & the compiler will asume or take the parameters from the left hand side

        for (Song song : mSongs) {

            List<Song> artistSong = byArtist.get(song.getArtist());
            if (artistSong == null) {

                artistSong = new ArrayList<>();
                byArtist.put(song.getArtist(), artistSong);
            }
            artistSong.add(song);
        }

        return byArtist;
    }

    public Set<String> getArtist() {

        return byArtist().keySet();
    }

    public List<Song> getAllSongsForArtist(String artistName) {

        List<Song> songs = byArtist().get(artistName);
        songs.sort(new Comparator<Song>() {

                       @Override
                       public int compare(Song song1, Song song2) {

                           if (song1.equals(song2)) {
                               return 0;
                           }
                           return song1.mTitle.compareTo(song2.mTitle);
                       }

                   }
        );
        return songs;
    }

}