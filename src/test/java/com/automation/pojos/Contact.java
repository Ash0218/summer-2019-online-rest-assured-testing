package com.automation.pojos; // 013120

import com.github.javafaker.Address;
import com.google.gson.annotations.SerializedName;

public class Contact {
    // variables:
    private int contactId;
    private String emailAddress;
    private String phone;
    @SerializedName("premanentAddress")
    private String permanantAddress;
    // used @SerializedName("premanentAddress") -> In Postman, it is
    //  "premanentAddress" but I used "permanentAddress" in IntelliJ.

    public Contact(){
        // this is no argument constructor
    }

    // Use Generate -> constructor -> select all -> ok
 //   public Contact(int contactId, String emailAddress, String phone, String permanantAddress) {
//        this.contactId = contactId;
        // we don't post Id, so we don't need contactId b.c when we create pojos,
        //  we are going to set it up. So, delete the contactId part.
    public Contact(String emailAddress, String phone, String permanantAddress) {
        this.emailAddress = emailAddress;
        this.phone = phone;
        this.permanantAddress = permanantAddress;
    }

    // Use Generate -> Getter and Setter -> select all -> ok
    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPermanantAddress() {
        return permanantAddress;
    }

    public void setPermanantAddress(String permanantAddress) {
        this.permanantAddress = permanantAddress;
    }


    // Generate -> toString -> select all -> ok
    @Override
    public String toString() {
        return "Contact{" +
                "contactId=" + contactId +
                ", emailAddress='" + emailAddress + '\'' +
                ", phone='" + phone + '\'' +
                ", permanantAddress='" + permanantAddress + '\'' +
                '}';
    }
}
