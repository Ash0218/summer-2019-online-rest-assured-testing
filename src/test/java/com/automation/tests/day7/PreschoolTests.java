package com.automation.tests.day7; // 013120

import com.automation.pojos.Student;
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

public class PreschoolTests {

    @BeforeAll
    public static void setup(){ // 1
        baseURI = ConfigurationReader.getProperty("school.uri"); // 2
    }

    @Test
    @DisplayName("Get student with id 2633 and convert payload into POJO")
    public void test1(){ // 3
        Response response = given().
                                accept(ContentType.JSON).
                                pathParam("id", 2633).
                            when().
                                get("/student/{id}").prettyPeek(); // 4

        // deserialization from JSON to POJO.
        //  student -> a POJO
        Student student = response.jsonPath().getObject("students[0]", Student.class); // 5

        System.out.println(student); // 6

        assertEquals(2633, student.getStudentId()); // 7
        assertEquals(11, student.getBatch()); // 8
        assertEquals("123456", student.getAdmissionNo()); // 9
        assertEquals("7925 Jones Branch Dr #3300", student.getContact().getPermanantAddress()); // 10
        assertEquals("sdet@email.com", student.getContact().getEmailAddress()); // 11
    }

}
