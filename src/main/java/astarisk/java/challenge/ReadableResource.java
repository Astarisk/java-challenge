package astarisk.java.challenge;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import astarisk.java.challenge.models.Album;
import astarisk.java.challenge.models.Image;
import astarisk.java.challenge.models.Product;
import astarisk.java.challenge.responses.ReadableAlbum;
import astarisk.java.challenge.responses.ReadableImage;
import astarisk.java.challenge.responses.ReadableProduct;
import astarisk.java.challenge.services.AlbumService;
import astarisk.java.challenge.services.ImageService;
import astarisk.java.challenge.services.ProductService;

@Path("/readable")
public class ReadableResource {
    @Inject
    ProductService productService;
    @Inject
    AlbumService albumService;
    @Inject
    ImageService imageService;

    @GET
    public ArrayList<ReadableProduct> list() {
        ArrayList<Product> products = productService.listAll();
        ArrayList<ReadableProduct> readableProducts = new ArrayList<>();

        for (Product product : products) {
            ReadableProduct readableProduct = new ReadableProduct();
            ReadableAlbum readableAlbum = new ReadableAlbum();
            ArrayList<ReadableImage> readableImages = new ArrayList<>();

            readableProduct.name = product.getName();
            readableProduct.description = product.getDescription();
            readableProduct.album = readableAlbum;
            
            Album album = albumService.findById(product.getAlbum());
            readableAlbum.description = album.getDescription();
            readableAlbum.images = readableImages;

            for (Integer imageID : album.getImages()) {
                ReadableImage readableImage = new ReadableImage();
                Image image = imageService.findById(imageID);
                readableImage.description = image.getDescription();
                readableImage.title = image.getTitle();
                readableImages.add(readableImage);
            }
            readableProducts.add(readableProduct);
        }
        return readableProducts;
    }
}