package com.automation.pojos; // 013120
// represent "address" part in Postman, My school, PostStudent

public class Address {
    private int addressId;
    private String city;
    private String state;
    private String street;
    private int zipCode;

    // I added addressId, city, state, street, zipCode b.c these are the
    //  data in Postman.

    public Address(){
        // no arguments constructor -> need this to make it visible outside
        //  of the package.
    }

    // Generate constructor -> select all
    public Address(int addressId, String city, String state, String street, int zipCode) {
        this.addressId = addressId;
        this.city = city;
        this.state = state;
        this.street = street;
        this.zipCode = zipCode;
    }


    // Generate getters and setters (select all)
    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }


    // Generate toString
    @Override
    public String toString() {
        return "Address{" +
                "addressId=" + addressId +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", street='" + street + '\'' +
                ", zipCode=" + zipCode +
                '}';
    }
}
