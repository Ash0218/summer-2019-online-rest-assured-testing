package com.automation.tests.day5; // 012820

import com.automation.pojos.Job;
import com.automation.utilities.ConfigurationReader;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;

public class POJOTesting {
    @BeforeAll
    public static void setup(){ // 1
        baseURI = ConfigurationReader.getProperty("ords.uri"); // 2
    }

    @Test
    @DisplayName("Get job info from JSON and convert it into POJO")
    public void test1(){ // 3
        Response response = given().
                                accept(ContentType.JSON).
                            when().
                                get("/jobs"); // 4

        JsonPath jsonPath = response.jsonPath(); // 5

        // This is deserialization from JSON to POJO (java object)
        Job job = jsonPath.getObject("item[0]", Job.class); // 6
        // job.class type of POJO that you gonna create from JSON

        System.out.println(job); // 7

        System.out.println("Job id: "+job.getJob_id()); // 8
    }
}
