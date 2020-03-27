package com.automation.tests.day7; // 013120


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

public class SpartansTestsDay7 {

    @BeforeAll
    public static void setup(){ // 1
        baseURI = ConfigurationReader.getProperty("spartan.uri"); // 2
    }

    // add new Spartan from the external JSON file -> spartan.json file under pop.xml


    @Test
    @DisplayName("Add new user by using external JSON file")
    public void test1(){ // 3
        File file = new File(System.getProperty("user.dir")+"/spartan.json"); // 4
        // read file from the user directory. The file name is 'spartan.json'

        given().
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                body(file).
        when().
                post("/spartans").prettyPeek().
        then().assertThat().
                statusCode(201).
                body("success", is("Spartan is Born!")); // 5
        // run -> error b.c of bad request -> name was too long, so
        //  go to spartan.json file and fix the name to "SDET" -> success
    }

    @Test
    @DisplayName("Add new user by using map")
    public void test2(){ // 6
        Map<String, Object> spartan = new HashMap<>(); // 7
        // we used Object b.c the values are objects (#8-10)
        spartan.put("phone", 12999999117L); // 8
        // "phone", 12999L -> key, value
        spartan.put("gender", "male"); // 9
        spartan.put("name", "John Deer"); // 10

        assertEquals("Male", spartan.get("gender")); // 12

        // you must specify content type whenever you POST
        // contentType(ContentType.JSON)
        given().
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                body(spartan).
        when().
                post("/spartans").prettyPeek().
        then().assertThat().
                statusCode(201).
                body("success", is("A Spartan is Born!")).
                body("data.name", is("John Deer")).
                body("data.gender", is("Male"));; // 11
        // run -> when it succeeds, it shows status code 201 (HTTP/1.1 201)
        //  and data of #11.
        // In the response, we have spartan object inside data variable to get
        //  properties we need to specify name of that object data.
        // put . and parameter that we want to read:
        //  data.id, data.gender, data.name
        // success - property, string variable.
        // data - object that represents spartan.
    }

    @Test
    @DisplayName("update spartan, only name PATCH")
    public void test3(){ // 13
        Map<String, Object> update = new HashMap<>(); // 14

        update.put("name", "John Deer"); // 16
        // run -> success -> the Spartan website changes the name of id 787
        //  to John Deer.
        update.put("gender", "Female"); // 17

        given().
                contentType(ContentType.JSON).
             //   accept(ContentType.JSON).
             // after run this test, there is no response. Sinse
             //  response doesn't contain body, after PATCH request,
             //  we don't need 'accept(ContentType.JSON)'.
                body(update).pathParam("id", 787).
        when().
                patch("/spartans/{id}").prettyPeek().
        then().assertThat().
                statusCode(204); // 15
        // PUT: all parameters
        // PATCH: 1+ parameters



    }



}
