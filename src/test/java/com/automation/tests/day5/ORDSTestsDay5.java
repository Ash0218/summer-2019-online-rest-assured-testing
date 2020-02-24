package com.automation.tests.day5; // 012820

import com.automation.utilities.ConfigurationReader;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;

public class ORDSTestsDay5 { // 1
    @BeforeAll
    public static void setup() {baseURI = ConfigurationReader.getProperty("ords.uri"); // 2
    }

    /*
    Warm up:
        given path parameter is "/employees"
        when user makes get request
        then user verifies that status code is 200
        and user verifies that average salary is greater than 5000
     */

}
