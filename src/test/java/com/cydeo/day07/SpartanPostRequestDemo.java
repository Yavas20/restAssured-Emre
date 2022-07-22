package com.cydeo.day07;

import com.cydeo.pojo.Spartan;
import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SpartanPostRequestDemo extends SpartanTestBase {
    /*
        Given accept type and Content type is JSON
        And request json body is:
        {
          "gender":"Male",
          "name":"Severus",
          "phone":8877445596
       }
        When user sends POST request to '/api/spartans'
        Then status code 201
        And content type should be application/json
        And json payload/response/body should contain:
        "A Spartan is Born!" message
        and same data what is posted
     */
    @Test
    public void postMethod1() {

        String requestJsonBody = "{\"gender\":\"Male\",\n" +
                "\"name\":\"EEEmree\",\n" +
                "\"phone\":8877445596}";

        Response response = given().accept(ContentType.JSON).and() //what we are asking from api which is JSON response
                .contentType(ContentType.JSON) //what we are sending to api, which is JSON also
                .body(requestJsonBody)
                .when()
                .post("/api/spartans");

        //verify status code

        assertThat(response.statusCode(), is(201));
        assertThat(response.contentType(), is("application/json"));

        String expectedResponseMessage = "A Spartan is Born!";

        assertThat(response.path("success"), is(expectedResponseMessage));
        assertThat(response.path("data.name"), is("EEEmree"));
        assertThat(response.path("data.gender"), is("Male"));
        assertThat(response.path("data.phone"), is(8877445596l));

    }

    @DisplayName("POST with Map to JSON")
    @Test
    public void postMethod2() {

        //create a map to keep request json body information

        Map<String, Object> requestMap = new LinkedHashMap<>();
        requestMap.put("name", "EEEmree");
        requestMap.put("gender", "Male");
        requestMap.put("phone", 8877445596l);

        Response response = given().accept(ContentType.JSON).log().all()
                .and().contentType(ContentType.JSON)
                .body(requestMap)
                .when().log().body()
                .post("/api/spartans");

        //verify status code

        assertThat(response.statusCode(), is(201));
        assertThat(response.contentType(), is("application/json"));

        String expectedResponseMessage = "A Spartan is Born!";

        assertThat(response.path("success"), is(expectedResponseMessage));
        assertThat(response.path("data.name"), is("EEEmree"));
        assertThat(response.path("data.gender"), is("Male"));
        assertThat(response.path("data.phone"), is(8877445596l));

    }

    @DisplayName("POST with Map to Spartan Class")
    @Test
    public void postMethod3() {

        //create one object from your pojo, send it as a JSON

        Spartan spartan = new Spartan();
        spartan.setName("EEEmree");
        spartan.setGender("Male");
        spartan.setPhone(8877445596l);

        Response response = given().accept(ContentType.JSON).log().all()
                .and().contentType(ContentType.JSON)
                .body(spartan)
                .when().log().body()
                .post("/api/spartans");

        //verify status code

        assertThat(response.statusCode(), is(201));
        assertThat(response.contentType(), is("application/json"));

        String expectedResponseMessage = "A Spartan is Born!";

        assertThat(response.path("success"), is(expectedResponseMessage));
        assertThat(response.path("data.name"), is("EEEmree"));
        assertThat(response.path("data.gender"), is("Male"));
        assertThat(response.path("data.phone"), is(8877445596l));

    }

    @DisplayName("POST with Map to Spartan Class")
    @Test
    public void postMethod4() {
        //this example we implement serialization with creating spartan object sending as a request body
        //also implemented deserialization getting the id,sending get request and saving that body as a response

        //create one object from your pojo, send it as a JSON

        Spartan spartan = new Spartan();
        spartan.setName("EEEmree");
        spartan.setGender("Male");
        spartan.setPhone(8877445596l);

        System.out.println(spartan);
        String expectedResponseMessage = "A Spartan is Born!";

        int idFromPost = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .body(spartan)
                .when().post("/api/spartans")
                .then()
                .statusCode(201)
                .contentType("application/json")
                .body("success", is(expectedResponseMessage))
                .extract().jsonPath().getInt("data.id");

        System.out.println("idFromPost = " + idFromPost);
        //send a get request to id

        Spartan spartan1 = given().accept(ContentType.JSON).pathParam("id", idFromPost)
                .when().get("/api/spartans/{id}")
                .then().statusCode(200)
                .log().all().extract().as(Spartan.class);

        assertThat(spartan1.getName(), is(spartan.getName()));
        assertThat(spartan1.getGender(), is(spartan.getGender()));
        assertThat(spartan1.getPhone(), is(spartan.getPhone()));
        assertThat(spartan1.getId(), is(idFromPost));


    }
}