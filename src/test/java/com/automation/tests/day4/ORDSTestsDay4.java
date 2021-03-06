package com.automation.tests.day4; // 012720

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

public class ORDSTestsDay4 {

    @BeforeAll
    public static void setup(){ // 1
        baseURI = ConfigurationReader.getProperty("ords.uri"); // 2
    }

    /*
    Warmup!
           		Given accept type is JSON
          		 When users sends a GET request to "/employees"
         		 Then status code is 200
          		And Content type is application/json
          		And response time is less than 3 seconds
*/

    @Test
    @DisplayName("Verify that response time is less than 3 seconds")
    public void test1(){ // 3
        given().
                accept(ContentType.JSON).
        when().
                get("/employees").
        then().
                assertThat().
                            statusCode(200).
                            contentType(ContentType.JSON).
                    // verify that the server use proper content type (JSon)
                            time(lessThan(3L), TimeUnit.SECONDS).
                log().all(true); // 4
        // log().all() is better than pretty print b.c it shows everything
        //  about the response. It prints into console all info about
        //  response: header, body, status code, schema(http), schema
        //  version (1.1).
        // all(true): it is like prettyPrint(), prettyPick(), but they
        //  just print body.
        // Payload = body, and our body has JSON format.
    }


    /*
    Warmup2!
        In Postman: {{baseUrl}}/countries?q={"country_id":"US"}
        -> this shows country information (US)

                Given accept type is JSON
                And parameters: q = country_id = US
                When users sends a GET request to "/countries"
                Then status code is 200
                And Content type is application/json
                And country_name from payload is "United States of America"
                {"country_id":"US"}

     */

    @Test
    @DisplayName("Verify that country_name from payload is \"United States of America\"")
    public void test2(){ // 5
        given().
                accept(ContentType.JSON).
                queryParam("q", "{\"country_id\":\"US\"}"). // 6
        when().
                get("/countries").
        then().
                assertThat().
                    contentType(ContentType.JSON).
                    statusCode(200).
                    body("item[0].country_name", is("United States of America")).
                    log().all(true);// 7

    }


    @Test
    @DisplayName("Get all links and print them out")
    public void test3(){ // 8
        Response response = given().
                                    accept(ContentType.JSON).
                                    queryParam("q", "{\"country_id\":\"US\"}").
                            when().
                                    get("/countries"); // 9
                            // it has new Response();

        JsonPath jsonPath = response.jsonPath(); // 10
        // jsonPath represents the body of the data: [{"rel":"describedby",...}]

        List<?> links = jsonPath.getList("links.href"); // 11
        // <?> -> it should be String, but it can be other types, so put ? here.
        // If I don't put index, I will get collection of properties (only if they exists)
        /* "links.href" is from the Postman:
        "links": [
                {
                    "rel": "self",
                    "href": "http://ec2-54-211-56-96.compute-1.amazonaws.com:1000/ords/hr/countries/US"
                }
         */
        // If you want to have only specific link, use:
        //  "items[0].links.href" in path. [0] is the specific link you want.
        //  "items.links.href" shows all links.
        // "links.href" shows only links in US.

        for (Object link: links){ // 12
            System.out.println(link); // 13
            // links are shown
        }
    }


    @Test
    @DisplayName("Verify that payload contains only 25 countries")
    public void test4(){ // 14
        List<?> countries = given().
                                accept(ContentType.JSON).
                            when().
                                get("/countries").prettyPeek().
                            thenReturn().jsonPath().getList("items"); // 15

        assertEquals(25, countries.size()); // 16
        // the maximum number of data that ORDS shows is 25.
    }

    /*
    Task:
        given path parameter is "/regions" and region id is 2
        when user makes get request
        then assert that status code is 200
        and user verifies that body returns following country names
            |Argentina                  |
            |Brazil                     |
            |Canada                     |
            |Mexico                     |
            |United States of America   |
     */

    @Test
    @DisplayName("Verify that payload contains following countries")
    public void test5(){ // 17
        List<String> expected =
                List.of("Argentina", "Brazil", "Canada", "Mexico", "United States of America"); // 18
                // to use List.of()set java 9 at least in pom.xml,
                //  source, target.

        Response response = given().
                                accept(ContentType.JSON).
                                queryParam("q", "{\"region_id\":\"2\"}").
                            when().
                                get("/countries").prettyPeek(); // 19
        List<String> actual = response.jsonPath().getList("items.country_name"); // 20
        // "items.country_name" is from the Postman data:
        //  Get /countries (on the left) -> "items": [{ "country_name": ...}]
        assertEquals(expected, actual); // 21

        // With assertThat():
        given().
                accept(ContentType.JSON).
                queryParam("q", "{\"region_id\":\"2\"}").
        when().
                get("/countries").
        then().
                assertThat().body("items.country_name", contains("Argentina", "Brazil", "Canada", "Mexico", "United States of America")); // 22
    }

