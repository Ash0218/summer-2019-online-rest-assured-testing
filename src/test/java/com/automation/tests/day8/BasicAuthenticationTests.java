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

public class BasicAuthenticationTests {

    @BeforeAll
    public static void setup(){ // 1
        baseURI = "http://practice.cybertekschool.com"; // 2
        // https will not work b.c of SSL certification issues.
        //  this website doesn't have it.
    }


    @Test
    @DisplayName("basic authentication test")
    public void test1(){ // 3
        given().
                auth().basic("admin", "admin").
        when().
                get("/basic_auth").prettyPeek().
        then().assertThat().statusCode(200); // 4

    }
}
