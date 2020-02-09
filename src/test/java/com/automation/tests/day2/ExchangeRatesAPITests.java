package com.automation.tests.day2; // 012320

import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class ExchangeRatesAPITests {
    private String baseURI = "http://api.openrates.io"; // 1

    @Test
    public void test1(){ // 2
        Response response = given().baseUri(baseURI+"latest").get(); // 3
        assertEquals(200, response.getStatusCode()); // 4
    }
}
