package astarisk.java;

import io.quarkus.test.junit.QuarkusTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;

import javax.json.Json;
import javax.json.JsonObject;

import static org.hamcrest.CoreMatchers.containsString;

@QuarkusTest
public class ProductResourceTest {
    @Test
    public void testProductResource() {
        // Test GET before any entries.
        RestAssured.given().when().get("/products").then().statusCode(200);
    }

    // @BeforeEach
    // public void setUp() {
    //     // Create a few albums to run tests with.
    //     JsonObject jobj = Json.createObjectBuilder().add("images", Json.createArrayBuilder().build()).add("description", "Album of Apples.").build();
    //     RestAssured.given().contentType("application/json").body(jobj.toString()).when().post("/albums").then().statusCode(200);

    //     jobj = Json.createObjectBuilder().add("images", Json.createArrayBuilder().build()).add("description", "A forest of trees.").build();
    //     RestAssured.given().contentType("application/json").body(jobj.toString()).when().post("/albums").then().statusCode(200);
    // }

    @Test
    public void testProductCreate() {
        // Test POST

        // Test the creation of a Product without an AlbumId provided. This should fail and return a statuscode 400.
        JsonObject jobj = Json.createObjectBuilder().add("name", "Apple").add("description", "Red and Shiny.").build();
        RestAssured.given().contentType("application/json").body(jobj.toString()).when().post("/products").then().statusCode(400);

        // Create an album to test with
        jobj = Json.createObjectBuilder().add("images", Json.createArrayBuilder().build()).add("description", "A collection of images.").build();
        RestAssured.given().contentType("application/json").body(jobj.toString()).when().post("/albums").then().statusCode(200);

        // Create and assign an album
        jobj = Json.createObjectBuilder().add("name", "Apple").add("description", "Red and Shiny.").add("album", 0).build();
        RestAssured.given().contentType("application/json").body(jobj.toString()).when().post("/products").then().statusCode(200);
    }

    @Test
    public void testProductGetById() {
        // Test GET
        JsonObject jobj = Json.createObjectBuilder().add("name", "Apple").add("description", "Red and Shiny.").add("album", 0).build();
        RestAssured.given().contentType("application/json").body(jobj.toString()).when().post("/products").then().statusCode(200);

        // Create an album to test with
        jobj = Json.createObjectBuilder().add("images", Json.createArrayBuilder().build()).add("description", "A collection of images.").build();
        RestAssured.given().contentType("application/json").body(jobj.toString()).when().post("/albums").then().statusCode(200);

        jobj = Json.createObjectBuilder().add("name", "Trees").add("description", "A forest of trees.").add("album", 0).build();
        RestAssured.given().contentType("application/json").body(jobj.toString()).when().post("/products").then().statusCode(200);

        RestAssured.given().contentType("application/json").body(jobj.toString()).when().get("/albums/0").then().statusCode(200);
    } 

    @Test
    public void testList() {
        // Test GET when multiple items have been added.
        // Create an album to test with
        JsonObject jobj = Json.createObjectBuilder().add("images", Json.createArrayBuilder().build()).add("description", "A collection of images.").build();
        RestAssured.given().contentType("application/json").body(jobj.toString()).when().post("/albums").then().statusCode(200);

        jobj = Json.createObjectBuilder().add("name", "Apple").add("description", "Red and Shiny.").add("album", 0).build();
        RestAssured.given().contentType("application/json").body(jobj.toString()).when().post("/products").then().statusCode(200);

        RestAssured.when().get("/products").then().statusCode(200).body(containsString("Red"));
    }
}