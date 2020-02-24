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
    @Test
    @DisplayName("verify that average salary is greater than 5000")
    public void test1(){ // 3
        Response response = given().
                                    accept(ContentType.JSON).
                            when().
                                    get("/employees"); // 4

        JsonPath jsonPath = response.jsonPath(); // 5

        List<Integer> salaries = jsonPath.getList("items.salary"); // 6

        int sum = 0; // 7
        for (int salary : salaries) { // 8
            sum += salary; // 9
        } // we are finding a sum of all salaries

        int avg = sum / salaries.size(); // 10
        // we are calculating average: salary sum/ count

        assertTrue(avg > 5000, "ERROR: actually average salary is lower than 5000: "+avg); // 11
        // we are asserting that average salary is greater than 5000
    }
}
