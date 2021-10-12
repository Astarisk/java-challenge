package astarisk.java.challenge;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
