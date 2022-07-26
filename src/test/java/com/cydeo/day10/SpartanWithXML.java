package com.cydeo.day10;

import com.cydeo.utilities.SpartanAuthBase;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SpartanWithXML extends SpartanAuthBase {

    @DisplayName("GET request to /api/spartans and verify xml")
    @Test
    public void getSpartanXml() {
        //we will ask for xml response
        //assert status code 200
        //assert content type is xml (we got xml response)
        //verify first spartan name is Meade

        given().accept(ContentType.XML).and()
                .auth().basic("admin", "admin")
                .get("/api/spartans")
                .then().statusCode(200)
                .contentType("application/xml;charset=UTF-8")
                .body("List.item[0].name", is("Meade"))
                .body("List.item[0].gender", is("Male"));

    }

    @DisplayName("GET request /api/spartans with xmlPath")
    @Test
    public void testXmlPath(){

        Response response =  given().accept(ContentType.XML).and()
                .auth().basic("admin", "admin")
                .get("/api/spartans");

        XmlPath xmlPath = response.xmlPath();
        String nameFirst = xmlPath.getString("List.item[0].name");
        System.out.println(nameFirst);

        int idThirdItem = xmlPath.getInt("List.item[2].id");
        System.out.println(idThirdItem);

        List<String> names = xmlPath.getList("List.item.name");
        System.out.println(names);

    }



}