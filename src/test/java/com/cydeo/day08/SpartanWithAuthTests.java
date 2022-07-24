package com.cydeo.day08;

import com.cydeo.utilities.SpartanAuthBase;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SpartanWithAuthTests extends SpartanAuthBase {


    @DisplayName("GET /api/spartans as a public user(guest) expect 401 ")
    @Test
    public void test1() {
        given().accept(ContentType.JSON).
                when().
                get("/api/spartans")
                .then().statusCode(401)
                .log().all();

    }

    @DisplayName("GET /api/spartans as admin user expect 200")
    @Test
    public void testAdmin() {
        //how to pass admin admin as a username and password ?

        given().auth().basic("admin", "admin")
                .accept(ContentType.JSON)
                .when().get("/api/spartans")
                .then().statusCode(200)
                .log().all();

    }

    @DisplayName("DELETE /spartans/{id} as editor user expect 403")
    @Test
    public void testEditorDelete(){

        given().auth().basic("editor", "editor")
                .accept(ContentType.JSON)
                .pathParam("id", 30)
                .when().delete("/api/spartans/{id}")
                .then().statusCode(403).log().body();

    }

    @Test
    public void testSpartan(){

        /*
        As a homework,write a detailed test for Role Base Access Control(RBAC)
            in Spartan Auth app (7000)
            Admin should be able take all CRUD
            Editor should be able to take all CRUD
                other than DELETE
            User should be able to only READ data
                not update,delete,create (POST,PUT,PATCH,DELETE)
       --------------------------------------------------------
        Can guest even read data ? 401 for all
     */

        Map<String , Object> mapSpartan = new LinkedHashMap<>();
        mapSpartan.put("name", "Emre");
        mapSpartan.put("gender", "Male");
        mapSpartan.put("phone", 1111111111l);

        given().auth().basic("admin", "admin")
                .accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .body(mapSpartan)
                .when().post("/api/spartans")
                .then().statusCode(201).log().body();

        Map<String , Object> mapSpartan2 = new LinkedHashMap<>();
        mapSpartan.put("name", "Emreeee");
        mapSpartan.put("gender", "Male");
        mapSpartan.put("phone", 1111111111l);

        given().auth().basic("user", "user")
                .accept(ContentType.JSON).and().contentType(ContentType.JSON)
                .body(mapSpartan2)
                .pathParam("id", 103)
                .when().put("/api/spartans/{id}")
                .then().statusCode(403);

       given().auth().basic("editor", "editor")
               .contentType(ContentType.JSON)
               .pathParam("id", 107)
               .when().delete("api/spartans/{id}")
               .then().statusCode(403);

        given().auth().basic("admin", "admin")
                .contentType(ContentType.JSON)
                .pathParam("id", 111)
                .when().delete("api/spartans/{id}")
                .then().statusCode(204);

        given().auth().basic("user", "user")
                .accept(ContentType.JSON)
                .pathParam("id", 111)
                .when().get("/api/spartans/{id}")
                .then().statusCode(404);


    }
}
/*
 given().auth().basic("editor", "editor")
                .accept(ContentType.JSON).and().contentType(ContentType.JSON)
                .pathParam("id", 103)
                .body(mapSpartan2)
                .when().put("/api/spartans/{id}")
                .then().statusCode(203);

        given().auth().basic("user", "user")
                .accept(ContentType.JSON)
                .pathParam("id", 103)
                .when().get("/api/spartans/{id}")
                .then().statusCode(200);
 */