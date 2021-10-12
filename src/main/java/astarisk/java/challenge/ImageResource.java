package astarisk.java.challenge;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;

import astarisk.java.challenge.models.Album;
import astarisk.java.challenge.models.Image;
import astarisk.java.challenge.services.AlbumService;
import astarisk.java.challenge.services.ImageService;

@Path("/images")
public class ImageResource {
    @Inject
    ImageService imageService;
    @Inject
    AlbumService albumService;

    @POST
    public Image create(Image image) {
        // Keep the implentation as simple as possible.
        // Reject creation if the any of the albumId's passed is invaled.
        if(image.getAlbums() != null) {
            for (Integer albumId : image.getAlbums()) {
                if(albumService.findById(albumId) == null)
                    throw new WebApplicationException(400);
            }
        }

        image = imageService.createEntry(image);
        // Add the imageId to all listed albums.
        for (Integer albumId : image.getAlbums()) {
            Album album = albumService.findById(albumId);
            album.addImage(image.getId());
        }
        return image;
    }

    @PUT
    @Path("/{id}")
    public Image update(@PathParam("id") int id, Image image)  {
        Image oldImage = imageService.findById(id);
        if (oldImage == null)
            throw new WebApplicationException(404);

        // Same rules as create. Return status 400 if any image can't be found.
        if(image.getAlbums() != null) {
            for (Integer albumId : image.getAlbums()) {
                if(albumService.findById(albumId) == null)
                    throw new WebApplicationException(400);
            }
        }

        // Clean up the imageId's belonging to images in the oldAlbum
        for (Integer albumId : oldImage.getAlbums()) {
            Album album = albumService.findById(albumId);
            album.removeImage(oldImage.getId());
        }

        // Add the new imageId to the new albums.
        for (Integer albumId : image.getAlbums()) {
            Album album = albumService.findById(albumId);
            album.addImage(oldImage.getId());
        }

        image.setId(oldImage.getId());
        image = imageService.updateEntry(image);
        
        return image;
    }

    @DELETE
    @Path("/{id}")
    public Image delete(@PathParam("id") int id) {
        Image deleteThis = imageService.deleteEntry(id);
        if(deleteThis == null)
            throw new WebApplicationException(404);

        // Remove this imageId for all albums with it.
        for (Integer albumId : deleteThis.getAlbums()) {
            Album album = albumService.findById(albumId);
            album.removeImage(deleteThis.getId());   
        }
        return deleteThis;
    }

    @GET
    @Path("/{id}")
    public Image getById(@PathParam("id") int id) {
        Image image = imageService.findById(id);

        if(image == null)
            throw new WebApplicationException(404);
        return image;    
    }
    
    @GET
    public ArrayList<Image> list() {
        return imageService.listAll();
    }
}
