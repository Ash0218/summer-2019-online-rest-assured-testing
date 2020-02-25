package com.automation.pojos; // 012820

public class Job { // 1
    private String job_id; // 2
    private String job_title; // 3
    private Integer min_salary; // 4
    private Integer max_salary; // 5

    public Job(String job_id, String job_title, Integer min_salary, Integer max_salary) { // 6
        // right click on empty space, generate, constructor, select all parameters, ok
        this.job_id = job_id; // 7
        this.job_title = job_title; // 8
        this.min_salary = min_salary; // 9
        this.max_salary = max_salary; // 10
    }

    public String getJob_id() {  // 11
        // right click on empty space, Generate, Getter and Setter, select all, ok
        return job_id; // 12
    }

    public void setJob_id(String job_id) { // 13
        this.job_id = job_id; // 14
    }

    public String getJob_title() { // 15
        return job_title; // 16
    }

    public void setJob_title(String job_title) { // 17
        this.job_title = job_title; // 18
    }

    public Integer getMin_salary() { // 19
        return min_salary; // 20
    }

    public void setMin_salary(Integer min_salary) { // 21
        this.min_salary = min_salary; // 22
    }

    public Integer getMax_salary() { // 23
        return max_salary; // 24
    }

    public void setMax_salary(Integer max_salary) { // 25
        this.max_salary = max_salary; // 26
    }


    @Override
    public String toString() { // 27
        // right click on empty space, generate, toString(), ok
        return "Job{" +
                "job_id='" + job_id + '\'' +
                ", job_title='" + job_title + '\'' +
                ", min_salary=" + min_salary +
                ", max_salary=" + max_salary +
                '}'; // 28
    }
}
