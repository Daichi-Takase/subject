/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.subject.service;

import com.mycompany.subject.dto.MemberDto;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.junit.Ignore;

/**
 *
 * @author toisu
 */
public class JdbcMainTest {

    //接続文字列
    String url = "jdbc:postgresql://localhost:5432/postgres";
    String user = "postgres";
    String password = "postgres";

    @Test
    public void test0() {
        fail("作成中");
    }

    @Test
    public void testinsertMember1() {

        /* 画面入力項目セット（仮）*/
        MemberDto memberDto = new MemberDto();
        //名前
        memberDto.setName("高瀬");
        //性別
        memberDto.setSex("男性");
        //生年月日（年・月・日）
        memberDto.setYear("1991");
        memberDto.setMonth("10");
        memberDto.setDay("05");
        //郵便番号
        memberDto.setPostal_code("530-0037");
        //住所
        memberDto.setAddress("大阪府大阪市北区");
        //電話番号
        memberDto.setTelephone_no("08012345678");
        //クレジットカード番号
        memberDto.setCredit_no("1234567890123452");

        int result = JdbcMain.insertMember(memberDto, url, user, password);
        assertEquals(1, result);

    }

    @Test
    public void testinsertMember2() {

        /* 画面入力項目セット（仮）*/
        MemberDto memberDto = new MemberDto();
        //名前
        memberDto.setName("高瀬");
        //性別
        memberDto.setSex("性別なし");
        //生年月日（年・月・日）
        memberDto.setYear("1991");
        memberDto.setMonth("10");
        memberDto.setDay("05");
        //郵便番号
        memberDto.setPostal_code("530-0037");
        //住所
        memberDto.setAddress("大阪府大阪市北区");
        //電話番号
        memberDto.setTelephone_no("08012345678");
        //クレジットカード番号
        memberDto.setCredit_no("1234567890123452");

        int result = JdbcMain.insertMember(memberDto, url, user, password);
        assertEquals(0, result);

    }

    @Test
    public void testselectMember1() {

        /* 画面入力項目セット（仮）*/
        MemberDto memberDto = new MemberDto();
        //ID
        memberDto.setId("3");

    }

    @Test
    public void testgetByCd1() {
        JdbcMain.SexInfo si = JdbcMain.SexInfo.getByCd("女性");
        assertThat(si.getCd(), is(2));
    }

    @Test
    public void testgetBySex1() {
        JdbcMain.SexInfo si = JdbcMain.SexInfo.getBySex(1);
        assertThat(si.getSex(), is("男性"));
    }

    @Test
    public void testisZipCode1() {
        boolean result = JdbcMain.isZipCode("5300037");
        assertThat(result, is(true));
    }

    @Test
    public void testisZipCode2() {
        boolean result = JdbcMain.isZipCode("〒530-0037");
        assertThat(result, is(false));
    }

    @Test
    public void testisZipCode3() {
        boolean result = JdbcMain.isZipCode(null);
        assertThat(result, is(false));
    }

    @Test
    public void testcreditCheckDigit1() {
        int result = JdbcMain.creditCheckDigit("1234567890123452");
        assertEquals(2, result);
    }

    @Test
    public void testcreditCheckDigit2() {
        int result = JdbcMain.creditCheckDigit("1234567890123453");
        assertEquals(3, result);
    }

}
