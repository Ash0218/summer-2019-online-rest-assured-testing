package com.automation.utilities; // 013020

import com.automation.pojos.Spartan;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.File;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.*;

public class APIUtilities {
    private static String URI = ConfigurationReader.getProperty("spartan.uri"); // 14
/*
This method can POST new spartan
@param spartan POJO
@return response object
 */
    public static Response postSpartan(Spartan spartan){ // 1,2
        Response response = given().
                contentType(ContentType.JSON).
                basePath(URI).
                body(spartan).
                when().
                post("/spartans"); // 3
        return response; // 4
    }


    public static Response postSpartan(Map<String, ?> spartan){
        RestAssured.baseURI = ConfigurationReader.getProperty("spartan.uri");
        Response response = given().
                    contentType(ContentType.JSON).
                    body(spartan).
                    when().
                    post("/spartans");
        return response;
    }

/*
This method can POST new spartan
@param filePath to the Spartan external JSON file
@return response object
 */
public static Response postSpartan(String filePath){ // 5
    RestAssured.baseURI = ConfigurationReader.getProperty("spartan.uri"); // 12
    File file = new File(filePath); // 6
    // it will read the spartan.json file under target package
    RestAssured.baseURI = ConfigurationReader.getProperty("spartan.uri"); // 7
    Response response = given().
            contentType(ContentType.JSON).
            body(file).
            when().post("/spartans"); // 8
    return response; // 9
}

/*
Method to delete spartan
@param id of spartan that you would like to delete
@return response object that you can assert later
 */
public static Response deleteSpartanById(int id){ // 10
    RestAssured.baseURI = ConfigurationReader.getProperty("spartan.uri"); // 13
    return RestAssured.when().delete("/spartans/{id}", id); // 11

}


/*
Delete all spartans
@Return response
 */
public static void deleteAllSpartans(){
    Response response = given().
            basePath(baseURI).
            accept(ContentType.JSON).
            when().
            get("/spartans");

    List<Integer> userIDs = response.jsonPath().getList("id");
    for (int i=0; i<userIDs.size(); i++){
        when().delete("/spartans/{id}", userIDs.get(i)).then().assertThat().statusCode(204);
        System.out.println("Deleted spartan with id: " + userIDs.get(i));
    }

}
}
