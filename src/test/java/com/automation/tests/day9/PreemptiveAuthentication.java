package com.automation.tests.day9; // 020320


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


import com.automation.utilities.ConfigurationReader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;

public class PreemptiveAuthentication {

    @BeforeAll
    public static void setup(){ // 1
        // https will not work because of SSL certificate issues
        // this website doesn't have it.
        baseURI = "http://practice.cybertekschool.com"; // 2
    }

    @Test
    @DisplayName("Normal basic authentication")
    public void test1(){
        // actually, it will make 2 calls:
        //  1st: with no credentials, then will get 401,
        //  to ensure that only requested server will get credentials
        //  2nd: with credentials.
        given().
                auth().basic("admin", "admin").
        when().
                get("/basic_auth").prettyPeek().
        then().assertThat().statusCode(200); // 3
    }


    @Test
    @DisplayName("Preemptive authentication")
    public void test2(){
        given().
                auth().preemptive().basic("admin", "admin").
        when().
                get("/basic_auth").prettyPeek().
                then().assertThat().statusCode(200);  // 4

    }
}
