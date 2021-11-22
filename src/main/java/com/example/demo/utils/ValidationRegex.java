package com.example.demo.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationRegex {

    // 이메일 정규식 표현식
    public static boolean isRegexEmail(String target) {
        String regex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }

    // 비밀번호 정규식 표현식
    public static boolean isRegexPassword(String target) {
        //'숫자', '문자', '특수문자' 무조건 1개 이상, 비밀번호 '최소 8자에서 최대 16자' 허용
        // (특수문자는 정의된 특수문자만 사용 가능)
        // ^(?=.*[A-Za-z])(?=.*\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\d~!@#$%^&*()+|=]{8,16}$

        // '숫자', '문자' 무조건 1개 이상, '최소 8자에서 최대 20자' 허용
        // (특수문자는 정의된 특수문자만 사용 가능)
        // ^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d~!@#$%^&*()+|=]{8,20}$

        String regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }

    // 휴대폰 번호 정규식 표현식
    public static boolean isRegexPhone(String target) {
        // 핸드폰번호 정규식
        // ^\d{3}-\d{3,4}-\d{4}$

        // 일반 전화번호 정규식
        // ^\d{2,3}-\d{3,4}-\d{4}$

        //  010-1234-1234 또는 01012341234
        // ^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$

        // 숫자만 전화번호 정규식
        // ^01(?:0|1|[6-9])[0-9]{4}[0-9]{4}$
        // ^01(?:0|1|[6-9])(\\d{3,4})(\\d{4})$
        String regex = "^01(?:0|1|[6-9])(\\d{3,4})(\\d{4})$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }

    // 이름 정규식 표현식
    public static boolean isRegexName(String target) {
        // 한글 이름만
        // ^[가-힣]{2, 20}$

        // 한글과 영어이름 둘다 가능
        // ^[a-zA-Z가-힣]{2, 20}$

        String regex = "^[a-zA-Z가-힣]{2,20}$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }

    // 위도 정규식 표현식
    public static boolean isRegexLatitude(String target) {
        String regex = "^(\\+|-)?(?:90(?:(?:\\.0{1,6})?)|(?:[0-9]|[1-8][0-9])(?:(?:\\.[0-9]{1,6})?))$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }

    // 경도 정규식 표현식식
    public static boolean isRegexLongitude(String target) {
        String regex = "^(\\+|-)?(?:180(?:(?:\\.0{1,6})?)|(?:[0-9]|[1-9][0-9]|1[0-7][0-9])(?:(?:\\.[0-9]{1,6})?))$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }
}

