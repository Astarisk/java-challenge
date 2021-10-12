package astarisk.java;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;

import javax.json.Json;
import javax.json.JsonObject;

import static org.hamcrest.CoreMatchers.containsString;

@QuarkusTest
public class ImageResourceTest {
    @Test
    public void testImageResource() {
        // Test GET before any entries.
        RestAssured.given().when().get("/images").then().statusCode(200);
    }

    @Test
    public void testImageCreate() {
        // Test POST

        // A simple Image with no albums.
        JsonObject jobj = Json.createObjectBuilder().add("albums", Json.createArrayBuilder().build()).add("title", "Apple.png.").add("description", "A worm ridden apple.").build();
        RestAssured.given().contentType("application/json").body(jobj.toString()).when().post("/images").then().statusCode(200);

        // A simple Image with albums that doesn't exist
        jobj = Json.createObjectBuilder().add("albums", Json.createArrayBuilder().add(3).build()).add("title", "Apple.png.").add("description", "A worm ridden apple.").build();
        RestAssured.given().contentType("application/json").body(jobj.toString()).when().post("/images").then().statusCode(400);

        // Create an album.
        jobj = Json.createObjectBuilder().add("images", Json.createArrayBuilder().build()).add("description", "A collection of apples.").build();
        RestAssured.given().contentType("application/json").body(jobj.toString()).when().post("/albums").then().statusCode(200);

        // Add an image to an album
        jobj = Json.createObjectBuilder().add("albums", Json.createArrayBuilder().add(0).build()).add("title", "Apple.png.").add("description", "A worm ridden apple.").build();
        RestAssured.given().contentType("application/json").body(jobj.toString()).when().post("/images").then().statusCode(200).body(containsString("Apple.png"));
    }

    @Test
    public void testImageGetById() {
        // Test GET
        JsonObject jobj = Json.createObjectBuilder().add("albums", Json.createArrayBuilder().build()).add("title", "Apple.png.").add("description", "A worm ridden apple.").build();
        RestAssured.given().contentType("application/json").body(jobj.toString()).when().get("/images/0").then().statusCode(200);
    } 

    @Test
    public void testDelete() {
        JsonObject jobj = Json.createObjectBuilder().add("albums", Json.createArrayBuilder().build()).add("title", "Apple.png.").add("description", "A worm ridden apple.").build();
        RestAssured.given().contentType("application/json").body(jobj.toString()).when().post("/images").then().statusCode(200);

        jobj = Json.createObjectBuilder().add("albums", Json.createArrayBuilder().build()).add("title", "Pear.png.").add("description", "A green pear.").build();
        RestAssured.given().contentType("application/json").body(jobj.toString()).when().post("/images").then().statusCode(200);
        
        RestAssured.given().contentType("application/json").body(jobj.toString()).when().get("/images/1").then().statusCode(200);
        RestAssured.given().contentType("application/json").body(jobj.toString()).when().delete("/images/1").then().statusCode(404);
    }

    @Test
    public void testUpdate() {
        JsonObject jobj = Json.createObjectBuilder().add("albums", Json.createArrayBuilder().build()).add("title", "Apple.png.").add("description", "A worm ridden apple.").build();
        RestAssured.given().contentType("application/json").body(jobj.toString()).when().post("/images").then().statusCode(200);
        RestAssured.given().contentType("application/json").body(jobj.toString()).when().get("/images/0").then().statusCode(200);

        jobj = Json.createObjectBuilder().add("albums", Json.createArrayBuilder().build()).add("title", "Pear.png.").add("description", "A green pear.").build();
        RestAssured.given().contentType("application/json").body(jobj.toString()).when().put("/images/0").then().
        statusCode(200).body(containsString("A green pear."));

        // TODO: Add in the testing of imageId updating when ImageResource is made.
    }
    
    @Test 
    public void testGetList() {
        JsonObject jobj = Json.createObjectBuilder().add("albums", Json.createArrayBuilder().build()).add("title", "Apple.png.").add("description", "A worm ridden apple.").build();
        RestAssured.given().contentType("application/json").body(jobj.toString()).when().post("/images").then().statusCode(200);

        jobj = Json.createObjectBuilder().add("albums", Json.createArrayBuilder().build()).add("title", "Pear.png.").add("description", "A green pepar.").build();
        RestAssured.given().contentType("application/json").body(jobj.toString()).when().post("/images").then().statusCode(200);

        RestAssured.when().get("/images").then().statusCode(200).body(containsString("Apple.png"), containsString("Pear.png"));
    }
}
