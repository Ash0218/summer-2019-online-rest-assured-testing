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


    @Test
    @DisplayName("Verify that B12 exists")
    public void test5_1() {
        // end point: /api/batches/{batch-number} -> http://cybertek-reservation-api-docs.herokuapp.com/#get-a-batch-by-number
        given().
                auth().oauth2(APIUtilities.getTokenForBookit()).
                when().
                get("/api/batches/{batch-number}", 12).
                then().assertThat().statusCode(200).log().body(true);

    }


//    campus-location	required	name of the campus which team will be added to
//    batch-number	required	number of the batch which team will be added to
//    team-name	required	name of the team, should be uniq per campus

    /**
     * given valid token is provided for student team leader
     * and user provides following query parameters
     * |campus-location|batch-number|team-name     |
     * |     VA        |    12      |Online_Hackers|
     * when user performs POST request to "/api/teams/team"
     * then user should verify that status code is 403
     */
    @Test
    @DisplayName("Create a new team in B12 (negative)")
    public void test6() {
        //Online_Hackers
//    end point to create a team:    POST /api/teams/team
        given().
                auth().oauth2(APIUtilities.getTokenForBookit()).
                queryParam("campus-location", "VA").
                queryParam("batch-number", 12).
                queryParam("team-name", "Online_Hackers").
        when().
                post("/api/teams/team").
                then().assertThat().statusCode(403).log().all(true);

        //only teacher allowed to modify database. <---- authorization
        // 403 Forbidden - that means you are not authorized to do this
    }

    /**
     * given valid token is provided for teacher
     * and user provides following query parameters
     * |campus-location|batch-number|team-name     |
     * |     VA        |    12      |Online_Hackers|
     * when user performs POST request to "/api/teams/team"
     * then user should verify that status code is 201
     */

    @Test
    @DisplayName("Create a new team in B12 (positive)")
    public void test7() {
        //Online_Hackers
//        POST /api/teams/team
        given().
                auth().oauth2(APIUtilities.getTokenForBookit("teacher")).
                queryParam("campus-location", "VA").
                queryParam("batch-number", 12).
                queryParam("team-name", "Online_Hackers").
                when().
                post("/api/teams/team").
                then().log().all(true);

        //only teacher allowed to modify database. <---- authorization
    }

    //test: add yourself to the team
    /**
     *  {
     *             "id": 5443,
     *             "name": "Online_Hackers",
     *             "members": [
     *
     *             ]
     *         },
     */
    /**
     * POST /api/students/student
     * <p>
     * Query Parameters
     * Parameter	    Demand	    Description
     * first-name	    required	first name of the student
     * last-name	    required	last name of the student
     * email	        required	email of the student, will be used for an authentication
     * password	        required	password of the account, will be used for an authentication
     * role      	    required	role of the student, [student-team-leader, student-team-member]
     * campus-location	required	name of the campus which student will be added to
     * batch-number	    required	number of the batch which student will be added to
     * team-name	    required	name of the team which student will be added to
     * <p>
     * given valid token is provided for student team leader
     * and user provides following query parameters
     * |first-name  |last-name    |email         |password    |role               |campus-location|batch-number|team-name      |
     * |    YourName|YourLastName |temp@email.com| anypassword|student-team-member|      VA       |    12      | Online_Hackers|
     * when user performs POST request to "/api/students/student"
     * then user should verify that status code is 403
     */

    @Test
    @DisplayName("Create a new student (negative test)")
    // use the HTTP request url in http://cybertek-reservation-api-docs.herokuapp.com/#add-a-student
    // it has query parameter
    public void test5() {
        given().
                auth().oauth2(APIUtilities.getTokenForBookit()).
                queryParam("first-name", "Vasyl").
                queryParam("last-name", "Fomiuk").
                queryParam("email", "baxex65713@hiwave.org").
                queryParam("password", "1234").
                queryParam("role", "student-team-leader").
                queryParam("campus-location", "VA").
                queryParam("batch-number", 12).
                queryParam("team-name", "Online_Hackers").
                when().
                post("/api/students/student").
                then().assertThat().statusCode(403).log().all(true).body("", contains("only teacher allowed to modify database."));


    }

    /**
     * POST /api/students/student
     * <p>
     * Query Parameters
     * Parameter	    Demand	    Description
     * first-name	    required	first name of the student
     * last-name	    required	last name of the student
     * email	        required	email of the student, will be used for an authentication
     * password	        required	password of the account, will be used for an authentication
     * role      	    required	role of the student, [student-team-leader, student-team-member]
     * campus-location	required	name of the campus which student will be added to
     * batch-number	    required	number of the batch which student will be added to
     * team-name	    required	name of the team which student will be added to
     * <p>
     * given valid token is provided for teacher
     * and user provides following query parameters
     * |first-name  |last-name    |email         |password    |role               |campus-location|batch-number|team-name      |
     * |    YourName|YourLastName |temp@email.com| anypassword|student-team-member|      VA       |    12      | Online_Hackers|
     * when user performs POST request to "/api/students/student"
     * then user should verify that status code is 403
     */

    @Test
    @DisplayName("Create a new student (positive test)")
    public void test8() {
        given().
                auth().oauth2(APIUtilities.getTokenForBookit("teacher")).
                queryParam("first-name", "namr").
                queryParam("last-name", "name").
                queryParam("email", "email@enayu.com").
                queryParam("password", "1234").
                queryParam("role", "student-team-leader").
                queryParam("campus-location", "VA").
                queryParam("batch-number", 12).
                queryParam("team-name", "Online_Hackers").
                when().
                post("/api/students/student").
                then().statusCode(201).log().all(true);
//        as a response, I've got this one
//        HTTP/1.1 201 Created
//        Connection: close
//        Date: Tue, 04 Feb 2020 20:22:03 GMT
//        Content-Type: application/json
//        Server: Jetty(9.4.8.v20171121)
//        Via: 1.1 vegur
//
//        user Vasyl Fomiuk has been added to database.
        // if you are getting 422, that means email already exists

    }

    //let's try to delete some student | 5486

    //DELETE /api/students/{id}

    @Test
    @DisplayName("Delete student (negative)")
    public void test9() {
        given().
                auth().oauth2(APIUtilities.getTokenForBookit()).
                when().
                delete("/api/students/{id}", 5486).
                then().assertThat().statusCode(403).log().all(true);
    }

    @Test
    @DisplayName("Delete student (positive)")
    public void test10() {
        given().
                auth().oauth2(APIUtilities.getTokenForBookit("teacher")).
                when().
                delete("/api/students/{id}", 5480).
                then().assertThat().
                statusCode(not(403)).log().all(true);
        // statusCode(not(403)) - to ensure that status code is anything but not 403
    }

    //let's find how to delete all users from Online_Hackers team
    @Test
    @DisplayName("Delete all people from Online_Hackers team")
    public void test11() {
        //I created toke only once
        //first of all, token doesn't expire that fast
        //plus, this token in bookit never expire, it's a bug(it's not a bug, it's a feature

        //if we gonna generate token for every request,
        //it will overload network.
        String token = APIUtilities.getTokenForBookit("teacher");

        Response response = given().
                auth().oauth2(token).
                when().
                get("/api/teams/{id}", 5443);

        response.then().log().all(true);
        //members.id
        //"members":  - it's an array of students
        // if you need 1st person: members[0]
        //if you need last one: members[-1]
        //collect all properties: members.property_name
        List<Integer> allIds = response.jsonPath().getList("members.id");

        System.out.println(allIds);

        for (int i = 0; i < allIds.size(); i++) {
            given().
                    auth().oauth2(token).
                    when().
                    delete("/api/students/{id}", allIds.get(i)).
                    then().assertThat().statusCode(204).log().ifError();
            //.log().ifError(); --- will print response info into console, in case of error
            //if request was successful and assertion passed
            //you will not see response output
            //why? because it's too much print in console

            System.out.println("Deleted student with id: " + allIds.get(i));

        }
    }

    @Test
    @DisplayName("Get team info")
    public void test12() {
        String token = APIUtilities.getTokenForBookit("teacher");

        Response response = given().
                auth().oauth2(token).
                when().
                get("/api/teams/{id}", 5443);

        response.then().log().all(true);
    }

    //add students by reading data from excel file

    @Test
    @DisplayName("Add new students from excel file")
    public void test13() {
        String filePath = System.getProperty("user.dir") + "/src/test/resources/test_data/bookit_test_data.xlsx";
        ExcelUtil excelUtil = new ExcelUtil(filePath, "students");

        for (Map<String, String> map : excelUtil.getDataList()) {
            System.out.println(map);
        }
        //get the data from excel file
        List<Map<String, String>> students = excelUtil.getDataList();
        //generate token
        String token = APIUtilities.getTokenForBookit("teacher");
        //loop through the collection of students
        //and add them one by one
        //we can specify query parameters one by one
        //or put them as a map
        //make sure that key names in the map
        //matching query parameters
        for(Map<String, String> student: students) {
            given().
                    auth().oauth2(token).
                    queryParams(student).
                    when().
                    post("/api/students/student").
                    then().assertThat().statusCode(201).
                    log().body(true).log().ifError();
        }
        //log body of response
    }

}
