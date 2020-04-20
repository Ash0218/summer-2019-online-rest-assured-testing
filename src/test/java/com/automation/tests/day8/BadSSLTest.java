package com.automation.tests.day8; // 020320


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

public class BadSSLTest {
    @BeforeAll
    public static void before(){ // 1
        baseURI = "https://untrusted-root.badssl.com/"; // 2
    }

    @Test
    @DisplayName("Access web site with bad SSL")
    public void test1(){ // 3
        Response response = get().prettyPeek(); // 4
        // it shows: javax.net.ssl.SSLHandshakeException -> b.c there is no
        //  SSL certificate, there is no handshake. If web site cannot provide
        //  valid certificate, secured connection is not possible. Client will
        //  reject to exchange information by default.

        System.out.println(response.statusCode()); // 5
        // there is no status code b.c there was no exchange of information at all.
    }


    @Test
    @DisplayName("Access web site with bad SSL (solution)")
    public void test2(){ // 6
        Response response = given().relaxedHTTPSValidation().get().prettyPeek(); // 7
        // relaxedHTTPSValidation() -> ignores SSL issues

        System.out.println(response.statusCode()); // 8

        assertEquals(200, response.getStatusCode()); // 9

    }
}
