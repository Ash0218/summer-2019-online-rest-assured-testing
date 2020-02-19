package com.automation.tests.day3; // 012420

import com.automation.utilities.ConfigurationReader;
import com.sun.xml.xsom.impl.Ref;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeAll;

import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
        and().assertThat().body("employee_id", is(100),
            // body ("phone_number": 515.123.4567) -> this is the data after run this class.
            //  this is coming from "import static org.hamcrest.Matchers.*;
                "department_id", is(90),
                                        "last_name", is("King")).
        // if the value does not match, it fails.
                log().all(true); // 4

    }

    /*
    Task:
        given path parameter is "/regions/{id}"
        when user makes get request
        and region id equals to 1
        then assert that status code is 200
        and assert that region name is Europe
     */

    @Test
    public void test3(){ // 5
        given().
                accept("application/json").
                pathParam("id", 1).
                when().
                        get("/regions/{id}").
                // id here is from 1 b.c of the pathParam.
                then().
                        assertThat().statusCode(200).
                        assertThat().body("region_name", is("Europe")).
                        time(lessThan(10L), TimeUnit.SECONDS).
                    // verify that response time is less than 10 seconds
                        log().body(true); // 6
                    // same as "pretty print". all = header + body + status code
    }

    // task: verify only specific objects
    @Test
    public void test4(){ // 7
        JsonPath json = given().
                accept("application/json").
        when().
                get("/employees").
        thenReturn().jsonPath(); // 8

        // items[employee1, employee2, employee3] | items[0] = employee1.first_name = Steven

        String nameOfFirstEmployee = json.getString("items[0].first_name"); // 9
        String nameOfLastEmployee = json.getString("items[-1].first_name"); // 11
        // -1 -> last index

        System.out.println("First employee name: "+ nameOfFirstEmployee); // 10
        // if it works properly, it shows: "First employee name: Steven"

        System.out.println("Last employee name: "+ nameOfLastEmployee); // 12
        // if it works properly, it shows: "Last employee name: Kevin"

       // in JSon, employee looks like object that consists of params and their values.
        // we can parse that json object and store in the map.
        Map<String, ?> firstEmployee = json.get("items[0]"); // 13
        // <String, String> -> does not specify data.
        // it can be <String, ?> -> ? can be String or not
        System.out.println(firstEmployee); // 14
        // if it works properly, the data showed like a map. ex: {employee_id=100, first_name=Steven, ...}

        // Since firstEmployee is a map (key-value pair), we can iterate through it by using Entry. entry
        //  represents one key-value pair.
        for (Map.Entry<String, ?> entry : firstEmployee.entrySet()) { // 15
            // <String, ?> -> ? includes every values like String, integer, etc. If you put
            //  <String, String> then you can get castingException which cannot convert
            //  from integer (or something else) to String value.
            System.out.println("key: " + entry.getKey()+", value: "+ entry.getValue()); // 16
        // if it works properly, it shows: key: employee_id, value: 100 ...
        }

        // An item is an object. Whenever you need to read some property from the object, you put
        //  object.property but, if response has multiple objects, we can get property from every
        //  object.
        // get and print all last names:
        List<String> lastNames = json.get("items.last_name"); // 17
        for (String str : lastNames) { // 18
            System.out.println("Last employee: "+ str); // 19
            // if no error, it shows: Last name: [King]
            //                        Last name: [Kohhar] ...
        }

    }

    // Write code to
    // get info from countries as List<Map<String, ?>>
    // prettyPrint() -> print json/xml/html in nice format and returns String, thus
    //  we cannot retrieve without extraction ...
    // prettyPeek() -> does the same job, but return Response object, and from that
    //  object, we can get json path.
    @Test
    public void test5(){ // 20
        JsonPath json = given().
                accept("application/json").
                when().
                get("/countries").prettyPeek().jsonPath(); // 21
        // prettyPeek() -> returns response, and from the response, we can
        //  get info about the response object. If you use prettyPrint(), you
        //  can't use jsonPath(). prettyPeek() get the response, so we can use
        //  it to continue with the object.
        // If you exclude prettyPeek(), then you will not see detailed info about response.

        List<HashMap<String, ?>> allCountries = json.get("items"); // 22
        System.out.println(allCountries); // 23
        // it shows:
        //  "items": [
        //      {
        //          "country_id": "AR", ...

        // When we read data from json response, values are not only Strings, so
        //  if we are not sure that all values will have same data type, we
        //  can put ?.
        for (HashMap<String, ?> map: allCountries){ // 24
            System.out.println(map); // 25
            // it shows: {country_id=CA, ...}
        }
    }

    // get collection of employees' salaries then sort it and print it
    @Test
    public void test6(){ // 26
        List<Integer> salaries = given().
                                        accept("application/json").
                                when().
                                        get("/employees").
                                thenReturn().jsonPath().get("items.salary"); // 27
      //  Collections.sort(salaries); // 28
        // sort from small to large

        Collections.reverse(salaries); // 30
        // sort from large to small
        System.out.println(salaries); // 29
        // it shows: [2500, ...]
    }


    // get collection of phone numbers from employees and replace
    //  all dots "." in every phone number with dash "-"
    @Test
    public void test7(){ // 31
        List<String> phoneNumbers=given().
            accept("application/json").
            when().get("/employees").
            thenReturn().jsonPath().get("item.phone_number"); // 32
        // it calls Gpath (GroovyPath), like Xpath(XMLpath).

        // Replace each element of this list with the result of applying
        //  the operator to that element.
        // replace "." with "-" in every value
        phoneNumbers.replaceAll(p -> p.replace(".", "-")); // 34
        System.out.println(phoneNumbers); // 33
    }


    /* Task:
    Given accept as Json
    And path parameter is id with value 1700
    When user sends get request to /locations
    Then user verifies that status code is 200
    And user verifies json path information:
        |location_id|postal_code|city   |state+province|
        |1700       |98199      |Seattle|Washington     |
     */
    @Test
    public void test8(){ // 35
        Response response = given().
            accept(ContentType.JSON).
            pathParam("id", 1700).
        when().
            get("/locations/{id}"); // 36

        response.
            then().
                assertThat().body("location_id", is(1700)).
                assertThat().body("postal_code", is("98199")).
                assertThat().body("city", is("Seattle")).
                assertThat().body("state_province", is("Washington")).
                log().body(); // 37
    }
}
