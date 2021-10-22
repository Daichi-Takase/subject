/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.subject.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.junit.Ignore;


/**
 *
 * @author toisu
 */
public class JdbcMainTest {

    @Test
    public void test0() {
        fail("作成中");
    }
 
    @Test
    public void testgetByCd1() {
        JdbcMain.SexInfo si = JdbcMain.SexInfo.getByCd("男性");
        assertThat(si.getCd(), is(1));
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

}
