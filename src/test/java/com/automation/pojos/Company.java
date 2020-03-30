package com.automation.pojos; // 013120
// represent "company" part in Postman, My school, PostStudent

/*
Composition: one of the fundamental concepts of OOP. When class is referenced
    to one or more objects of another class in instance variable, it is called
    composition.
    Ex: Company.java contains information of Address.java.

Company object will contain address object.

The main advantage of Composition is that you can reuse the code without
 'is-a relationship' (without inheritance).
 */
public class Company {
    private Address address;
    // Address contains information about Address class in pojos package

    private int companyId;
    private String companyName;
    private String startDate;
    private String title;


    // Generate constructor (select all except companyId)
    public Company(Address address, String companyName, String startDate, String title) {
        this.address = address;
        this.companyName = companyName;
        this.startDate = startDate;
        this.title = title;
    }


    public Company(){
        // give false constructor to make it visible outside of the package
    }


    // Generate getters and setters (select all)
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    // Generate toString
    @Override
    public String toString() {
        return "Company{" +
                "address=" + address +
                ", companyId=" + companyId +
                ", companyName='" + companyName + '\'' +
                ", startDate='" + startDate + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
