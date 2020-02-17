package com.automation.tests.day3; // 012420

import com.automation.utilities.ConfigurationReader;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;


public class ORDSTestsDay3 {

    @BeforeAll
    public static void setup(){ // 1
        baseURI = ConfigurationReader.getProperty("ords.uri"); // 2

    }

    // accept ("application/json") shortcut for header ("Accept", "application/json")
    //  we are asking for json as a response
    @Test
    public void test1(){ // 1
        given().
                accept("application/json").
                get("/employees").
        then().
                assertThat().statusCode(200).
                and().assertThat().contentType("application/json").
                // assert everything at once
                log().all(true); // 2
    }

    @Test
    public void test2(){ // 3
        given().accept("application/json").
            pathParam("id", 100).
            // path Parameter - specify particular (specific) resources
            //  (in ID 100): /employees/:id/ - id is a path parameter
            // query parameter filters the result, or describe some info about
            //  new resource: POST /users?name=James&age=60&job-title=SDET
            //  or, to filter: GET /employee?name=Jamal get all employees with name Jamal
        when().get("/employees/{id}").
            then().assertThat().statusCode(200).
            and().assertThat().body("employee_id", is(100)); // 4
    }
}
