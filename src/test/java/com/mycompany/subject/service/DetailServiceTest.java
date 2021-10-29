/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.subject.service;

import com.mycompany.subject.dto.DetailDto;
import com.mycompany.subject.dto.MemberDetailDto;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author toisu
 */
public class DetailServiceTest {

    //接続文字列
    String url = "jdbc:postgresql://localhost:5432/postgres";
    String user = "postgres";
    String password = "postgres";

    DetailDto detailDto;

    @Before
    public void setup() {

        detailDto = new DetailDto();
        //ID
        detailDto.setId("2");
        //商品
        detailDto.setGoods("参考書");
        //料金
        detailDto.setPayment("5000");
        //クレジットカード番号
        detailDto.setCredit_no("1234567890123452");
        //付与ポイント
        detailDto.setGive_point("50");

    }

    @Test
    public void testinsertDetail1() {

        int result = DetailService.insertDetail(detailDto, url, user, password);
        assertEquals(1, result);

    }

    @Test
    public void testinsertDetail2() {

        detailDto.setCredit_no("1234567890123453");

        int result = DetailService.insertDetail(detailDto, url, user, password);
        assertEquals(0, result);

    }

    @Test
    public void testselectDetailSum1() {

        detailDto.setId("1");

        DetailDto result = DetailService.selectDetailSum(detailDto, "21/10/20", url, user, password);
        assertThat(result.getPaymanet_sum(), is("6550"));

    }

    @Test
    public void testselectMemberDetail1() {

        detailDto.setId("1");

        MemberDetailDto result = DetailService.selectMemberDetail(detailDto, url, user, password);

        assertThat(result.getId(), is("1   "));
        assertThat(result.getName(), is("結合"));
        assertThat(result.getSex(), is("女性"));
        assertThat(result.getDate_of_birth(), is("2021年10月27日"));
        assertThat(result.getPostal_code(), is("〒5300037"));
        assertThat(result.getAddress(), is("大阪府大阪市北区"));
        assertThat(result.getTelephone_no(), is("08012345678"));
        assertThat(result.getBuy_date_time(), is("2021年10月20日(水) 16時48分"));
        assertThat(result.getGoods(), is("参考書"));
        assertThat(result.getPayment(), is("3,550"));

    }

}
