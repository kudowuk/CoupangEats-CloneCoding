package com.example.demo.config;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),


    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false,2003,"권한이 없는 유저의 접근입니다."),

    // users
    USERS_EMPTY_USER_ID(false, 2010, "유저 아이디 값을 확인해주세요."),

    // [POST] /users
    POST_USERS_EMPTY_EMAIL(false, 2015, "이메일을 입력해주세요."),
    POST_USERS_INVALID_EMAIL(false, 2016, "이메일 형식을 확인해주세요."),
    POST_USERS_EXISTS_EMAIL(false,2017,"중복된 이메일입니다."),
    POST_USERS_LENGTH_EMAIL(false,2018,"이메일은 30자 이하로 작성해주세요."),
    POST_USERS_EMPTY_PASSWORD(false, 2019, "비밀번호를 입력해주세요."),
    POST_USERS_INVALID_PASSWORD(false, 2020, "'숫자', '문자', '특수문자' 각 1개 이상, 최소 8자에서 최대 16자이하로 작성해주세요"),
    POST_USERS_EMPTY_NAME(false, 2021, "이름을 입력해주세요."),
    POST_USERS_INVALID_NAME(false, 2022, "이름은 2자~20자 이하로 한글 또는 영어만 입력하세요."),
    POST_USERS_EMPTY_PHONE(false, 2023, "휴대폰 번호을 입력해주세요."),
    POST_USERS_INVALID_PHONE(false, 2024, "휴대폰 번호 형식에 맞게 숫자만 입력하세요."),


    // [PATCH] /users
    PATCH_USERID_EXISTS_EMAIL(false,2030,"현재 동일한 이메일입니다."),
    PATCH_USERNAME_EXISTS_EMAIL(false,2031,"현재 동일한 성명입니다."),

    // [POST] /carts
    POST_CARTS_OVER_TOOWNER(false, 2101, "가게 사장님에게 요청사항은 50자 이하로 적어주세요."),
    POST_CARTS_OVER_TODRIVER(false, 2102, "배달 기사님에게 요청사항은 50자 이하로 적어주세요."),

    // [POST] /coupons
    POST_COUPONS_EMPTY_NUMBER(false, 2111, "쿠폰 번호를 입력해주세요."),
    POST_COUPONS_8LENGTH16_NUMBER(false, 2112, "쿠폰 번호를 8자리 또는 16자리로 입력해주세요."),

    // [POST] /reviews
    POST_REVIEWS_OVER_CONTENT(false, 2121, "리뷰 내용을 100자 이하로 적어주세요."),

    // [POST] /addresses
    POST_ADDRESSES_OVER_NAME(false, 2131, "주소 별칭을 30자 이하로 적어주세요."),
    POST_ADDRESSES_INVALID_LATITUDE(false, 2132, "알맞은 국내 위도를 작성해주세요."),
    POST_ADDRESSES_INVALID_LONGITUDE(false, 2133, "알맞은 국내 경도를 작성해주세요."),
    
    
    // [GET] /likes
    GET_LIKES_EMPTY_SORT(false, 2141, "즐겨찾기 정렬값을 입력해주세요."),
    GET_LIKES_INVALID_SORT(false, 2142, "정확한 즐겨찾기 정렬값을 입력해주세요."),

    // [POST] /likes
    POST_LIKES_EMPTY_STOREIDX(false, 2143, "가게 인덱스를 입력해주세요."),

    // [PATCH] /likes
    PATCH_LIKES_EMPTY_STATUS(false, 2144, "즐겨찾기 상태를 입력해주세요"),
    PATCH_LIKES_YN_STATUS(false, 2145, "즐겨찾기 상태를 'Y' 또는 'N'을 입력해주세요"),


    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),

    // [POST] /users
    DUPLICATED_EMAIL(false, 3013, "중복된 이메일입니다."),
    FAILED_TO_LOGIN(false,3014,"없는 아이디거나 비밀번호가 틀렸습니다."),

    // [GET] /users
    NOT_EXIST_USERS(false, 3015, "없는 유저입니다. 다시 확인해주세요."),
    WITHDREW_USERS(false, 3016, "탈퇴한 유저입니다."),

    // [GET] /likes inactivation
    NOT_EXIST_LIKES(false, 3021, "없는 가게입니다. 다시 확인해주세요."),
    INACTIVATION_LIKES(false, 3022, "비활성화되어있는 가게입니다."),


    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),

    //[PATCH] /users/{userIdx}
    MODIFY_FAIL_USERNAME(false,4014,"유저네임 수정 실패"),

    PASSWORD_ENCRYPTION_ERROR(false, 4011, "비밀번호 암호화에 실패하였습니다."),

    //[PATCH] Address
    MODIFY_FAIL_ADDRESS(false,4015,"어드레스 수정 실패"),

    //[PATCH] Like
    MODIFY_FAIL_LIKE(false,4016,"즐겨찾기 수정 실패"),

    //[PATCH] Cart
    MODIFY_FAIL_CART(false,4017,"카트 수정 실패"),

    //[PATCH] Review
    MODIFY_FAIL_REVIEW(false,4018,"리뷰 수정 실패"),


    PASSWORD_DECRYPTION_ERROR(false, 4012, "비밀번호 복호화에 실패하였습니다.");



    // 5000 : 필요시 만들어서 쓰세요
    // 6000 : 필요시 만들어서 쓰세요


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
