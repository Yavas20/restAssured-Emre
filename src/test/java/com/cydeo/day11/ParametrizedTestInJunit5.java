package com.cydeo.day11;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ParametrizedTestInJunit5 {

    @ParameterizedTest
    @ValueSource(ints = {1, 4, 5, 6, 2, 11, 2 , 55})
    public void test1 (int number) {
        System.out.println("number = " + number);
        Assertions.assertTrue(number > 5);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Emre", "Latife", "Ümit", "Zeynep"})
    public void test2(String name){
        System.out.println(name);
    }

    // SEND GET REQUEST TO https://api.zippopotam.us/us/{zipcode}
    // with these zipcodes 22030,22031, 22032, 22033 , 22034, 22035, 22036
    // check status code 200

    @ParameterizedTest
    @ValueSource(ints = {22030,22031, 22032, 22033 , 22034, 22035, 22036})
    public void testApi (int zipcode){

        given().accept(ContentType.JSON)
                .and().pathParam("zipcode", zipcode)
                .when().get("https://api.zippopotam.us/us/{zipcode}")
                .then().statusCode(200);

    }


}
