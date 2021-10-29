/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.subject.common;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author toisu
 */
public class ServiceCommonTest {

    @Test
    public void testgetByCd1() {
        ServiceCommon.SexInfo si = ServiceCommon.SexInfo.getByCd("女性");
        assertThat(si.getCd(), is(2));
    }
    
    @Test
    public void testgetByCd2() {
        ServiceCommon.SexInfo si = ServiceCommon.SexInfo.getByCd("その他");
        assertThat(si, is(nullValue()));
    }

    @Test
    public void testgetBySex1() {
        ServiceCommon.SexInfo si = ServiceCommon.SexInfo.getBySex(1);
        assertThat(si.getSex(), is("男性"));
    }
    
    @Test
    public void testgetBySex2() {
        ServiceCommon.SexInfo si = ServiceCommon.SexInfo.getBySex(3);
        assertThat(si, is(nullValue()));
    }

    @Test
    public void testisZipCode1() {
        boolean result = ServiceCommon.isZipCode("5300037");
        assertThat(result, is(true));
    }

    @Test
    public void testisZipCode2() {
        boolean result = ServiceCommon.isZipCode("〒530-0037");
        assertThat(result, is(false));
    }

    @Test
    public void testisZipCode3() {
        boolean result = ServiceCommon.isZipCode(null);
        assertThat(result, is(false));
    }

    @Test
    public void testcreditCheckDigit1() {
        int result = ServiceCommon.creditCheckDigit("1234567890123452");
        assertEquals(2, result);
    }

    @Test
    public void testcreditCheckDigit2() {
        int result = ServiceCommon.creditCheckDigit("1234567890123453");
        assertEquals(3, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testcreditCheckDigit3() {
        int result = ServiceCommon.creditCheckDigit(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testcreditCheckDigit4() {
        int result = ServiceCommon.creditCheckDigit("12345678901234567");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testcreditCheckDigit5() {
        int result = ServiceCommon.creditCheckDigit("あ");
    }

}
