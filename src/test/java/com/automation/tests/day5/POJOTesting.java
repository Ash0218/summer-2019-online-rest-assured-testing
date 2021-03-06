package com.automation.tests.day5; // 012820

import com.automation.pojos.Job;
import com.automation.pojos.Location;
import com.automation.utilities.ConfigurationReader;
import com.google.gson.Gson;
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


    @Test
    @DisplayName("Convert POJO to JSON")
    public void test2(){ // 9
        Job sdet = new Job("SDET", "Software Development Engineer in Test", 5000, 20000); // 10

        Gson gson = new Gson(); // 12
        // Convert to Gson.

      //  gson.toJson(sdet); // 13
        // convert POJO to JSON -> serialization

        String json = gson.toJson(sdet); // 15

        System.out.println("JSON file :" + json); // 11
        System.out.println("From toString(): "+ sdet); // 16
      //  System.out.println(gson.toJson(sdet)); // 14
    }


    @Test
    @DisplayName("Convert JSON into collection of POJO's")
    public void test3(){ // 17
        Response response = given().
                accept(ContentType.JSON).
                when().
                get("/jobs"); // 18

        JsonPath jsonPath = response.jsonPath(); // 19
        List<Job> jobs = jsonPath.getList("items", Job.class); // 20
        // for the collection of jobs, I used "List<Job>." If it is one job, I'd
        //  use "Job" like #10.

        for (Job job : jobs){ // 21
            System.out.println(job.getJob_title()); // 22
        }

        for (Job job : jobs){ // 23
            System.out.println(job); // 24
        }
    }

    @Test
    @DisplayName("Convert from JSON to Location POJO")
    public void test4(){ // 25
        Response response = given().
                                    accept(ContentType.JSON).
                            when().
                                    get("/locations/{location_id}", 2500); // 26
        Location location = response.jsonPath().getObject("", Location.class); // 27

        System.out.println(location); // 28


        Response response2 = given().
                accept(ContentType.JSON).
                when().
                get("/locations"); // 29

        List<Location> locations = response2.jsonPath().getList("items", Location.class); // 30

        for (Location l : locations) { // 31
            System.out.println(l); // 32
        }

    }
}
