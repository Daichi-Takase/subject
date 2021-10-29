/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.subject.dao;

import com.mycompany.subject.entity.DetailEntity;
import com.mycompany.subject.entity.MemberDetailEntity;
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
public class DetailDao {

    private final Connection conn;

    public DetailDao(Connection conn) {
        this.conn = conn;
    }

    //明細登録処理
    public int setDetail(DetailEntity de) {

        int r = 0;
        PreparedStatement ps = null;

        try {

            // IDはプライマリキーの生成としてシーケンスを使用
            String sql = "INSERT INTO detail (id, buy_date_time, goods, payment, credit_no, give_point)"
                    + "VALUES(?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, de.getId());
            ps.setTimestamp(2, de.getBuy_date_time());
            ps.setString(3, de.getGoods());
            ps.setInt(4, de.getPayment());
            ps.setString(5, de.getCredit_no());
            ps.setBigDecimal(6, de.getGive_point());
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

    //明細参照処理
    public List<DetailEntity> getDetail(String id) {
        
        List<DetailEntity> data = new ArrayList<DetailEntity>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            String sql = "select * from detail where id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {
                DetailEntity de = new DetailEntity();
                de.setId(rs.getString("id"));
                de.setBuy_date_time(rs.getTimestamp("buy_date_time"));
                de.setGoods(rs.getString("goods"));
                de.setPayment(rs.getInt("payment"));
                de.setCredit_no(rs.getString("credit_no"));
                de.setGive_point(rs.getBigDecimal("give_point"));
                data.add(de);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    //明細会員情報参照（結合）
    public List<MemberDetailEntity> getMemberDetail(String id) {
        
        List<MemberDetailEntity> data = new ArrayList<MemberDetailEntity>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            String sql = "select detail.id, name, sex, date_of_birth, postal_code, address, telephone_no, buy_date_time, goods, payment "
                    + "from detail LEFT OUTER JOIN member ON detail.id = member.id "
                    + "where detail.id = ?";
            
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {
                DetailEntity de = new DetailEntity();
                MemberEntity me = new MemberEntity();
                de.setId(rs.getString("id"));
                me.setName(rs.getString("name"));
                me.setSex(rs.getString("sex"));
                me.setDate_of_birth(rs.getDate("date_of_birth"));
                me.setPostal_code(rs.getString("postal_code"));
                me.setAddress(rs.getString("address"));
                me.setTelephone_no(rs.getString("telephone_no"));
                de.setBuy_date_time(rs.getTimestamp("buy_date_time"));
                de.setGoods(rs.getString("goods"));
                de.setPayment(rs.getInt("payment"));
                MemberDetailEntity mde = new MemberDetailEntity();
                mde.setDe(de);
                mde.setMe(me);
                data.add(mde);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

}
