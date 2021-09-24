package com.assignment.crud.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class CreateUpdateUserDto {
    @ApiModelProperty(notes = "Title of Person", required = true, value = "Mr")
    private String title;
    @ApiModelProperty(notes = "First Name", required = true, value = "Victor")
    private String firstName;
    @ApiModelProperty(notes = "Last Name", required = true, value = "Chinyavada")
    private String lastName;
    @ApiModelProperty(notes = "Date of Birth", required = true, value = "1997-05-16")
    private Date dob;
    @ApiModelProperty(notes = "Job Title", required = true, value = "Software Engineer")
    private String jobTitle;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
}
