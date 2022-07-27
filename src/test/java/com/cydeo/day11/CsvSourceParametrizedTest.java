package com.cydeo.day11;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CsvSourceParametrizedTest {

    // Test first number + second number = third number
//            1, 3 , 4
//            2, 3 , 5
//            8, 7 , 15
//            10, 9 , 19

    @ParameterizedTest
    @CsvSource({"1, 3 , 4",
                "2, 3 , 5",
                "8, 7 , 15",
                "10, 9 , 19"})
    public void testAddition(int num1, int num2, int sum){

        assertThat(num1+num2, is(sum));

    }

    // Write a parameterized test for this request
    // GET https://api.zippopotam.us/us/{state}/{city}
    /*
        "NY, New York",
        "CO, Denver",
        "VA, Fairfax",
        "VA, Arlington",
        "MA, Boston",
        "NY, New York",
        "MD, Annapolis"
     */
    //verify place name contains your city name
    //print number of places for each request

    @ParameterizedTest
    @CsvSource({"NY, New York", "CO, Denver", "VA, Fairfax",
            "VA, Arlington", "MA, Boston","NY, New York", "MD, Annapolis"})
    public void testApi(String state, String city){

        JsonPath jsonPath = given().accept(ContentType.JSON).pathParams("state", state, "city", city)
                .when().get("https://api.zippopotam.us/us/{state}/{city}").jsonPath();

        List<String> names = jsonPath.getList("places.'place name'");
        assertThat(names, everyItem(containsStringIgnoringCase(city)));
        System.out.println(names.size());

        /*
         int placeNumber = given()
                .baseUri("https://api.zippopotam.us")
                .accept(ContentType.JSON)
                .pathParam("state", state)
                .pathParam("city", city)
                .log().uri()
                .when()
                .get("/us/{state}/{city}")
                .then()
                .statusCode(200)
                .and()
                .body("places.'place name'", everyItem(containsStringIgnoringCase(city)))
                //.log().body()
                .extract()
                .jsonPath().getList("places").size();

        System.out.println("placeNumber = " + placeNumber);
         */

    }


}
