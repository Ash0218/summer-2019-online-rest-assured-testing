package com.automation.tests.day5; // 012820

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

public class SchoolTests {

    @BeforeAll
    public static void setup(){ // 1
        baseURI = ConfigurationReader.getProperty("school.uri"); // 2
    }

    @Test
    @DisplayName("Create and delete student")
    public void test1(){ // 3

        String json = "{\n" +
                "  \"admissionNo\": \"1234\",\n" +
                "  \"batch\": 12,\n" +
                "  \"birthDate\": \"01/01/1890\",\n" +
                "  \"company\": {\n" +
                "    \"address\": {\n" +
                "      \"city\": \"McLean\",\n" +
                "      \"state\": \"Virginia\",\n" +
                "      \"street\": \"7925 Jones Branch Dr\",\n" +
                "      \"zipCode\": 22102\n" +
                "    },\n" +
                "    \"companyName\": \"Cybertek\",\n" +
                "    \"startDate\": \"02/02/2020\",\n" +
                "    \"title\": \"SDET\"\n" +
                "  },\n" +
                "  \"contact\": {\n" +
                "    \"emailAddress\": \"james_bond@gmail.com\",\n" +
                "    \"phone\": \"240-123-1231\",\n" +
                "    \"premanentAddress\": \"7925 Jones Branch Dr\"\n" +
                "  },\n" +
                "  \"firstName\": \"James\",\n" +
                "  \"gender\": \"Males\",\n" +
                "  \"joinDate\": \"01/01/3321\",\n" +
                "  \"lastName\": \"Bond\",\n" +
                "  \"major\": \"CS\",\n" +
                "  \"password\": \"1234\",\n" +
                "  \"section\": \"101010\",\n" +
                "  \"subject\": \"Math\"\n" +
                "}"; // these long info is from the Postman, student/create from My school.

        //create student
        Response response = given().
                                contentType(ContentType.JSON).
                            body(json).
                                post("student/create").prettyPeek();
                                // "student/create" is from the url of Postman

        int studentId = response.jsonPath().getInt("studentId");

        // Delete student
        Response response2 = given().
                                accept(ContentType.JSON).
                            when().
                                delete("student/delete/{id}", studentId).
                            // 5681 is the ID of student that you want to delete
                                prettyPeek(); // 4
        // the result shows: HTTP/1.1 200  -> success
    }

    @Test
    @DisplayName("Delete student")
    public void test2(){
        Response response2 = given().
                accept(ContentType.JSON).
                when().
                delete("student/delete/{id}", 5681).
                // 5681 is the ID of student that you want to delete
                        prettyPeek(); // 4
    }

    @Test
    @DisplayName("Create new student and read data from external JSON file")
    public void test3(){
        try {
            //read JSON file
            File file = new File(System.getProperty("user.dir")+"/student.json");

            Response response = given().
                    contentType(ContentType.JSON).
                    body(file).
                    post("student/create").prettyPeek();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
