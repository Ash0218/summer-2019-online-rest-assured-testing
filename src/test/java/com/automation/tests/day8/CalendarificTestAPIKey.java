package com.automation.tests.day8;

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



}
