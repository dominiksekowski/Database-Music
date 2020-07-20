package com.company;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Data data = new Data();
        if (!data.open()) {
            System.out.println("Unable to open database");
            return;
        }

        selectArtist(data);
        selectAlbum(data);
        selectSong(data);

        insertArtist(data);
        insertAlbum(data);
        insertSong(data);

        data.close();
    }

    public static void selectArtist(Data data) {
        List<Artist> artists = data.selectArtist("Sweet");

        if (artists == null) {
            System.out.println("No artists!");
            return;
        }

        for (Artist artist : artists) {
            System.out.println("ID = " + artist.getId() + " " + "| Name = " + artist.getName());
        }
    }

    public static void selectSong(Data data) {

        List<Song> songs = data.selectSong("Grace");

        if (songs == null) {
            System.out.println("No Songs!");
            return;
        }

        for (Song song : songs) {
            System.out.println("ID = " + song.getId() + " " + "| Track = " + song.getTrack() + "| Title = " + song.getTitle() + ", Album = " + song.getAlbum());
        }
    }

    public static void selectAlbum(Data data) {

        List<Album> albums = data.selectAlbum("Grace");

        if (albums == null) {
            System.out.println("No Album!");
            return;
        }

        for (Album album : albums) {
            System.out.println("ID = " + album.getId() + " " + "| Name = " + album.getName() + "| Artist = " + album.getArtist());
        }
    }

    public static void insertArtist(Data data) {

        try {
            data.insertArtist("Dominik");
        } catch (Exception e) {
            System.out.println("Error");
        }

    }

    public static void insertSong(Data data) {
        try {
            data.insertSong("Title", "Song", 666);
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    public static void insertAlbum(Data data) {
        try {
            data.insertAlbum("Song", "Dominik");
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

}


