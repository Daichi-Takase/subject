/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.subject.service;

import com.mycompany.subject.dto.MemberDto;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author toisu
 */
public class MemberServiceTest {

    //接続文字列
    String url = "jdbc:postgresql://localhost:5432/postgres";
    String user = "postgres";
    String password = "postgres";

    MemberDto memberDto;

    @Before
    public void setup() {

        /* 画面入力項目セット */
        memberDto = new MemberDto();
        //名前
        memberDto.setName("会員登録テスト");
        //性別
        memberDto.setSex("男性");
        //生年月日（年・月・日）
        memberDto.setYear("1991");
        memberDto.setMonth("10");
        memberDto.setDay("05");
        //郵便番号
        memberDto.setPostal_code("5300037");
        //住所
        memberDto.setAddress("大阪府大阪市北区");
        //電話番号
        memberDto.setTelephone_no("08012345678");
        //クレジットカード番号
        memberDto.setCredit_no("1234567890123452");

    }

    @Test
    public void testinsertMember1() {

        int result = MemberService.insertMember(memberDto, url, user, password);
        assertEquals(1, result);

    }

    @Test
    public void testinsertMember2() {

        memberDto.setSex("性別エラー");

        int result = MemberService.insertMember(memberDto, url, user, password);
        assertEquals(0, result);

    }

    @Test
    public void testinsertMember3() {

        memberDto.setYear("1991");
        memberDto.setMonth("10");
        memberDto.setDay("32");

        int result = MemberService.insertMember(memberDto, url, user, password);
        assertEquals(0, result);

    }

    @Test
    public void testinsertMember4() {

        memberDto.setPostal_code("530-0037");

        int result = MemberService.insertMember(memberDto, url, user, password);
        assertEquals(0, result);

    }

    @Test
    public void testinsertMember5() {

        memberDto.setCredit_no("1234567890123453");

        int result = MemberService.insertMember(memberDto, url, user, password);
        assertEquals(0, result);

    }

    @Test
    public void testselectMember1() {
        
        memberDto.setId("23");

        MemberDto result = MemberService.selectMember(memberDto, url, user, password);

        assertThat(result.getId(), is("23  "));
        assertThat(result.getName(), is("会員登録テスト"));
        assertThat(result.getSex(), is("1"));
        assertThat(result.getDate_of_birth(), is("1991年10月05日"));
        assertThat(result.getPostal_code(), is("5300037"));
        assertThat(result.getAddress(), is("大阪府大阪市北区"));
        assertThat(result.getTelephone_no(), is("08012345678"));
        assertThat(result.getCredit_no(), is("1234567890123452"));
        assertThat(result.getCreation_date_time(), is("2021年10月29日(金) 14時35分"));
    }

}
