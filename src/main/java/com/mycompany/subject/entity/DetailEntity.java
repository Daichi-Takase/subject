/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.subject.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 *
 * @author toisu
 */
public class DetailEntity {

    private String id;
    private Timestamp buy_date_time;
    private String goods;
    private int payment;
    private String credit_no;
    private BigDecimal give_point;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getBuy_date_time() {
        return buy_date_time;
    }

    public void setBuy_date_time(Timestamp buy_date_time) {
        this.buy_date_time = buy_date_time;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public String getCredit_no() {
        return credit_no;
    }

    public void setCredit_no(String credit_no) {
        this.credit_no = credit_no;
    }

    public BigDecimal getGive_point() {
        return give_point;
    }

    public void setGive_point(BigDecimal give_point) {
        this.give_point = give_point;
    }

}
