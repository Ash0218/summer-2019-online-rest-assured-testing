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



}
