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

public class BearerTokenTestsWithBookit {

    @BeforeAll
    public static void setup(){ // 1
        baseURI = ConfigurationReader.getProperty("bookit.qa1"); // 2
    }


    // Let's get the list of all rooms and verify that status code is 200


    public String getToken(){ // 3
        ////https://cybertek-reservation-api-qa.herokuapp.com/sign?email=vasyl@cybertekschool.com&password=cybertek2020
        Response response = given().
                                queryParam("email", ConfigurationReader.getProperty("team.leader.email")).
                                // "team.leader.email" is from configuration.properties
                                queryParam("password", ConfigurationReader.getProperty("team.leader.password")).
                            when().
                                get("/sign").prettyPeek(); // 4

        return response.jsonPath().getString("accessToken"); // 5
        // we have to add token every time

    }
}
