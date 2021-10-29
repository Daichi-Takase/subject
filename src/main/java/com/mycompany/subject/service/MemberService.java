/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.subject.service;

import com.mycompany.subject.common.ServiceCommon;
import com.mycompany.subject.dao.MemberDao;
import com.mycompany.subject.dto.MemberDto;
import com.mycompany.subject.entity.MemberEntity;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author toisu
 */
public class MemberService {

    //接続文字列
    String url = "jdbc:postgresql://localhost:5432/postgres";
    String user = "postgres";
    String password = "postgres";

    /* 会員登録実行 */
    public static int insertMember(MemberDto mDto, String url, String user, String password) {

        int record = 0;
        boolean exeFlg = true;
        Connection conn = null;

        try {
            //PostgreSQLへ接続
            conn = DriverManager.getConnection(url, user, password);

            MemberEntity memberEntity = new MemberEntity();
            MemberDao memberDao = new MemberDao(conn);

            /* DB更新項目セット */
            //名前
            memberEntity.setName(mDto.getName());
            //性別
            ServiceCommon.SexInfo si = ServiceCommon.SexInfo.getByCd(mDto.getSex());
            if (si != null) {
                memberEntity.setSex(String.valueOf(si.getCd()));
            } else {
                exeFlg = false;
                System.out.println("性別が不正です。");
            }

            //生年月日
            String ymd = mDto.getYear() + "-" + mDto.getMonth() + "-" + mDto.getDay();
            try {
                LocalDate.parse(ymd, DateTimeFormatter.ofPattern("uuuu-MM-dd").withResolverStyle(ResolverStyle.STRICT));
                java.sql.Date date_of_birth = java.sql.Date.valueOf(ymd);
                memberEntity.setDate_of_birth(date_of_birth);
            } catch (Exception dtp) {
                dtp.printStackTrace();
                exeFlg = false;
                System.out.println("生年月日が不正です。");
            }
            //郵便番号
            if (ServiceCommon.isZipCode(mDto.getPostal_code())) {
                memberEntity.setPostal_code(mDto.getPostal_code());
            } else {
                exeFlg = false;
                System.out.println("郵便番号が不正です。");
            }
            //住所
            memberEntity.setAddress(mDto.getAddress());
            //電話番号
            memberEntity.setTelephone_no(mDto.getTelephone_no());
            //クレジットカード番号
            if (ServiceCommon.creditCheckDigit(mDto.getCredit_no()) == Integer.parseInt(mDto.getCredit_no().substring(15, 16))) {
                memberEntity.setCredit_no(mDto.getCredit_no());
            } else {
                exeFlg = false;
                System.out.println("クレジットカード番号が不正です。");
            }
            //登録日
            LocalDateTime localDateTime = LocalDateTime.now();
            java.sql.Timestamp creation_date_time = java.sql.Timestamp.valueOf(localDateTime);
            memberEntity.setCreation_date_time(creation_date_time);

            if (exeFlg) {
                record = memberDao.setMember(memberEntity);
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

    /* 会員参照実行 */
    public static MemberDto selectMember(MemberDto mDto, String url, String user, String password) {

        Connection conn = null;
        MemberDto resultDto = new MemberDto();

        try {

            //PostgreSQLへ接続
            conn = DriverManager.getConnection(url, user, password);

            MemberDao memberDao = new MemberDao(conn);
            List<MemberEntity> data = new ArrayList<MemberEntity>();

            data = memberDao.getMember(mDto.getId());

            // 生年月日フォーマット
            DateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
            // 購入日時フォーマット
            SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyy年MM月dd日(E) HH時mm分");

            data.stream().forEach(md -> {
                // 出力確認用
                System.out.println(md.getId() + ", "
                        + md.getName() + ", "
                        + md.getSex() + ", "
                        + md.getDate_of_birth() + ", "
                        + md.getPostal_code() + ", "
                        + md.getAddress() + ", "
                        + md.getTelephone_no() + ", "
                        + md.getCredit_no() + ", "
                        + md.getCreation_date_time());

                // 画面出力値セット（DTO）
                resultDto.setId(md.getId());
                resultDto.setName(md.getName());
                resultDto.setSex(md.getSex());
                resultDto.setDate_of_birth(dateFormat.format(md.getDate_of_birth()));
                resultDto.setPostal_code(md.getPostal_code());
                resultDto.setAddress(md.getAddress());
                resultDto.setTelephone_no(md.getTelephone_no());
                resultDto.setCredit_no(md.getCredit_no());
                resultDto.setCreation_date_time(timestampFormat.format(md.getCreation_date_time()));
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
        return resultDto;
    }

}
