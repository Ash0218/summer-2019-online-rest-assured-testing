package com.automation.tests.day8; // 020320

import com.automation.utilities.ConfigurationReader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;


import com.automation.pojos.Spartan;
import com.automation.utilities.ConfigurationReader;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;


public class CalendarificTestAPIKey {

    @BeforeAll
    public static void setup(){ // 1
        baseURI = ConfigurationReader.getProperty("calendarific.uri"); // 2
    }

    /*
    API key is a secret that the API generates and gives to the developer.
    API key looks like a long string: 52372b83bf9da05a40bf123f9e3762197c019a18
    API key can go as query parameter or inside a header. It depends on
     web service; how you must pass API key (as a header parameter or a query parameter.
    How it get's created? You go to web site, register, and service gives
     you API key. Then you have to pass API key alongside with every request.
    API key is easy to implement for developer and client. But non-technical
     people have no idea about this. So, it's mostly used by developers only.
     */


    /* Scenario 1:
    Given accept content type as JSON
    when user sends GET request to "/countries"
    Then user verifies that status code is 401
    And user verifies that status line contains "Unauthorized" message
    And user verifies that meta.error_detail contains "Missing or invalid api credentials." message
     */

    // status line: HTTP/1.1 401 Unauthorized
    @Test
    @DisplayName("Verify that user cannot access web service wihtout valid API key")
    public void test1(){ // 3
        given().
                accept(ContentType.JSON).
        when().
                get("/countries").prettyPeek().
        then().assertThat().
                statusCode(401).
                statusLine(containsString("Unauthorized")).
                body("meta.error_detail", containsString("Missing or invalid api credentials.")); // 4
        // Test unsuccessful -> Test passed
    }

    /* Scenario 2:
    Given accept content type as JSON
    And query parameter api_key with valid API key
    When user sends GET request to "/countries"
    Then user verifies that status code is 200
    And user verifies that status line contains "OK" message
    And user verifies that countries array is not empty
     */

     /* Using Postman:
     open Postman and add a new collection -> click + sign -> click
         Collection -> Collection name: Calendarific -> Create -> click
         three dots next to Calendarific -> Add request -> Request name:
         Get all countries -> Save to Calendarific -> click "GET Get all
         countries" -> copy the API base url from calendarific website:
         https://calendarific.com/api/v2 -> paste to GET -> add "/countries"
         to the url: https://calendarific.com/api/v2/countries -> Send -> put
         "api_key" under KEY -> put your API key under value: 52372b83bf9da05a40bf123f9e3762197c019a18
         -> Send -> Array is in the quote marks before :[. Ex: "countries": [
         -> you see array is not empty. The KEY should be "api_key" b.c it is on the website.
     */

     // Using RestAssured:
    @Test
    public void test2(){ // 5
        given().
                accept(ContentType.JSON).
                queryParam("api_key", "52372b83bf9da05a40bf123f9e3762197c019a18").
                // 52372b83bf9da05a40bf123f9e3762197c019a18 -> my API key from the calendarific website
        when().
                get("/countries").prettyPeek().
        then().assertThat().statusCode(200).
                            statusLine(containsString("OK")).
                            body("response.countries", not(empty())); // 6
                            // "response.countries" -> b.c countries is in response
                            // not(empty()) -> countries is not empty
    }


    /* Scenario 3:
    Given accept content type as JSON
    And query parameter api_key with valid API key
    And query parameter country is equals to US
    And query parameter type is equals to national
    And query parameter year equals to 2019
    When user sends GET request to "/holidays"
    Then user verifies that number of national holidays in US eqlaus to 11
     */

    /* Using Postman:
    Go to Postman, click three dots in Calendarific and click "Add request"
    -> Request name: Get list national holidays -> Save to Calendarific ->
    double click the "Get list national holidays" -> copy the GET url from
    Get all countries: https://calendarific.com/api/v2/countries?api_key=52372b83bf9da05a40bf123f9e3762197c019a18
    and paste it to Get list national holidays -> In the url, delete
    "countries" and change it to "holidays": https://calendarific.com/api/v2/holidays?api_key=52372b83bf9da05a40bf123f9e3762197c019a18
    -> under api_key, put "country" -> next: "US" -> under country: type,
    next: national -> under type: year, next: 2019 -> Send
     */

    // Using RestAssured:
    @Test
    @DisplayName("Verify that there are 11 national holidays in the US")
    public void test3(){ // 7
        Response response = given().
                accept(ContentType.JSON).
                queryParam("api_key", "52372b83bf9da05a40bf123f9e3762197c019a18").
                queryParam("country", "US").
                queryParam("type", "national").
                queryParam("year", 2019).
        when().
                get("/holidays").prettyPeek(); // 8

        // shorter way with syntax sugar (hamcrest matcher)
        response.then().assertThat().statusCode(200).body("response.holidays", hasSize(11)); // 9
        // List<Map<String, ?>> - list of objects, since there are nested objects, we cannot specify soe value type.
        List<Map<String, ?>> holidays = response.jsonPath().get("response.holidays"); // 10
        // "response.holidays" -> b.c holidays is in response

        // regular way
        assertEquals(11, holidays.size(), "Wrong number of holidays!"); // 11
        // "Wrong number of holidays!" -> error message
        assertEquals(200, response.getStatusCode()); // 12



    }


}
