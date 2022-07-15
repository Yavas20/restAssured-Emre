package com.cydeo.utilities;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public abstract class HRTestBase {

    @BeforeAll
    public static void init() {

        RestAssured.baseURI = "http://52.90.200.128:1000/ords/hr";

    }

}
