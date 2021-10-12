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

@Path("/albums")
public class AlbumResource {
    @Inject
    AlbumService albumService;
    @Inject
    ImageService imageService;

    @POST
    public Album create(Album album) {
        // Keep the implentation as simple as possible.
        // Don't create the album if any ImageId is invalid. This will be a strict rule.
        // No creation of an Image should be done here.

        if(album.getImages() != null) {
            for (Integer imageId : album.getImages()) {
                if(imageService.findById(imageId) == null)
                    throw new WebApplicationException(400);
            }
        }
        album = albumService.createEntry(album);
        // Add the albumId to all listed images.
        for (Integer imageId : album.getImages()) {
            Image image = imageService.findById(imageId);
            image.addAlbum(album.getId());
        }
        return album;
    }

    @PUT
    @Path("/{id}")
    public Album update(@PathParam("id") int id, Album album)  {
        Album oldAlbum = albumService.findById(id);
        if (oldAlbum == null)
            throw new WebApplicationException(404);

        // Same rules as create. Return status 400 if any image can't be found.
        if(album.getImages() != null) {
            for (Integer imageId : album.getImages()) {
                if(imageService.findById(imageId) == null)
                    throw new WebApplicationException(400);
            }
        }

        // Clean up the albumId's belonging images in the oldAlbum
        for (Integer imageId : oldAlbum.getImages()) {
            Image image = imageService.findById(imageId);
            image.removeAlbum(oldAlbum.getId());
        }

        // Add the new albumId to the new images.
        for (Integer imageId : album.getImages()) {
            Image image = imageService.findById(imageId);
            image.addAlbum(oldAlbum.getId());
        }

        album.setId(oldAlbum.getId());
        album = albumService.updateEntry(album);
        
        return album;
    }

    @DELETE
    @Path("/{id}")
    public Album delete(@PathParam("id") int id) {
        Album deleteThis = albumService.deleteEntry(id);
        if(deleteThis == null)
            throw new WebApplicationException(404);

        // Remove this albumId for all images with it.
        for (Integer imageId : deleteThis.getImages()) {
            Image image = imageService.findById(imageId);
            image.removeAlbum(deleteThis.getId());   
        }
        return deleteThis;
    }

    @GET
    @Path("/{id}")
    public Album getById(@PathParam("id") int id) {
        Album album = albumService.findById(id);

        if(album == null)
            throw new WebApplicationException(404);
        return album;    
    }
    
    @GET
    public ArrayList<Album> list() {
        return albumService.listAll();
    }
}