    /*
 given path parameter is "/employees"
 when user makes get request
 then assert that status code is 200
 then user verifies that every employee has positive salary
     */
    // to switch to java 9, add/ replace it in pom.xml:
    /*
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <source>9</source>
                    <target>9</target>
                </configuration>
            </plugin>
        </plugins>
    </build>
     */

    @Test
    @DisplayName("Verify that every employee has positive salary")
    public void test6(){ // 23
        given().
                accept(ContentType.JSON).
        when().
                get("/employees").
        then().
                assertThat().
                    statusCode(200).
                    body("items.salary", everyItem(greaterThan(0))); // 24

        // Whenever you specify path as items.salary, you will get collection of salaries
        //  then to check every single value.
        // we can use everyItem(is()), everyItem(greaterThan())
        /*
        Creates a matcher for {@link Iterable}s that only matches when a single pass over the
        examined {@link Iterable} yields items that are all matched by the specified
        <code>itemMatcher</code>.
        For example:
        <pre>assertThat(Arrays.asList("bar", "baz"), everyItem(startsWith("ba")))</pre>
         */
    }

    /*
    Task:
        given path parameter is "/employees/{id}"
        and path parameter is 101
        when user makes get request
        then assert that status code is 200
        and verifies that phone number is 515-123-4568
     */

    @Test
    @DisplayName("Verify that employee 101 has following phone number: 515-123-4568 ")
    public void test7(){ // 25
        Response response = given().
                                accept(ContentType.JSON).
                            when().
                                get("/employees/{id}", 101); // 26
        assertEquals(200, response.getStatusCode()); // 27

        String expected = "515-123-4568"; // 28
        String actual = response.jsonPath().get("phone_number"); // 29

        expected = expected.replace("-", "."); // 30
        assertEquals(expected, actual); // 31
    }


    /* Task: verify that body returns following salary information after sorting from higher to lower
     * given path parameter is "/employees"
     * when user makes get request
     * then assert that status code is 200
     * and verify that body returns following salary information after sorting from higher to lower
     *  24000, 17000, 17000, 12008, 11000,
     *  9000, 9000, 8200, 8200, 8000,
     *  7900, 7800, 7700, 6900, 6500,
     *  6000, 5800, 4800, 4800, 4200,
     *  3100, 2900, 2800, 2600, 2500
     */

    @Test
    @DisplayName("verify that body returns following salary information after sorting from higher to lower (after sorting it in descending order)")
    public void test8(){ // 32
        List<Integer> expectedSalaries = List.of(24000, 17000, 17000, 12008, 11000,
                                                9000, 9000, 8200, 8200, 8000,
                                                7900, 7800, 7700, 6900, 6500,
                                                6000, 5800, 4800, 4800, 4200,
                                                3100, 2900, 2800, 2600, 2500); // 33
        Response response = given().
                                    accept(ContentType.JSON).
                            when().
                                    get("/employees"); // 34
        assertEquals(200, response.statusCode()); // 35

        List<Integer> actualSalaries = response.jsonPath().getList("items.salary"); // 36

        Collections.sort(actualSalaries, Collections.reverseOrder()); // 39
        // without sort, it has an error b.c actualSalaries and expectedSalaries do not match.
        System.out.println(actualSalaries); // 37

        assertEquals(expectedSalaries, actualSalaries, "Salaries are not matching"); // 38
    }


    /*
    Given accept type as JSON
    And path parameter is id with value 2900
    When user sends get request to /locations/{id}  -> this is path parameter b.c it has /{}. Query parameter has question mark (?).
    Then user verifies that status code is 200
        |street_address         |city  |postal_code|country_id|state_province|
        |20 Rue des Corps-Saints|Geneva|1730       |CH        |Geneve        |
 */

    /*
    key         :  value    -> all together is entry
    "location_id": 2900,
    "street_address": "20 Rue des Corps-Saints",
    "postal_code": "1730",
    "city": "Geneva",
    "state_province": "Geneve",
    "country_id": "CH"
     */
    @Test
    @DisplayName("Verify that JSON body has following entries")
    public void test9(){ // 40
            given().
                    accept(ContentType.JSON).
                    pathParam("id", 2900).
            when().
                    get("/locations/{id}").
            then().
                    assertThat().
                        statusCode(200).
                        body("", hasEntry("street_address", "20 Rue des Corps-Saints")).
                        body("", hasEntry("city", "Geneva")).
                        body("", hasEntry("postal_code", "1730")).
                        body("", hasEntry("country_id", "CH")).
                        body("", hasEntry("state_province", "Geneva")).
                        log().all(true); // 41
    }

    // or you can use test9_2() instead of test9()
    @Test
    @DisplayName("Verify that JSON body has following entries")
    public void test9_2(){ // 42
        given().
                accept(ContentType.JSON).
                pathParam("id", 2900).
                when().
                get("/locations/{id}").
                then().
                assertThat().
                statusCode(200).
                body("street_address", is("20 Rue des Corps-Saints")).
                body("city", is("Geneva")).
                body("postal_code", is("1730")).
                body("country_id", is("CH")).
                body("state_province", is("Geneva")).
                log().all(true); // 43
    }




}


