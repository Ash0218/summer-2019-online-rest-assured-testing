package com.automation.pojos; // 013020

/*
Task: show data: {
    "id": 112,
    "name": "King Vasys",
    "gender": "Male",
    "phone": 7654321876
    }
 */

import com.google.gson.annotations.SerializedName;

public class Spartan {
    @SerializedName("id")
    private int spartanId; // 1
    // spartanId is different from id in the task, so we need @SerializedName
    private String name; // 2
    private String gender; // 3
    private long phone; // 4
    // after #4, right click -> Generate -> Getter and Setter -> select all -> ok
    //  Then, right click -> Generate -> constructor -> select all -> ok
    //  Then, right click -> Generate -> toString() -> select all -> ok


    public Spartan(){ // 5

    }






    public Spartan(int spartanId, String name, String gender, long phone) {
        this.spartanId = spartanId;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
    }

    public int getSpartanId() {
        return spartanId;
    }

    public void setSpartanId(int spartanId) {
        this.spartanId = spartanId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }


    @Override
    public String toString() {
        return "Spartan{" +
                "spartanId=" + spartanId +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", phone=" + phone +
                '}';
    }
}
