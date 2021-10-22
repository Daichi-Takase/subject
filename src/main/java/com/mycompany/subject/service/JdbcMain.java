/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.subject.service;

import com.mycompany.subject.dao.MemberDao;
import com.mycompany.subject.dto.MemberDto;
import com.mycompany.subject.entity.MemberEntity;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author toisu
 */
public class JdbcMain {

    public static void main(String[] args) {
        Connection conn = null;

        //接続文字列
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "postgres";

        try {
            //PostgreSQLへ接続
            conn = DriverManager.getConnection(url, user, password);

            MemberDto memberDto = new MemberDto();
            MemberEntity memberEntity = new MemberEntity();
            MemberDao memberDao = new MemberDao(conn);

            /* 画面入力項目セット（仮）*/
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
            memberDto.setCredit_no("1234567890123456");

            /* DB更新項目セット */
            //名前
            memberEntity.setName(memberDto.getName());
            //性別
            SexInfo si = SexInfo.getByCd(memberDto.getSex());
            memberEntity.setSex(String.valueOf(si.getCd()));
            //生年月日
            String ymd = memberDto.getYear() + "-" + memberDto.getMonth() + "-" + memberDto.getDay();
            try {
                LocalDate.parse(ymd, DateTimeFormatter.ofPattern("uuuu-MM-dd").withResolverStyle(ResolverStyle.STRICT));
            } catch (DateTimeParseException dtp) {
                dtp.printStackTrace();
            }
            java.sql.Date date_of_birth = java.sql.Date.valueOf(ymd);
            memberEntity.setDate_of_birth(date_of_birth);
            //郵便番号
            if (isZipCode(memberDto.getPostal_code())) {
                memberEntity.setPostal_code(memberDto.getPostal_code());
            } else {
                memberEntity.setPostal_code("");
            }
            //住所
            memberEntity.setAddress(memberDto.getAddress());
            //電話番号
            memberEntity.setTelephone_no(memberDto.getTelephone_no());
            //クレジットカード番号
            memberEntity.setCredit_no(memberDto.getCredit_no());
            //登録日
            LocalDateTime localDateTime = LocalDateTime.now();
            java.sql.Timestamp creation_date_time = java.sql.Timestamp.valueOf(localDateTime);
            memberEntity.setCreation_date_time(creation_date_time);

            /* 会員登録実行 */
            //memberDao.setAll(memberEntity);
            
            /* 会員参照実行 */
            List<MemberEntity> data = new ArrayList<MemberEntity>();
            data = memberDao.getMember("3");
            for (MemberEntity d : data) {
                System.out.println(d.getName());
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
    }

    //性別コード変換
    public static enum SexInfo {

        MAN(1, "男性"),
        WOMAN(2, "女性");

        private int cd;
        private String sex;

        private SexInfo(int cd, String sex) {
            this.cd = cd;
            this.sex = sex;
        }

        public int getCd() {
            return cd;
        }

        public String getSex() {
            return sex;
        }

        //性別に一致するコードを返却
        public static SexInfo getByCd(String sex) {
            for (SexInfo si : SexInfo.values()) {
                if (si.getSex().equals(sex)) {
                    return si;
                }
            }
            return null;
        }

        //コードに一致する性別を返却
        public static SexInfo getBySex(int cd) {
            for (SexInfo si : SexInfo.values()) {
                if (si.getCd() == cd) {
                    return si;
                }
            }
            return null;
        }
        
    }

    //郵便番号チェック
    public static boolean isZipCode(String value) {
        boolean result = false;
        if (value != null) {
            Pattern pattern = Pattern.compile("^[0-9]{7}$");
            result = pattern.matcher(value).matches();
        }
        return result;
    }

}
