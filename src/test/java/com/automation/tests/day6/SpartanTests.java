package com.automation.tests.day6; // 013020

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
        // web service may return different content type and to request
        // JSON, you can just put in the given part ContentType.JSON
        // If you want to ask for XML, you can put ContentType.XML
        //  but, if web service configured only for JSON, it will not
        //  give you anything else.
        // GET, POST, PUT, DELETE, etc: HTTP verbs, or methods
        // GET: to get the data from web service
        // PUT: update existing record
        // DELETE: delete something like delete spartan
        // PATCH: partial update of existing record
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

    We can convert payload (JSON body for example) into collection.
    If it's a single variable -> "name" :"James", we can store in String or List<String>
    If there are multiple names in the payload, we cannot use single String as a storage.
    Instead, use List<String>
    If payload returns object:
        {
        "name" : "James"
        "age"  : 25
        }
        Then, we can store this object (on our, java side, as POJO or Map<String, ?>)

    If it's a POJO, we need to create corresponding POJO class, in order to map properties
     from json and java object:
                Java class          JSON file
        private String name     |    "name"
        private int age         |    "age"

    If you want to use different variable name in Java class, use @SerializedName annotation.
                Java class              JSON file
        @SerializedName("name")
        private String firstName     |    "name"
        private int age              |    "age"

        otherwise, Gson, jackson, or any other Json parser, will not be able to
         map properties correctly.
         Serialization: from POJO (java object) to stream of bytes, let's say JSON
         Deserialization: from stream of bytes, let's say JSON into POJO (java object)

    If payload returns array of objects,

         [
                   {
                       "id": 202,
                       "name": "Helen Highwater",
                       "gender": "Female",
                       "phone": 60242012223
                   },
                   {
                       "id": 203,
                       "name": "Ellie Noise",
                       "gender": "Female",
                       "phone": 37812781233
                   },
               ]

        Then we can store this payload as List<Map<?, ?>>>
        or like list of POJO's List<Spartan>
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
    @DisplayName("Add 10 test users to Spartan app")
    public void test8(){ // 45
        Faker faker = new Faker(); // 46
        for (int i=0; i < 10; i++){ // 47
            Spartan spartan = new Spartan(); // 48
            spartan.setName(faker.name().firstName()); // 49
            // set name as faker
            String phone = faker.phoneNumber().subscriberNumber(12).replaceAll("\\D", ""); // 50
            // \\D -> all digit (0-9), "" -> empty. Replace all digits to empty
            // ex: phone.matches("\\d") -> checks if this string contains only digits
            // ex: phone.matches("[a-x]") -> checks if this string contains letters
            //  in the range from a to x
            // remove all digits
            // replaceAll(): takes regex (regular expression)
            // regex: it's a pattern, means that one character can represent group or chars/ symbols/ digits

            spartan.setPhone(Long.parseLong(phone)); // 50
            // convert it from String to Long
            spartan.setGender("Female"); // 51

            System.out.println(spartan); // 54

            Response response = given().
                    contentType(ContentType.JSON).
                    body(spartan).
            when().
                    post("/spartans").prettyPeek(); // 52

            System.out.println(response.jsonPath().getString("success")); // 54
            // whenever you successfully add new spartan you will get this message:
            //  "A Spartan is Born!"
            assertEquals(201, response.getStatusCode()); // 53
        } // error: "Phone number should be at least 10 digit and unique"
        // 201 here -> post request went well
    }

    @Test
    @DisplayName("Update spartan")
    // update ID: 437 with some information
    public void test9(){
        Spartan spartan = new Spartan().
                                withGender("Male").
                                withName("Mister Twister").
                                withPhone(9999999999L); // 55

        Response response = given().
                                    accept(ContentType.JSON).
                                    // what we expect

                                    contentType(ContentType.JSON).
                                    // what we send to the website

                                    body(spartan).
                                    pathParam("id", 437).
                            put("/spartans/{id}").prettyPeek(); // 56
        // run -> go to the Spartan website -> 437 Vasyl is changed to 437 Mister Twister.
        // PUT: update existing record. Also, when you make PUT request, you need to specify
        //  entire body.
        // POST: create a new one
        // We never POST/ PUT id; it must be auto-generated. If it's not like this -> a bug!
        // If the contentType in the given() is: contentType(ContentType.JSON) -> you tell
        //  the web service what data you are sending.

    }

    @Test
    @DisplayName("Update only name with PATCH")
    public void test10(){ // 57
        Map<String, String> update = new HashMap<>(); // 58
        update.put("name", "SDET"); // 59
        // to change the phone number: in #58, Map<String, Long> update = new HashMap<>();
        //  #59: update.put("phone", "10000000000L");
        // The phone number of id 380 will be changed to 10000000000L

        Response response = given().
                                accept(ContentType.JSON).
                                contentType(ContentType.JSON).
                                body(update).
                                pathParam("id", 380).
                // 380: the user id in Spartan website
                                patch("/spartans/{id}"); // 60

        response.prettyPeek(); // 61
        // run -> id 380 has name, "SDET"
        // POST: add new spartan
        // PUT: update existing one, but you have to specify all properties
        // PATCH: update existing one, but you may specify one or more parameters to update.

    }


}
