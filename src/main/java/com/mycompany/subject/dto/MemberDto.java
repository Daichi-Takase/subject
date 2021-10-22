/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.subject.dto;

/**
 *
 * @author toisu
 */
public class MemberDto {

    private String id;
    private String name;
    private String sex;
    private String year;
    private String month;
    private String day;    
    private String date_of_birth;
    private String postal_code;
    private String address;
    private String telephone_no;
    private String credit_no;
    private String creation_date_time;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
    
    public String getDate_of_birth() {
        return this.year + "/" + this.month + "/" + this.day;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone_no() {
        return telephone_no;
    }

    public void setTelephone_no(String telephone_no) {
        this.telephone_no = telephone_no;
    }

    public String getCredit_no() {
        return credit_no;
    }

    public void setCredit_no(String credit_no) {
        this.credit_no = credit_no;
    }

    public String getCreation_date_time() {
        return creation_date_time;
    }

    public void setCreation_date_time(String creation_date_time) {
        this.creation_date_time = creation_date_time;
    }

}
