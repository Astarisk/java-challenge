package astarisk.java.challenge.models;

public class Product {
    
    private String name;
    private String description;
    private Integer album;

    // I want to treat this more like how I would operate a database entry.
    private int id;

    public Product() {
    }

    public Product(String name, String description, Integer album) {
        this.name = name;
        this.description = description;
        this.album = album;
    }

    public Product(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getAlbum() {
        return album;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAlbum(Integer album) {
        this.album = album;
    }

    public void setId(int id) {
        this.id = id;
    }
}
