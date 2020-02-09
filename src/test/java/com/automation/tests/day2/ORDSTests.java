package com.automation.tests.day2; // 012320

import io.restassured.RestAssured;
import io.restassured.http.Header;
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

    // According to OOP conventions, all instance variable should be private
    //  but, if we will make it public, it will be make any difference for us.
    // It's just good practice, so later, we will not hesitate which keyword to use
    //  when it's gonna be important

    @Test // 2
    // verify that status code is 200
    // We start from "given()", then we can specify what type of request
    //  like: get(), put(), delete(), post()... And as a parameter, we
    //  enter resource location (URI). Then, we are getting response back.
    //  That response we can put into Response object. From response
    //  object, we can retrieve: body, header, status code.
    // -> it works without RestAssured() b.c of
    //  static import.
    public void test1(){ // 1
        // run to check if it's working

        Response response = given().
                get("http://ec2-52-207-227-74.compute-1.amazonaws.com:1000/ords/hr/employees"); // 6
        System.out.println(response.getBody().asString()); // 7
        // run

        assertEquals(200, response.getStatusCode()); // 8
        System.out.println(response.prettyPrint()); // 9
        // it makes the jason response pretty (easy to see)

    }

    // Task: get employee with id 100 and verify that status code is 200
    //  Also, print body
    @Test // 10
    public void test2(){ // 11
        Response response = given().
                header("Accept", "application/json").
                // header stands for meta data. Usually, it is used to
                //  include cookies. In this example, we are specifying what
                //  kind of response type we need b.c web service can return
                //  json or xml when we put header info "Accept", "application/json".
                //  We are saying that we need only json as response.
                // You can use "accept" instead of "Accept"
                get(baseURI+"/employees/100"); // 12
                 // employee in ID 100 -> path parameter.

        int actualStatusCode = response.getStatusCode(); // 13
        System.out.println(response.prettyPrint()); // 15
        assertEquals(200, actualStatusCode); // 14
        // it must return 200 (code of success) or it fails

        // get information about response content type, you can
        //  retrieve from response object.
        System.out.println("What kind of content server sends to you, in this response: "
                + response.getHeader("Content-Type")); // 15
        // it shows: What kind of content server sends to you, in this response:
        //  application/json

    }

    // Task: Perform GET request to /regions, print body and all headers.
    @Test
    public void test3(){ // 16
        Response response = given().get(baseURI+"/regions"); // 17
        assertEquals(200, response.getStatusCode()); // 18
        // to get specific header:
        Header header = response.getHeaders().get("Content-Type"); // 19
        // Print all headers one by one:
        for (Header h: response.getHeaders()){ // 20
            System.out.println(h); // 21
        }
        System.out.println(response.prettyPrint()); // 22
    }

}
