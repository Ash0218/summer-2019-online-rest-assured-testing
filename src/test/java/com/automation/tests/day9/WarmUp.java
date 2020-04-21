package com.automation.tests.day9; // 020420


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

/*
-	Warm up:
o	Given accept content type as JSON
o	And query parameter api_key with valid API key
o	When user sneds GET request to “/countries”
o	Then user verifies that total number of holidays in United Kingdom is equals to 95
o	Website: https://calendarific.com/

 */

public class WarmUp {

    @BeforeAll
    public static void setup(){ // 1
        baseURI = ConfigurationReader.getProperty("bookit.qa1"); // 2
    }


    @Test
    @DisplayName("verifies that total number of holidays in United Kingdom is equals to 95")
    public void TC1(){
        Response response=
                given().accept(ContentType.JSON).
                        queryParam("api_key","aaf312cd592b0c7b22d90dd82e805dd4cf182dd5")
                        // api_key is the key given from the website, the value is my api key
                        .when().get("/countries").prettyPeek();
        response.then().assertThat().statusCode(200);
        //Gpath - it's like Xpath, stands for searching values in JSON file.
        //GPath: response.countries.find {it.country_name == 'United Kingdom'}.total_holidays
        //find - method to find some parameter
        //{it.parameter_name == value} find JSON object that is matching criteria
        //.parameter_that_you_want = return this parameter after filtering
        int numberHolidays = response.jsonPath().get("response.countries.find {it.country_name == 'United Kingdom'}.total_holidays");

        //get all countries where number supported_languages is = 4
        List<String> countries = response.jsonPath().get("response.countries.findAll {it.supported_languages == 4}.country_name");
        // if it has supported language = 4, then get the country names.

        System.out.println(countries);

        assertEquals(95,numberHolidays);
        System.out.println("MY TESTS PASSED SUCCESSFULLY😍😍");
    }

    @Test
    @DisplayName("user verifies that total number of holidays in United Kingdom is equals to 95")
    public void TC1_2(){
        Response response=
                given().accept(ContentType.JSON).
                        queryParam("api_key","aaf312cd592b0c7b22d90dd82e805dd4cf182dd5")
                        .queryParam("country","GB")
                        .queryParam("year",2019)
                        .when().get("/holidays").prettyPeek();
        response.then().assertThat().statusCode(200);
        List<Map<String,?>> holidays=response.jsonPath().get("response.holidays");
        assertEquals(95,holidays.size());
        System.out.println("MY TESTS PASSED SUCCESSFULLY😍😍");
    }








}
