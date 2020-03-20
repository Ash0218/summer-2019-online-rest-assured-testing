package com.automation.tests.day6; // 013020

import com.automation.pojos.Spartan;
import com.automation.utilities.ConfigurationReader;
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

public class SpartanTests {

    @BeforeAll
    public static void setup(){ // 1
        baseURI = ConfigurationReader.getProperty("spartan.uri"); // 2
    }

    /*
    Task 1:
    given accept content type as JSON
    when user sends GET request to /spartans
    then user verifies that status code is 200
    and user verifies that content type is JSON
     */
    @Test
    @DisplayName("Verify that /spartans end-point returns 200 and content type as JSON")
    public void test1(){ // 3
        given().
                accept(ContentType.JSON).
        when().
                get("/spartans").prettyPeek().
        then().assertThat().
                statusCode(200).
                contentType(ContentType.JSON); // 4

    }


    /*
    Task 2:
    given accept content type as XML
    when user sends GET request to /spartans
    then user verifies that status code is 200
    and user verifies that content type is XML
     */
    @Test
    @DisplayName("Verify that /spartans end-point returns 200 and content type as XML")
    public void test2(){ // 5
        // asking:
        // accept(ContentType.XML) -> you are asking for payload format as XML from web service
        // receiving:
        // contentType(ContentType.XML) -> you are verifying that payload's format is XML
        given().
                accept(ContentType.XML).
                when().
                get("/spartans").prettyPeek().
                then().assertThat().
                statusCode(200).
                contentType(ContentType.XML); // 6

    }


    /*
    Task 3:
    given accept content type as XML
    when user sends GET request to /spartans
    then user saves payload into collection
     */
    @Test
    @DisplayName("Save payload into java collection")
    public void test3(){ // 7
        Response response = given().
                                contentType(ContentType.JSON).
                            when().
                                get("/spartans"); // 8

        List<Map<String, ?>> collection = response.jsonPath().get(); // 9

        for (Map<String, ?> map : collection){ // 9
        //    System.out.println(map); // 10
            System.out.println(map.get("phone")); // 11
            // only phone number
        }

    }


    /* After adding Spartan in pojos package,
    Task 4:
    given accept content type as XML
    when user sends GET request to /spartans
    then user saves payload into collection of Spartan (in pojos package)
     */
    @Test
    @DisplayName("Save payload into java collection of Spartan")
    public void test4(){ // 12
        Response response = given().
                                contentType(ContentType.JSON).
                            when().
                                get("/spartans").prettyPeek(); // 13

        // whenever you see: Class object = response.jsonPath.getObject() | deserialization
        List<Spartan> collection = response.jsonPath().getList("", Spartan.class); // 14
        // used "getList" instead of "get" b.c get doesn't know how to convert pojos into Map or List, so
        //  it shows hashcodes. getList can coverts pojos into List. And the data type is Spartan.
        // "" -> doesn't have anything b.c in the pojo, the first "[" in the result has no data.

        for (Spartan spartan : collection){ // 15
            System.out.println(spartan); // 16
        }
    }


    /*
    Task 5:
    given accept content type as JSON
    when user sends POST request to /spartans
    then user should be able to create new spartan
        |gender|name          |phone      |
        | male |Mister Twister| 5712134235|

    then user verifies that status code is 201

    201 - mean created. Whenever you POST something, you should get back 201 status code
     in case of created record.
     */
    @Test
    @DisplayName("Create new spartan and verify that status code is 201")
    public void test5(){ // 17
        // #22: created after #5-13 in Spartan.java
        // builder pattern: one of the design patterns in OOP
        // Instead of having too many different constructors, we can use
        //  builder pattern and chain with {propertyName} methods to specify properties of an object
        Spartan spartan1 = new Spartan().
                withGender("Male").
                withName("Some User").
                withPhone(5712134235L); // 22


        Spartan spartan = new Spartan(); // 18
        spartan.setGender("Male"); // 19
        // Male or Female
        spartan.setName("Mister Twister"); // 20
        spartan.setPhone(5712134235L); // 21
        // at least 10 digits


        Response response = given().
                                contentType(ContentType.JSON).
                                body(spartan1).
                            when().post("/spartans"); // 23
        assertEquals(201, response.getStatusCode(), "Status code is wrong!"); // 24
        assertEquals("application/json", response.getContentType(), "Content type is invalid!"); // 25
        assertEquals(response.jsonPath().getString("success"), "A Spartan is Born!"); // 27

        response.prettyPrint(); // 26

        // #27, 28 shows the created data from above
        Spartan spartan_from_response = response.jsonPath().getObject("data", Spartan.class); // 27

        System.out.println("Spartan id: "+spartan_from_response.getSpartanId()); // 28

        // delete spartan that you just created above
      //  when().delete("/spartans/{id}", spartan_from_response.getSpartanId()).
      //          prettyPeek().
       //         then().assertThat().statusCode(204); // 29
        // 204 means no content
    }

    // created spartan.json file under target, and APIUtilities under utilities

    @Test
    @DisplayName("Delete user")
    public void test6(){ // 30
        int idOfTheUserThatYouWantToDelete = 125; // 31

        Response response = when().delete("/spartans/{id}", idOfTheUserThatYouWantToDelete); // 32

        response.prettyPeek(); // 33
        // go to All Spartan webpage and Add Spartan
    }


    @Test
    @DisplayName("Delete half of the records")
    public void test7() { // 34
        int idOfTheUserThatYouWantToDelete = 125; // 35
        Response response = given().
                accept(ContentType.JSON).
                when().
                get("/spartans"); // 38
        List<Integer> userIDs = response.jsonPath().getList("id"); // 39
        // collected all user IDs

        Collections.sort(userIDs, Collections.reverseOrder()); // 41
        // sorted user IDs in descending order from biggest to smallest
        System.out.println("Before: "+userIDs); // 44

        for (int i=0; i< userIDs.size()/2; i++){ // 42
            // userIDs.size()/2 -> represents half of the spartans
            when().delete("/spartans/{id}", userIDs.get(i)); // 43
        } // went through half of the collection, and deleted half of the users
          // this will delete spartan based on id that you specify

        System.out.println("After: " +userIDs); // 40
        //    Response response2 = when().delete("/spartans/{id}", idOfTheUserThatYouWantToDelete); // 36

        //    response.prettyPeek(); // 37


    }

    @Test
    @DisplayName("Get all spartan ids and print it as list")
    public void test8(){

    }

}
