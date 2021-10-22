/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.subject.dao;

import com.mycompany.subject.entity.MemberEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author toisu
 */
public class MemberDao {

    private final Connection conn;

    public MemberDao(Connection conn) {
        this.conn = conn;
    }

    //会員登録処理
    public int setAll(MemberEntity me) {

        int r = 0;
        PreparedStatement ps = null;

        try {

            // IDはプライマリキーの生成としてシーケンスを使用
            String sql = "INSERT INTO member (id, name, sex, date_of_birth, postal_code, address, telephone_no, credit_no, creation_date_time)"
                    + "VALUES(nextval('seq01'), ?, ?, ?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, me.getName());
            ps.setString(2, me.getSex());
            ps.setDate(3, me.getDate_of_birth());
            ps.setString(4, me.getPostal_code());
            ps.setString(5, me.getAddress());
            ps.setString(6, me.getTelephone_no());
            ps.setString(7, me.getCredit_no());
            ps.setTimestamp(8, me.getCreation_date_time());
            r = ps.executeUpdate();

            if (r != 0) {
                System.out.println(r + "件のレコードを追加しました");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return r;
    }

    //会員参照処理
    public List<MemberEntity> getMember(String id) {
        List<MemberEntity> data = new ArrayList<MemberEntity>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "select * from member where id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();

            while(rs.next()) {
                MemberEntity me = new MemberEntity();
                me.setId(rs.getString("id"));
                me.setName(rs.getString("name"));
                me.setSex(rs.getString("sex"));
                me.setDate_of_birth(rs.getDate("date_of_birth"));
                me.setPostal_code(rs.getString("postal_code"));
                me.setAddress(rs.getString("address"));
                me.setTelephone_no(rs.getString("telephone_no"));
                me.setCredit_no(rs.getString("credit_no"));
                me.setCreation_date_time(rs.getTimestamp("creation_date_time"));
                data.add(me);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(ps != null) {
                    ps.close();
                }
                if(rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

}
