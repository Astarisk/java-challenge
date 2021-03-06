package astarisk.java;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;

import javax.json.Json;
import javax.json.JsonObject;

import static org.hamcrest.CoreMatchers.containsString;

@QuarkusTest
public class AlbumResourceTest {
    @Test
    public void testAlbumResource() {
        // Test GET before any entries.
        RestAssured.given().when().get("/albums").then().statusCode(200);
    }

    @Test
    public void testProductCreate() {
        // Test POST

        // A simple Album with no images
        JsonObject jobj = Json.createObjectBuilder().add("images", Json.createArrayBuilder().build()).add("description", "A collection of images.").build();
        RestAssured.given().contentType("application/json").body(jobj.toString()).when().post("/albums").then().statusCode(200);
    }

    @Test
    public void testAlbumGetById() {
        // Test GET
        JsonObject jobj = Json.createObjectBuilder().add("images", Json.createArrayBuilder().build()).add("description", "A collection of images.").build();
        RestAssured.given().contentType("application/json").body(jobj.toString()).when().get("/albums/0").then().statusCode(200);
    } 

    @Test
    public void testDelete() {
        JsonObject jobj = Json.createObjectBuilder().add("images", Json.createArrayBuilder().build()).add("description", "A collection paintings.").build();
        RestAssured.given().contentType("application/json").body(jobj.toString()).when().post("/albums").then().statusCode(200);
  
        jobj = Json.createObjectBuilder().add("images", Json.createArrayBuilder().build()).add("description", "A forest of trees.").build();
        RestAssured.given().contentType("application/json").body(jobj.toString()).when().post("/albums").then().statusCode(200);

        RestAssured.given().contentType("application/json").body(jobj.toString()).when().get("/albums/1").then().statusCode(200);
        RestAssured.given().contentType("application/json").body(jobj.toString()).when().delete("/albums/1").then().statusCode(200);
        RestAssured.given().contentType("application/json").body(jobj.toString()).when().get("/albums/1").then().statusCode(404);

    }

    @Test
    public void testUpdate() {
        // Create an Album.
        JsonObject jobj = Json.createObjectBuilder().add("images", Json.createArrayBuilder().build()).add("description", "A collection of images.").build();
        RestAssured.given().contentType("application/json").body(jobj.toString()).when().post("/albums").then().statusCode(200);
        
        jobj = Json.createObjectBuilder().add("images", Json.createArrayBuilder().build()).add("description", "A forest of trees.").build();
        RestAssured.given().contentType("application/json").body(jobj.toString()).when().put("/albums/0").then().
        statusCode(200).body(containsString("A forest of trees."));

        // TODO: Add in the testing of imageId updating when ImageResource is made.
    }
    
    @Test 
    public void testAlbumList() {
        JsonObject jobj = Json.createObjectBuilder().add("images", Json.createArrayBuilder().build()).add("description", "Red").build();
        RestAssured.given().contentType("application/json").body(jobj.toString()).when().post("/albums").then().statusCode(200);

        jobj = Json.createObjectBuilder().add("images", Json.createArrayBuilder().build()).add("description", "Apple").build();
        RestAssured.given().contentType("application/json").body(jobj.toString()).when().post("/albums").then().statusCode(200);

        RestAssured.when().get("/albums").then().statusCode(200).body(containsString("Apple"), containsString("Red"));
    }
}
