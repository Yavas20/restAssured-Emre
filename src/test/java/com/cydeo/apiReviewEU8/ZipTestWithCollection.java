package com.cydeo.apiReviewEU8;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ZipTestWithCollection extends ZipBase {

    @Test
    public void collectionTest() {

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("zip", 22031)
                .when().get("/{zip}");

        Map<String , Object> postCode = response.as(Map.class);
        System.out.println(postCode);
        System.out.println(postCode.get("places"));
        assertEquals("United States", postCode.get("country"));


        List<Map<String, Object>> places = (List<Map<String, Object>>) postCode.get("places");
        System.out.println("places = " + places);

        assertEquals("Virginia", places.get(0).get("state"));
        assertEquals("Fairfax", places.get(0).get("place name"));


    }
}