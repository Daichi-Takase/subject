/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.subject.service;

import com.mycompany.subject.dao.MemberDao;
import com.mycompany.subject.dto.MemberDto;
import com.mycompany.subject.entity.MemberEntity;
import static com.mycompany.subject.service.JdbcMain.insertMember;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author toisu
 */
public class JdbcMain {

    public static void main(String[] args) {

        //接続文字列
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "postgres";

        MemberDto memberDto = new MemberDto();
        int record = insertMember(memberDto, url, user, password);

    }

    /* 会員登録実行 */
    public static int insertMember(MemberDto mDto, String url, String user, String password) {

        int r = 0;
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
            SexInfo si = SexInfo.getByCd(mDto.getSex());
            if (si != null) {
                memberEntity.setSex(String.valueOf(si.getCd()));
            }
            exeFlg = false;
            System.out.println("性別が不正です。");

            //生年月日
            String ymd = mDto.getYear() + "-" + mDto.getMonth() + "-" + mDto.getDay();
            try {
                LocalDate.parse(ymd, DateTimeFormatter.ofPattern("uuuu-MM-dd").withResolverStyle(ResolverStyle.STRICT));
            } catch (DateTimeParseException dtp) {
                dtp.printStackTrace();
                exeFlg = false;
                System.out.println("生年月日が不正です。");
            }
            java.sql.Date date_of_birth = java.sql.Date.valueOf(ymd);
            memberEntity.setDate_of_birth(date_of_birth);
            //郵便番号
            if (isZipCode(mDto.getPostal_code())) {
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
            if (creditCheckDigit(mDto.getCredit_no()) == Integer.parseInt(mDto.getCredit_no().substring(15, 16))) {
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
                r = memberDao.setAll(memberEntity);
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
        return r;
    }

    /* 会員参照実行 */
    public static void selectMember(MemberDto mDto, String url, String user, String password) {

        Connection conn = null;

        try {

            //PostgreSQLへ接続
            conn = DriverManager.getConnection(url, user, password);

            MemberEntity memberEntity = new MemberEntity();
            MemberDao memberDao = new MemberDao(conn);
            List<MemberEntity> data = new ArrayList<MemberEntity>();

            data = memberDao.getMember(mDto.getId());
            for (MemberEntity d : data) {
                System.out.println(d.getName() + ", " + d.getSex() + ", " + d.getDate_of_birth());
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

            return Arrays.stream(SexInfo.values())
                    .filter(data -> data.getSex().equals(sex))
                    .findFirst()
                    .orElse(null);
        }
        //コードに一致する性別を返却

        public static SexInfo getBySex(int cd) {

            return Arrays.stream(SexInfo.values())
                    .filter(data -> data.getCd() == (cd))
                    .findFirst()
                    .orElse(null);
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

    //クレジットカード（CheckDigit）
    public static int creditCheckDigit(String creditNo) {

        //引数チェック
        if (creditNo == null) {
            throw new IllegalArgumentException("引数にnullは指定不可");
        }
        if (creditNo.length() != 16) {
            throw new IllegalArgumentException("引数は16桁必須");
        }
        if (!StringUtils.isNumeric(creditNo)) {
            throw new IllegalArgumentException("引数は数値必須");
        }

        char[] creditNoArgs = creditNo.toCharArray();
        String[] calcResultArgs = new String[15];
        
        for (int i = 0; i < creditNoArgs.length - 1; i++) {
            // 1桁目（i = 0：1回目）の場合、係数×２
            if (i % 2 == 0) {
                calcResultArgs[14 - i] = String.valueOf(Character.getNumericValue(creditNoArgs[14 - i]) * 2);
            } else {
                calcResultArgs[14 - i] = String.valueOf(Character.getNumericValue(creditNoArgs[14 - i]) * 1);
            }
        }
        
        //2桁以上は分割し、加算
        int calcTotal = 0;
        for (String cra : calcResultArgs) {
            if (cra.length() >= 2) {
                calcTotal = calcTotal + Integer.parseInt(cra.substring(0, 1));
                calcTotal = calcTotal + Integer.parseInt(cra.substring(1, 2));
            } else {
                calcTotal = calcTotal + Integer.parseInt(cra);
            }
        }

        // 10で割った余り
        int amari = calcTotal % 10;

        // 10-(10で割ったあまり)がチェックディジット
        int digit = 10 - amari;
        
        return digit;
        
    }
}
