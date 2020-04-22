package com.automation.tests.day9; // 020420


import com.automation.pojos.Room;
import com.automation.utilities.APIUtilities;
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


import com.automation.utilities.ConfigurationReader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;

public class BookItTests {

    @BeforeAll
    public static void setup(){ // 1
        baseURI = ConfigurationReader.getProperty("bookit.qa1"); // 2
    }

    /*
    Given accept content type as JSON
    When user sends get requests to /api/rooms
    Then user should get 401 status code
     */

    @Test
    @DisplayName("Verify that user cannot access bookit API without providing credentials")
    public void test1(){ // 3
        given().
                accept(ContentType.JSON).
        when().
                get("/api/rooms").
        then().assertThat().statusCode(401).log().all(true); // 4
        // Run -> shows 422 instead of 401: talk to the developers and check business requirement.

    }


    /*
    Given accept content type as JSON
    And user provides invalid token
    When user sends get requests to /api/rooms
    Then user should get 422 status code
     */

    @Test
    @DisplayName("Verify that system doesn't accept invalid token")
    public void test2(){ // 5
        given().
                accept(ContentType.JSON).
                header("Authorization", "invalid token").
          //      auth().oauth2("invalid_token").
                // header("Authorization", getToken()).
                // auth().oauth()
                //  these are same procedures. You need to provide token
                //  since bearer token was originally created for oauth 2.0.
                //  It works in the same way.
        when().
                get("/api/rooms").prettyPeek().
        then().assertThat().statusCode(422); // 6
        // Run -> it shows 500 status code: server is in trouble

    }


    /*
    Given valid bearer token
    When user performs GET request to "/api/rooms"
    Then user should get list of rooms in the payload
    And status code 200
     */
    @Test
    public void test3(){ // 7
        given().auth().oauth2(APIUtilities.getTokenForBookit()).
                accept(ContentType.JSON).
                when().
                get("/api/rooms").prettyPeek(); // 8
        // run -> you get the accessToken -> copy it -> go to Postman ->
        //  Bookit -> GET -> Authorization -> Type: OAuth 2.0 -> paste it
        //  to Access Token -> click Headers and you will see the copied AccessToken

    }


    @Test
    @DisplayName("Get all roms and deserialize it into collection of Rooms")
    public void test4(){
        //in real work environment, common practice is to authenticate with SSL certificate
        //you add SSL certificate on your side, with every request
        //and then you can work with web service
        Response response = given().auth().oauth2(APIUtilities.getTokenForBookit()).
        // auth()-> identify authentication -> oauth2() -> then, what kind of authentication.
                accept(ContentType.JSON).
                when().
                get("/api/rooms").prettyPeek();
        List<Room> rooms = response.jsonPath().getList("", Room.class);
        // Room -> from Room.java in pojos package. If you use "transient" at id in Room.java, you will see id=0 here.
        for (Room room : rooms) {
            System.out.println(room.getName());
        }
    }



}
