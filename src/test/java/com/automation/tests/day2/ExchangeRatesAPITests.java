package com.automation.tests.day2; // 012320

import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class ExchangeRatesAPITests {
    private String baseURI = "http://api.openrates.io"; // 1

    @Test
    public void test1(){ // 2
        Response response = given().get(baseURI+"/latest"); // 3
        // Verify status code:
        assertEquals(200, response.getStatusCode()); // 4
        // 200 means the request has succeeded.
        System.out.println(response.prettyPrint()); // 5
    }


    @Test
    public void test2(){ // 6
        Response response = given().get(baseURI+"/latest"); //7
        // Verify that content type is json:
        assertEquals(200, response.getStatusCode()); // 8
        // Verify that data is coming as json:
        assertEquals("application/json", response.getHeader("Content-Type")); // 9
        // or like this:
        assertEquals("application/json", response.getContentType()); // 10
    }


    // Task: get currency exchange rate for dollar. By default, it's euro.
    // GET https://api.exchangeratesapi.io/latest?base=USD HTTP/1.1
    // base is a query parameter that will ask web service to change currency
    //  from euro to something else.
    @Test
    public void test3(){ // 11
    // Response response = given().get(baseURI+"/latest?base=USD"); // 12
    // or use this(#15) instead of #12:
        Response response = given().baseUri(baseURI).
                basePath("latest").
                queryParam("base", "USD").get(); // 15

        assertEquals(200, response.getStatusCode()); // 13
        System.out.println(response.prettyPrint()); // 14

    }


    // Task: Verify that response body for latest currency rates which
    //  contains today's date.
    @Test
    public void test4(){ // 16
        Response response = given().
                baseUri(baseURI+"/latest").
                queryParam("base", "GBP").
                get(); // 17

        String todaysDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")); // 19

        System.out.println("Today's date: " + todaysDate); // 20

        assertEquals(200, response.getStatusCode()); // 21
        assertTrue(response.getBody().asString().contains("2020-02-10")); // 18
    }


    // Task: get currency exchange rate for year 2000.
    //  GET https://api.exchangeratesapi.io/history?start_at=2018-01-01&end_at=2018-09-01&base=USD&symbols=ILS.JPY
    @Test
    public void test5(){ // 22
        Response response = given().
        baseUri(baseURI+"/history").
        queryParam("start_at", "2000-01-01").
        queryParam("end_at", "2000-12-31").
        queryParam("base", "USD").
        queryParam("symbols", "EUR","JPY","GBP").
        get(); // 23

        System.out.println(response.prettyPrint()); // 24
    }

}
