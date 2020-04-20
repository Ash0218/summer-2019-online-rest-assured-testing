package com.automation.pojos;
import java.util.Objects;

// Homework #1
/*
In this assignment, you will test api uinames. This is a free api used to
 create test users. Documentation for this api is available at
 https://uinames.com. You can import Postman collection for this API
 using link: https://www.getpostman.com/collections/e1338b73a8be7a5500e6.
  Automate the given test cases. You can use any existing project. You
  can automate all test cases in same class or different classes.

  TEST CASES
  No params test
  1.Send a get request without providing any parameters
  2.Verify status code 200, content type application/json; charset=utf-8
  3.Verify that name, surname, gender, region fields have value

  Gender test
  1.Create a request by providing query parameter: gender, male or female
  2.Verify status code 200, content type application/json; charset=utf-8
  3.Verify that value of gender field is same from step 1

  2 params test
  1.Create a request by providing query parameters: a valid region and genderNOTE: Available region values are given in the documentation
  2.Verify status code 200, content type application/json; charset=utf-8
  3.Verify that value of gender field is same from step 1
  4.Verify that value of region field is same from step 1

  Invalid gender test
  1.Create a request by providing query parameter: invalid gender
  2.Verify status code 400 and status line contains Bad Request
  3.Verify that value of error field is Invalid gender

  Invalid region test
  1.Create a request by providing query parameter: invalid region
  2.Verify status code 400 and status line contains Bad Request
  3.Verify that value of error field is Regionorlanguagenotfound

  Amount and regions test
  1.Create request by providing query parameters: a valid regionand amount(must be bigger than 1)
  2.Verify status code 200, content type application/json; charset=utf-8
  3.Verify that all objects have different name+surname combination3

  params test
  1.Create a request by providing query parameters: a valid region, gender and amount(must be bigger than 1)
  2.Verify status code 200, content type application/json; charset=utf-8
  3.Verify that all objects the response have the same region and gender passed in step 1

  Amount count test
  1.Create a request by providing query parameter: amount (must be bigger than 1)
  2.Verify status code 200, content type application/json; charset=utf-8
  3.Verify that number of objects returned in the response is same as the amount passed in step 1

 */

public class User implements Comparable<User> {
    private String name;
    private String surname;
    private String gender;
    private String region;

    public User() {

    }

    public User(String name, String surname, String gender, String region) {
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.region = region;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void printFullName() {
        System.out.println(String.format("Full name: %s %s", surname, name));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof User))
            return false;
        User user = (User) o;
        return Objects.equals(name, user.name) && Objects.equals(surname, user.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", gender='" + gender + '\'' +
                ", region='" + region + '\'' +
                '}';
    }

    @Override
    public int compareTo(User o) {
        return this.surname.compareTo(o.getSurname());
    }
}
