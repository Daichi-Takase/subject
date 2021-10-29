/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.subject.service;

import com.mycompany.subject.common.ServiceCommon;
import com.mycompany.subject.dao.DetailDao;
import com.mycompany.subject.dto.DetailDto;
import com.mycompany.subject.dto.MemberDetailDto;
import com.mycompany.subject.entity.DetailEntity;
import com.mycompany.subject.entity.MemberDetailEntity;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author toisu
 */
public class DetailService {

    //接続文字列
    String url = "jdbc:postgresql://localhost:5432/postgres";
    String user = "postgres";
    String password = "postgres";

    /* 明細登録実行 */
    public static int insertDetail(DetailDto dDto, String url, String user, String password) {

        int record = 0;
        boolean exeFlg = true;
        Connection conn = null;

        try {
            //PostgreSQLへ接続
            conn = DriverManager.getConnection(url, user, password);

            DetailEntity detailEntity = new DetailEntity();
            DetailDao detailDao = new DetailDao(conn);

            /* DB更新項目セット */
            //ID
            detailEntity.setId(dDto.getId());
            //購入日時
            LocalDateTime localDateTime = LocalDateTime.now();
            java.sql.Timestamp buy_date_time = java.sql.Timestamp.valueOf(localDateTime);
            detailEntity.setBuy_date_time(buy_date_time);
            //商品
            detailEntity.setGoods(dDto.getGoods());
            //料金
            detailEntity.setPayment(Integer.parseInt(dDto.getPayment()));
            //クレジットカード番号
            if (ServiceCommon.creditCheckDigit(dDto.getCredit_no()) == Integer.parseInt(dDto.getCredit_no().substring(15, 16))) {
                detailEntity.setCredit_no(dDto.getCredit_no());
            } else {
                exeFlg = false;
                System.out.println("クレジットカード番号が不正です。");
            }
            //付与ポイント
            BigDecimal give_point = new BigDecimal(dDto.getGive_point());
            detailEntity.setGive_point(give_point);

            if (exeFlg) {
                record = detailDao.setDetail(detailEntity);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return record;
    }

    /* 明細集計参照実行 */
    public static DetailDto selectDetailSum(DetailDto dDto, String date, String url, String user, String password) {

        Connection conn = null;
        DetailDto detailDto = new DetailDto();

        try {

            //PostgreSQLへ接続
            conn = DriverManager.getConnection(url, user, password);
            DetailDao detailDao = new DetailDao(conn);

            //対象IDの明細情報を取得
            List<DetailEntity> data = new ArrayList<DetailEntity>();
            data = detailDao.getDetail(dDto.getId());

            //指定日付の使用料を集計
            int sum = data.stream().filter(d -> new SimpleDateFormat("yy/MM/dd").format(d.getBuy_date_time()).equals(date)).collect(Collectors.summingInt(d -> d.getPayment()));
            System.out.println(sum + "円");
            //合計値をセット
            detailDto.setPaymanet_sum(String.valueOf(sum));

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();

            }
        }
        return detailDto;
    }

    /* 会員明細情報参照 */
    public static MemberDetailDto selectMemberDetail(DetailDto dDto, String url, String user, String password) {

        Connection conn = null;
        MemberDetailDto mdDto = new MemberDetailDto();

        try {

            //PostgreSQLへ接続
            conn = DriverManager.getConnection(url, user, password);
            DetailDao detailDao = new DetailDao(conn);
            List<MemberDetailEntity> data = new ArrayList<MemberDetailEntity>();

            data = detailDao.getMemberDetail(dDto.getId());

            // 生年月日フォーマット
            DateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
            // 購入日時フォーマット
            SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyy年MM月dd日(E) HH時mm分");

            data.stream().forEach(mde -> {

                // 性別値取得
                ServiceCommon.SexInfo si = ServiceCommon.SexInfo.getBySex(Integer.parseInt(mde.getMe().getSex()));

                // 出力確認用
                System.out.println(mde.getDe().getId() + ", "
                        + mde.getMe().getName() + ", "
                        + si.getSex() + ", "
                        + dateFormat.format(mde.getMe().getDate_of_birth()) + ", "
                        + "〒" + mde.getMe().getPostal_code() + ", "
                        + mde.getMe().getAddress() + ", "
                        + mde.getMe().getTelephone_no() + ", "
                        + timestampFormat.format(mde.getDe().getBuy_date_time()) + ", "
                        + mde.getDe().getGoods() + ", "
                        + String.format("%,d", mde.getDe().getPayment()));

                // 画面出力値セット（DTO）
                mdDto.setId(mde.getDe().getId());
                mdDto.setName(mde.getMe().getName());
                if (si != null) {
                    mdDto.setSex(si.getSex());
                }
                mdDto.setDate_of_birth(dateFormat.format(mde.getMe().getDate_of_birth()));
                mdDto.setPostal_code("〒" + mde.getMe().getPostal_code());
                mdDto.setAddress(mde.getMe().getAddress());
                mdDto.setTelephone_no(mde.getMe().getTelephone_no());
                mdDto.setBuy_date_time(timestampFormat.format(mde.getDe().getBuy_date_time()));
                mdDto.setGoods(mde.getDe().getGoods());
                mdDto.setPayment(String.format("%,d", mde.getDe().getPayment()));
            });

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return mdDto;
    }

}
