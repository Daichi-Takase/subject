/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.subject.dao;

import com.mycompany.subject.dto.MemberDto;
import com.mycompany.subject.entity.MemberEntity;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author toisu
 */
public class MemberDaoTest {

    /*
    @Test
    public void testsetAll1() {
        try {
            MemberDao mDao = new MemberDao(DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres"));
            MemberEntity me = new MemberEntity();
            int result = mDao.setAll(me);
            assertEquals(1, result);
        } catch (SQLException e) {
        }
    }
    */
    
    @Test
    public void test1() {
        try {
            MemberDao mDao = new MemberDao(DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres"));
            List<MemberEntity> meList = new ArrayList<MemberEntity>();
            meList = mDao.getMember("3");
            for (MemberEntity result : meList) {
                assertThat(result.getName(), is("田中"));
            }
        } catch (SQLException e) {
        }
    }

}
