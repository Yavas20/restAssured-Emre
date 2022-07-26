package com.cydeo.day10;
import com.cydeo.utilities.SpartanAuthBase;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class JsonSchemaValidationTest extends SpartanAuthBase {


    @DisplayName("GET request to verify one spartan against to schema")
    @Test
    public void schemaValidation() {

        given().accept(ContentType.JSON).and()
                .auth().basic("admin", "admin")
                .pathParam("id", 50)
                .when().get("/api/spartans/{id}").then().statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("SingleSpartanSchema.json"))
                .log().all();

    }

    @DisplayName("GET request to all spartans and verify schema")
    @Test
    public void allSpartanSchemaTest() {

        given().accept(ContentType.JSON)
                .and().auth().basic("admin", "admin")
                .when().get("/api/spartans")
                .then().statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchema(new File("src/test/java/com/cydeo/day10/allSpartansSchema.json")));


    }

    @Test
    public void postSpartanSchemaTest() {

        //homework
        //put your post json schema under day10
        //post one spartan using dynamic input(name,gender,phone)
        //verify your post response matching with json schema

        Map<String, Object> jsOnPost = new LinkedHashMap<>();
        jsOnPost.put("name", "Zeyyneppp");
        jsOnPost.put("gender", "Female");
        jsOnPost.put("phone", 1111111111l);

        given().accept(ContentType.JSON).and().contentType(ContentType.JSON)
                .body(jsOnPost).auth().basic("admin", "admin")
                .when().post("/api/spartans")
                .then().statusCode(201)
                .log().all()
                .body(JsonSchemaValidator.matchesJsonSchema(new File("src/test/java/com/cydeo/day10/spartanPostJsonSchema.json")));


    }

    }

