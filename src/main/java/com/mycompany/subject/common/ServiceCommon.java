/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.subject.common;

import java.util.Arrays;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author toisu
 */
public class ServiceCommon {

    /* 性別コード値変換 */
    public static enum SexInfo {

        MAN(1, "男性"),
        WOMAN(2, "女性");

        final private int cd;
        final private String sex;

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

    /* 郵便番号チェック */
    public static boolean isZipCode(String value) {
        boolean result = false;
        if (value != null) {
            Pattern pattern = Pattern.compile("^[0-9]{7}$");
            result = pattern.matcher(value).matches();
        }
        return result;
    }

    /* クレジットカード（CheckDigit）*/
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

        // 2桁以上は分割し、加算
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
