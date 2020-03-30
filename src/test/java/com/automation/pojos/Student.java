package com.automation.pojos; // 013120

public class Student {
    private String admissionNo;
    private int batch;
    private String birthDate;
    private Company company;
    // Company contains address object

    private Contact contact;
    private String firstName;
    private String gender;
    private String joinDate;
    private String lastName;
    private String password;
    private String section;
    private int studentId;
    private String subject;


    // Generate constructor (select all)
    public Student(String admissionNo, int batch, String birthDate, Company company, Contact contact, String firstName, String gender, String joinDate, String lastName, String password, String section, int studentId, String subject) {
        this.admissionNo = admissionNo;
        this.batch = batch;
        this.birthDate = birthDate;
        this.company = company;
        this.contact = contact;
        this.firstName = firstName;
        this.gender = gender;
        this.joinDate = joinDate;
        this.lastName = lastName;
        this.password = password;
        this.section = section;
        this.studentId = studentId;
        this.subject = subject;
    }


    public Student(){
        // fake constructor
    }


    // Generate getters and setters (select all)
    public String getAdmissionNo() {
        return admissionNo;
    }

    public void setAdmissionNo(String admissionNo) {
        this.admissionNo = admissionNo;
    }

    public int getBatch() {
        return batch;
    }

    public void setBatch(int batch) {
        this.batch = batch;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }


    // Generate toString
    @Override
    public String toString() {
        return "Student{" +
                "admissionNo='" + admissionNo + '\'' +
                ", batch=" + batch +
                ", birthDate='" + birthDate + '\'' +
                ", company=" + company +
                ", contact=" + contact +
                ", firstName='" + firstName + '\'' +
                ", gender='" + gender + '\'' +
                ", joinDate='" + joinDate + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", section='" + section + '\'' +
                ", studentId=" + studentId +
                ", subject='" + subject + '\'' +
                '}';
    }
}
