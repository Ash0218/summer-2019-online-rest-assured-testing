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

public class OMDBTestsAPIKey {

    @BeforeAll
    public static void setup(){ // 1
        baseURI = ConfigurationReader.getProperty("omdb.uri"); // 2
    }

    @Test
    @DisplayName("Verify that unauthorized user cannot get info about movies from OMDB api")
    public void test1(){ // 3
        given().
                contentType(ContentType.JSON).
                queryParam("t", "Home Alone").
        when().
                get().prettyPeek().
        then().assertThat().statusCode(401).body("Error", is("No API key provided.")); // 4
        // 401 Unauthorized - you are not allowed to access this web service
    }


    @Test
    @DisplayName("Verify that Mcaulay Culkin appears in actors list for Home Alone movie")
    public void test2(){ // 5
        Response response = given().
                contentType(ContentType.JSON).
                queryParam("t", "Home Alone").
                // "t" is from the website (www.omdbapi.com) for movies
                queryParam("apikey", "8461f892").
                // "apikey" is from the website and the value (8461f892) is given to me when I registered.
        when().
                get().prettyPeek(); // 6
        response.then().assertThat().statusCode(200).body("Actors", containsString("Macaulay Culkin")); // 7

        Map<String, Object> payload = response.getBody().as(Map.class); // 8

        System.out.println(payload); // 9

        // entry: key=value pair
        // Map is a collection of entries
        // how to iterate a map?
        for (Map.Entry<String, Object> entry: payload.entrySet()){
            System.out.println("Key: "+entry.getKey()+", value: "+entry.getValue()); // 10
        }

    }

}
