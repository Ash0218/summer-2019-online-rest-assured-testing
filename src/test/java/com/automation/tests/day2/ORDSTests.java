package com.automation.tests.day2; // 012320

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*; // 3
import static org.junit.jupiter.api.Assertions.*; // 4

public class ORDSTests {
    private String baseURI = "http://ec2-52-207-227-74.compute-1.amazonaws.com:1000/ords/hr"; // 5
    // address of ORDS web service that is running on AWS ec2.
    // data is coming from SQL Oracle data base to this web service.
    // During back-end testing with SQL developer and JDBC API,
    //  we were accessing data base directly. Now, we're gonna
    //  access web service.

    @Test // 2
    // verify that status code is 200
    public void test1(){ // 1
        // run to check if it's working

        Response response = given().
                get("http://ec2-52-207-227-74.compute-1.amazonaws.com:1000/ords/hr/employees"); // 6
        System.out.println(response.getBody().asString()); // 7
        // run

        assertEquals(200, response.getStatusCode()); // 8


    }
}
