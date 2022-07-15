package com.cydeo.day03;

import com.cydeo.utilities.SpartanTestBase;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class SpartanTestWithPath extends SpartanTestBase {


    @DisplayName("Get request to /api/spartans/{id}, id with 10")
    @Test
    public void Test1() {

        Response response = given().accept(ContentType.JSON).and()
                .pathParam("id", 10).when().get("/api/spartans/{id}");

        assertEquals(200, response.statusCode());
        assertEquals("application/json", response.contentType());

        System.out.println(response.path("id").toString());
        System.out.println(response.path("name").toString());
        System.out.println(response.path("gender").toString());
        System.out.println(response.path("phone").toString());

        int id = response.path("id");
        String name = response.path("name");
        String gender = response.path("gender");
        long phone = response.path("phone");

        System.out.println("id = " + id);
        System.out.println("name = " + name);
        System.out.println("gender = " + gender);
        System.out.println("phone = " + phone);

        assertEquals(10, id);
        assertEquals("Lorenza", name);
        assertEquals("Female", gender);
        assertEquals(3312820936l, phone);


    }

    @DisplayName("Get request to /api/spartans")
    @Test
    public void Test2() {

        Response response = given().accept(ContentType.JSON).when().get("/api/spartans");

        int fistId = response.path("id[0]");
        System.out.println(fistId);

        String firstName = response.path("name[0]");
        System.out.println(firstName);

        int secondId = response.path("id[1]");
        System.out.println(secondId);

        String secondName = response.path("name[1]");
        System.out.println(secondName);

        String lastName = response.path("name[-1]");
        System.out.println(lastName);

        List<String > names = response.path("name");

        for (String n : names) {
            System.out.println(n);
        }


    }
}