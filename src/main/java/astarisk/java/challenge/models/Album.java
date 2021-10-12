package astarisk.java.challenge.models;

import java.util.ArrayList;

public class Album {
    
    private ArrayList<Integer> images;
    private String description;

    // I want to treat this more like how I would operate a database entry.
    private int id;

    public Album() {
    }

    public Album(ArrayList<Integer> images, String description) {
        this.images = images;
        this.description = description;
    }

    public ArrayList<Integer> getImages() {
        return images;
    }

    public void setImages(ArrayList<Integer> images) {
        this.images = images;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void addImage(Integer imageId) {
        if(images.contains(imageId))
            return;
        images.add(imageId);
    }

    public void removeImage(Integer imageId) {
        if(images.contains(imageId))
            images.remove(imageId);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
