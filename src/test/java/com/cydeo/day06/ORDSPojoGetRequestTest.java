package com.cydeo.day06;

import com.cydeo.pojo.Employee;
import com.cydeo.pojo.Region;
import com.cydeo.pojo.Regions;
import com.cydeo.utilities.HRTestBase;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ORDSPojoGetRequestTest extends HRTestBase {

    @Test
    public void regionTest() {

        JsonPath jsonPath = get("/regions").then().statusCode(200).log().body().extract().jsonPath();

        Region region1 = jsonPath.getObject("items[0]", Region.class);
        System.out.println(region1);
        System.out.println("region1.getRegion_name() = " + region1.getRegion_name());
        System.out.println("region1.getRegion_id() = " + region1.getRegionId());
        System.out.println("region1.getLinks().get(0).getHref() = " + region1.getLinks().get(0).getHref());

    }

    @DisplayName("GET request to /employees and only get couple of values as a Pojo class")
    @Test
    public void employeeGet() {

        Employee employee1 = get("/employees").then().statusCode(200)
                .extract().jsonPath().getObject("items[0]", Employee.class);

        System.out.println(employee1);

    }

    /* send a get request to regions
        verify that region ids are 1,2,3,4
        verify that regions names Europe ,Americas , Asia, Middle East and Africa
        verify that count is 4
        try to use pojo as much as possible
        ignore non used fields
     */

    @DisplayName("GET request to region only some fields test")
    @Test
    public void regionPojoTest() {
        //send a get request and save everything inside the regions object
        //since we prepare pojo also for the all properties we don't need to use any path so as() method is enough
        Regions regions = get("/regions").then().statusCode(200).extract().response().as(Regions.class);

        System.out.println(regions);
        //verify count is 4
        assertThat(regions.getCount(), is(4));

        List<String> regionNames = new ArrayList<>();
        List<Integer> regionIds = new ArrayList<>();

        List<Region> items = regions.getItems();

        for (Region region : items) {
            regionNames.add(region.getRegion_name());
            regionIds.add(region.getRegionId());
        }

        List<String> expectedRegionNames = Arrays.asList("Europe" ,"Americas" , "Asia", "Middle East and Africa");
        List<Integer> expectedRegionIds = Arrays.asList(1, 2, 3, 4);

        assertThat(regionNames, is(expectedRegionNames));
        assertThat(regionIds, is(expectedRegionIds));

    }
}
