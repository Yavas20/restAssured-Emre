package com.cydeo.day07;

import com.cydeo.pojo.Spartan;
import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PutAndPatchRequestDemo extends SpartanTestBase {

    @DisplayName("PUT request to one spartan for update with Map")
    @Test
    public void PUTRequest() {
        //just like post request we have different options to send body, we will go with map
        Map<String, Object> putRequestMap = new LinkedHashMap<>();
        putRequestMap.put("name", "Emre");
        putRequestMap.put("gender", "Male");
        putRequestMap.put("phone", 1111111111l);

        given().contentType(ContentType.JSON).body(putRequestMap).pathParam("id", 109)
                .when().put("/api/spartans/{id}")
                .then().statusCode(204);

        //send a GET request after update, make sure updated field changed, or the new info matching
        //with requestBody that we send

        Spartan spartan = given().accept(ContentType.JSON).and().pathParam("id", 109)
                .when().get("/api/spartans/{id}")
                .then().statusCode(200)
                .extract().as(Spartan.class);

        assertThat(spartan.getName(), is(putRequestMap.get("name")));
        assertThat(spartan.getGender(), is(putRequestMap.get("gender")));
        assertThat(spartan.getPhone(), is(putRequestMap.get("phone")));
        assertThat(spartan.getId(), is(109));

    }

    @DisplayName("PATCH request to one spartan for partial update with Map")
    @Test
    public void PATCHRequest() {
        //just like post request we have different options to send body, we will go with map

        Map<String, Object> putRequestMap = new LinkedHashMap<>();
        putRequestMap.put("phone", 2222222222l);

        given().contentType(ContentType.JSON).body(putRequestMap).pathParam("id", 109)
                .when().patch("/api/spartans/{id}")
                .then().statusCode(204);

        Spartan spartan = given().accept(ContentType.JSON).and().pathParam("id", 109)
                .when().get("/api/spartans/{id}")
                .then().statusCode(200)
                .extract().as(Spartan.class);

        assertThat(spartan.getName(), is("Emre"));
        assertThat(spartan.getGender(), is("Male"));
        assertThat(spartan.getPhone(), is(putRequestMap.get("phone")));
        assertThat(spartan.getId(), is(109));

    }

    @DisplayName("DELETE one spartan")
    @Test
    public void deleteSpartan() {
        int idToDelete = 101;

        given().pathParam("id", idToDelete).when()
                .delete("/api/spartans/{id}");

        given().accept(ContentType.JSON).and().pathParam("id", 101)
                .when().get("/api/spartans/{id}")
                .then().statusCode(404);

    }
}