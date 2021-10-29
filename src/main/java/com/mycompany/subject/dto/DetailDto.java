/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.subject.dto;

/**
 *
 * @author toisu
 */
public class DetailDto {
    
    private String id;
    private String buy_date_time;
    private String goods;
    private String payment;
    private String credit_no;
    private String give_point;
    private String paymanet_sum;
 
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBuy_date_time() {
        return buy_date_time;
    }

    public void setBuy_date_time(String buy_date_time) {
        this.buy_date_time = buy_date_time;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getCredit_no() {
        return credit_no;
    }

    public void setCredit_no(String credit_no) {
        this.credit_no = credit_no;
    }

    public String getGive_point() {
        return give_point;
    }

    public void setGive_point(String give_point) {
        this.give_point = give_point;
    }

    public String getPaymanet_sum() {
        return paymanet_sum;
    }

    public void setPaymanet_sum(String paymanet_sum) {
        this.paymanet_sum = paymanet_sum;
    }
    
}
