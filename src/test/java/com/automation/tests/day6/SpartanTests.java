package com.automation.tests.day6; // 013020

import com.automation.utilities.ConfigurationReader;
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

public class SpartanTests {

    @BeforeAll
    public static void setup(){ // 1
        baseURI = ConfigurationReader.getProperty("spartan.uri"); // 2
    }

    /*
    Task 1:
    given accept content type as JSON
    when user sends GET request to /spartans
    then user verifies that status code is 200
    and user verifies that content type is JSON
     */
    @Test
    @DisplayName("Verify that /spartans end-point returns 200 and content type as JSON")
    public void test1(){ // 3
        given().
                accept(ContentType.JSON).
        when().
                get("/spartans").prettyPeek().
        then().assertThat().
                statusCode(200).
                contentType(ContentType.JSON); // 4

    }


    /*
    Task 2:
    given accept content type as XML
    when user sends GET request to /spartans
    then user verifies that status code is 200
    and user verifies that content type is XML
     */
    @Test
    @DisplayName("Verify that /spartans end-point returns 200 and content type as XML")
    public void test2(){ // 5
        // accept(ContentType.XML) -> you are asking for body format
        // contentType(ContentType.XML) -> you are verifying that body format is XML
        given().
                accept(ContentType.XML).
                when().
                get("/spartans").prettyPeek().
                then().assertThat().
                statusCode(200).
                contentType(ContentType.XML); // 6

    }


    /*
    Task 3:
    given accept content type as XML
    when user sends GET request to /spartans
    then user saves payload in collection
     */
    @Test
    @DisplayName("Save payload into java collection")
    public void test3(){ // 7
        Response response = given().
                                contentType(ContentType.JSON).
                            when().
                                get("/spartans"); // 8

        List<Map<String, ?>> collection = response.jsonPath().get(); // 9

        for (Map<String, ?> map : collection){ // 9
        //    System.out.println(map); // 10
            System.out.println(map.get("phone")); // 11
            // only phone number
        }

    }
}
