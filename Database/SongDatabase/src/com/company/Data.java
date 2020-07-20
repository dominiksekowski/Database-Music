package com.company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Data {

    public static final String FILE_NAME = "music.db";
    public static final String CONNECTION_DATABASE = "jdbc:sqlite:C:\\Users\\carow\\Desktop\\music\\" + FILE_NAME;
    private Connection conn;


    public static final String SELECT_ARTISTS = "SELECT * FROM artists WHERE name = ?";
    public static final String SELECT_ALBUMS = "SELECT * FROM albums WHERE name = ?";
    public static final String SELECT_SONGS = "SELECT * FROM songs WHERE title = ?";

    public static final String SELECT_ARTISTS_COMPLETE = "SELECT * FROM artists WHERE name = ";
    public static final String SELECT_ALBUMS_COMPLETE  = "SELECT * FROM albums WHERE name = ";
    public static final String SELECT_SONGS_COMPLETE  = "SELECT * FROM songs WHERE title = ";

    public static final String INSERT_SONGS = "INSERT INTO songs (track, title, album) VALUES (?, ?, ?)";
    public static final String INSERT_ARTISTS = "INSERT INTO artists (name) VALUES (?)";
    public static final String INSERT_ALBUMS = "INSERT INTO albums (name, artist) VALUES (?, ?)";

    public static final String INSERT_SONGS_COMPLETE  = "INSERT INTO songs (track, title, album) VALUES ( ";
    public static final String INSERT_ARTISTS_COMPLETE  = "INSERT INTO artists (name) VALUES ( ";
    public static final String INSERT_ALBUMS_COMPLETE  = "INSERT INTO albums (name, artist) VALUES ( ";


    public boolean open() {
        try {
            conn = DriverManager.getConnection(CONNECTION_DATABASE);
            return true;
        } catch (SQLException e) {
            System.out.println("Database connection failure " + e.getMessage());
            return false;
        }
    }

    public void close() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Unable to close connetion " + e.getMessage());

        }
    }


    public List<Album> selectAlbum(String name) {

        try {
            System.out.println(" \n TABLE ALBUM \n***");

            PreparedStatement pstmtAlbums = conn.prepareStatement(SELECT_ALBUMS);

            pstmtAlbums.setString(1, name);     // set = ?

            ResultSet rs = pstmtAlbums.executeQuery();

            List<Album> albums = new ArrayList<>();

            while (rs.next()) {
                Album album = new Album();
                album.setId(rs.getInt(1));
                album.setName(rs.getString(2));
                album.setArtist(rs.getInt(3));
                albums.add(album);
            }

            pstmtAlbums.close();

            StringBuilder sb = new StringBuilder(SELECT_ALBUMS_COMPLETE);
            sb.append(name);

            System.out.println(" \" " + sb + " \" \n***");

            return albums;

        } catch (SQLException e) {
            System.out.println("Error SELECT Album" + e.getMessage());
            return null;
        }
    }

    public List<Artist> selectArtist(String name) {

        try {
            System.out.println(" \n TABLE ARTIST \n***");

            PreparedStatement pstmtArtists = conn.prepareStatement(SELECT_ARTISTS);

            pstmtArtists.setString(1, name);
            ResultSet rs = pstmtArtists.executeQuery();

            List<Artist> artists = new ArrayList<>();

            while (rs.next()) {
                Artist artist = new Artist();
                artist.setId(rs.getInt(1));
                artist.setName(rs.getString(2));
                artists.add(artist);
            }

            pstmtArtists.close();

            StringBuilder sb = new StringBuilder(SELECT_ARTISTS_COMPLETE);
            sb.append(name);

            System.out.println(" \" " + sb + " \" \n***");

            return artists;

        } catch (SQLException e) {
            System.out.println("Error SELECT Artist" + e.getMessage());
            return null;
        }
    }

    public List<Song> selectSong(String title) {

        try {
            System.out.println(" \n TABLE SONG \n***");

            PreparedStatement pstmtSongs = conn.prepareStatement(SELECT_SONGS);

            pstmtSongs.setString(1, title);
            ResultSet rs = pstmtSongs.executeQuery();

            List<Song> songs = new ArrayList<>();

            while (rs.next()) {
                Song song = new Song();
                song.setId(rs.getInt(1));
                song.setTrack(rs.getInt(2));
                song.setTitle(rs.getString(3));
                song.setAlbum(rs.getInt(4));
                songs.add(song);
            }

            pstmtSongs.close();

            StringBuilder sb = new StringBuilder(SELECT_SONGS_COMPLETE);
            sb.append(title);

            System.out.println(" \" " + sb + " \" \n***");

            return songs;

        } catch (SQLException e) {
            System.out.println("Error SELECT Song" + e.getMessage());
            return null;
        }
    }


    public void insertArtist(String name) throws SQLException {

        PreparedStatement pstmtArtists;

        try {
            conn.setAutoCommit(false);


            pstmtArtists = conn.prepareStatement(INSERT_ARTISTS);
            pstmtArtists.setString(1, name);
            pstmtArtists.executeUpdate();

            StringBuilder sb = new StringBuilder(INSERT_ARTISTS_COMPLETE);
            sb.append(name);
            sb.append(")");

            System.out.println("\n \" " + sb + " \" \n***\n");

            pstmtArtists.close();

        } catch (Exception e) {

            System.out.println("Couldn't inserted ARTIST" + e.getMessage());

            try {
                System.out.println("Rollback ....");
                conn.rollback();
            } catch (SQLException e1) {
                System.out.println("Critical Error " + e1.getMessage());
            }
        } finally {
            try {
                conn.setAutoCommit(true);

            } catch (SQLException e) {
                System.out.println("Error " + e.getMessage());
            }
        }

    }

    public void insertAlbum(String songName, String artistName) throws SQLException {

        PreparedStatement pstmtAlbums;
        PreparedStatement pstmtArtists = conn.prepareStatement(SELECT_ARTISTS);

        pstmtArtists.setString(1, artistName);
        ResultSet rs = pstmtArtists.executeQuery();
        int idArtist = rs.getInt(1);

        try {
            conn.setAutoCommit(false);

            pstmtAlbums = conn.prepareStatement(INSERT_ALBUMS);

            pstmtAlbums.setString(1, songName);
            pstmtAlbums.setInt(2, idArtist);
            pstmtAlbums.executeUpdate();

            StringBuilder sb = new StringBuilder(INSERT_ALBUMS_COMPLETE);
            sb.append(songName);
            sb.append(", ");
            sb.append(idArtist);
            sb.append(")");

            System.out.println("\n \" " + sb + " \" \n***\n");

            pstmtAlbums.close();



        } catch (Exception e) {

            System.out.println("Couldn't inserted ARTIST" + e.getMessage());

            try {
                System.out.println("Rollback ....");
                conn.rollback();
            } catch (SQLException e1) {
                System.out.println("Critical Error " + e1.getMessage());
            }
        } finally {
            try {
                conn.setAutoCommit(true);

            } catch (SQLException e) {
                System.out.println("Error " + e.getMessage());
            }
        }

    }

    public void insertSong(String songName, String albumName, int track) throws SQLException {

        PreparedStatement pstmtSong;
        PreparedStatement pstmtAlbums = conn.prepareStatement(SELECT_ALBUMS);

        pstmtAlbums.setString(1, albumName);
        ResultSet rs = pstmtAlbums.executeQuery();
        int idAlbum = rs.getInt(1);

        try {
            conn.setAutoCommit(false);

            pstmtSong = conn.prepareStatement(INSERT_SONGS);

            pstmtSong.setInt(1, track);
            pstmtSong.setString(2, songName);
            pstmtSong.setInt(3, idAlbum);
            pstmtSong.executeUpdate();

            StringBuilder sb = new StringBuilder(INSERT_SONGS_COMPLETE);
            sb.append(track);
            sb.append(", ");
            sb.append(songName);
            sb.append(", ");
            sb.append(idAlbum);
            sb.append(")");

            System.out.println("\n \" " + sb + " \" \n***\n");

            pstmtSong.close();



        } catch (Exception e) {

            System.out.println("Couldn't inserted ARTIST" + e.getMessage());

            try {
                System.out.println("Rollback ....");
                conn.rollback();
            } catch (SQLException e1) {
                System.out.println("Critical Error " + e1.getMessage());
            }
        } finally {
            try {
                conn.setAutoCommit(true);

            } catch (SQLException e) {
                System.out.println("Error " + e.getMessage());
            }
        }

    }

}




















































































































