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
import astarisk.java.challenge.models.Product;
import astarisk.java.challenge.services.AlbumService;
import astarisk.java.challenge.services.ProductService;

@Path("/products")
public class ProductResource {
    @Inject
    ProductService productService;
    @Inject
    AlbumService albumService;

    // Keep the implentation as simple as possible.
    // Only create a product if an Album exists, otherwise throw a WebApplicationException(400) error
    // This means that a Product cannot exist without an album being made prior.
    @POST
    public Product create(Product product) {
        if(product.getAlbum() == null)
            throw new WebApplicationException(400);
        
        // Check to see if a valid album exists with the given id.
        Album album = albumService.findById(product.getAlbum());
        if (album == null)
            throw new WebApplicationException(400);

        product = productService.createEntry(product);
        return product;
    }

    // Bonus update + delete
    @PUT
    @Path("/{id}")
    public Product update(@PathParam("id") int id, Product product)  {
        // The AlbumId situation is the same as create. Only update if the AlbumId provided is still valid.
        if(product.getAlbum() == null)
            throw new WebApplicationException(400);
        Album album = albumService.findById(product.getAlbum());
        if (album == null)
            throw new WebApplicationException(400);

        productService.updateEntry(product);
        return product;
    }

    @DELETE
    @Path("/{id}")
    public Product delete(@PathParam("id") int id) {
        // It is safe to only just delete the product, nothing else related to it needs to follow.
        Product deleteThis = productService.deleteEntry(id);
        if(deleteThis == null)
            throw new WebApplicationException(404);
        return deleteThis;
    }

    @GET
    @Path("/{id}")
    public Product getById(@PathParam("id") int id) {
        Product product = productService.findById(id);

        if(product == null)
            throw new WebApplicationException(404);
        return product;    
    }
    
    @GET
    public ArrayList<Product> list() {
        return productService.listAll();
    }
}
