package com.automation.tests.day4; // 012720

import com.automation.utilities.ConfigurationReader;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;


public class MetaWeatherJsonPathTests {

    @BeforeAll
    public static void setup(){ // 1
        baseURI = ConfigurationReader.getProperty("meta.weather.uri"); // 2
    }


    /**
     * TASK:
     * Given accept type is JSON
     * When users sends a GET request to "/search"
     * And query parameter is 'New'
     * Then user verifies that payload contains 5 objects
     */
    @Test
    @DisplayName("Verify that there are 5 cities that are matching 'New'")
    public void test1(){ // 3
        given().
                accept(ContentType.JSON).
                queryParam("query", "New").
                // query parameter has ? mark. EX: /api/location/search/?query=san
        when().
                get("/search").
                // path should be after location b.c the meta weather uri is:
                //  meta.weather.uri=https://www.metaweather.com/api/location
        then().
                assertThat().
                    statusCode(200).
                    body("", hasSize(5)).
        // 5 -> b.c it is the requirement:
        //  "Then user verifies that payload contains 5 objects"
                    log().body(true); // 4 4:26:49
    }
}



/**
 * TASK
 * Given accept type is JSON
 * When users sends a GET request to "/search"
 * And query parameter is New
 * Then user verifies that 1st object has following info:
 * |title   |location_type|woeid  |latt_long          |
 * |New York|City         |2459115|40.71455,-74.007118|
 */

/* * TASK
 * Given accept type is JSON
 * When users sends a GET request to "/search"
 * And query parameter is 'Las'
 * Then user verifies that payload  contains following titles:
 * |Glasgow  |
 * |  Dallas |
 * |Las Vegas|*/

/*
 * TASK
 * Given accept type is JSON
 * When users sends a GET request to "/search"
 * And query parameter is 'Las'
 * Then verify that every item in payload has location_type City
 */

/*
 * TASK
 * Given accept type is JSON
 * When users sends a GET request to "/location"
 * And path parameter is '44418'
 * Then verify following that payload contains weather forecast sources
   |BBC                 |
 * |Forecast.io         |
 * |HAMweather          |
 * |Met Office          |
 * |OpenWeatherMap      |
 * |Weather Underground |
 * |World Weather Online|
 * */

