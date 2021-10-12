package astarisk.java.challenge.models;

import java.util.ArrayList;

public class Image {
    
    // Changed to Integer from Album to avoid issues with Jackson and bidirectional references.
    private ArrayList<Integer> albums;
    private String title;
    private String description;

    // Treat this more like how a database would operate and assign id's to the entry.
    private int id;

    public Image() {
    }

    public Image(String title, String description) {
        this.albums = new ArrayList<Integer>();
        this.title = title;
        this.description = description;
    }
    public Image(ArrayList<Integer> albums, String title, String description) {
        this.albums = albums;
        this.title = title;
        this.description = description;
    }

    public ArrayList<Integer> getAlbums() {
        return albums;
    }

    public void setAlbums(ArrayList<Integer> albums) {
        this.albums = albums;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void addAlbum(Integer albumId) {
        albums.add(albumId);
    }

    public void removeAlbum(Integer albumId) {
        if(albums.contains(albumId))
            albums.remove(albumId);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
