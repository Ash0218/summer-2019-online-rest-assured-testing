package com.automation.pojos; // 012820

import com.google.gson.annotations.SerializedName;

/*
private int locationId;

    "location_id": 2900,
    "street_address": "20 Rue des Corps-Saints",
    "postal_code": "1730",
    "city": "Geneva",
    "state_province": "Geneve",
    "country_id": "CH",

 */

public class Location {
    @SerializedName("location_id")
    private int locationId; // 1
    // from the task, it should be location_id, but #1 has locationId.
    //  it cannot work, so we added @SerializedName("location_id") on top
    //  of #1.

    @SerializedName("street_address")
    private String streetAddress; // 2

    @SerializedName("postal_code")
    private String postalCode; // 3

    @SerializedName("city")
    private String city; // 4

    @SerializedName("state_province")
    private String stateProvince; // 5

    @SerializedName("countryId")
    private String countryId; // 6


    public Location(int locationId, String streetAddress, String postalCode, String city, String stateProvince, String countryId) {
        this.locationId = locationId;
        this.streetAddress = streetAddress;
        this.postalCode = postalCode;
        this.city = city;
        this.stateProvince = stateProvince;
        this.countryId = countryId;
    }

    public int getLocationId() {
        // Generate -> getter and setter -> select all -> ok
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateProvince() {
        return stateProvince;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }


    @Override // generate -> toString() -> ok
    public String toString() {
        return "Location{" +
                "locationId=" + locationId +
                ", streetAddress='" + streetAddress + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", city='" + city + '\'' +
                ", stateProvince='" + stateProvince + '\'' +
                ", countryId='" + countryId + '\'' +
                '}';
    }
}


