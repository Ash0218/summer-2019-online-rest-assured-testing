package com.automation.tests.day2; // 012320

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class MetaWeatherTests {

    /*
    /api/location/search/?query=san
    /api/location/search/?query=london
    /api/location/search/?lattlong=36.96,-122.02
    /api/location/search/?lattlong=50.068,-5.316
    /api/location/{woeid}/
        "title": "San Francisco",
        "location_type": "City",
        "woeid": 2487956,
        "latt_long": "37.777119, -122.41964"
     */
    private String baseURI = "https://www.metaweather.com/api/"; // 1

    @Test
    public void test1(){ // 2
        Response response = given()
                .baseUri(baseURI+"/location/search/")
                .queryParam("query", "san")
                .get(); // 3
        assertEquals(200, response.getStatusCode()); // 4
        System.out.println(response.prettyPrint()); // 5

    }

// check weather
// /users/100 -> 100 is a path parameter.
// /users/255?name=James/ -> name is a query parameter. key = value,
//  key is a query parameter.
    @Test
    public void test2(){ // 6
        Response response = given()
                .pathParam("woeid", "2487956")
                .get(baseURI+"/location/{woeid}"); // 7

        System.out.println(response.prettyPrint()); // 8
    }
}
