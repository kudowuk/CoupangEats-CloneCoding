package com.example.demo.src.user;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.*;

@RestController
@RequestMapping("/app/users")
public class UserController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final UserProvider userProvider;
    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final UserDao userDao;


    public UserController(UserProvider userProvider, UserService userService, JwtService jwtService, UserDao userDao){
        this.userProvider = userProvider;
        this.userService = userService;
        this.jwtService = jwtService;
        this.userDao = userDao;
    }

    /**
     * 회원 조회 API
     * [GET] /users
     * 회원 번호 및 이메일 검색 조회 API
     * [GET] /users? Email=
     * @return BaseResponse<List<GetUserRes>>
     */
    //Query String
    @ResponseBody
    @GetMapping("") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<List<GetUserRes>> getUsers(@RequestParam(required = false) String userId) {
        try{
            if(userId == null){
                List<GetUserRes> getUserRes = userProvider.getUsers();
                return new BaseResponse<>(getUserRes);
            }
            // Get Users
            List<GetUserRes> getUserRes = userProvider.getUsersByEmail(userId);
            return new BaseResponse<>(getUserRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 회원 1명 조회 API
     * [GET] /user/:userIdx
     * @return BaseResponse<GetUserRes>
     */
    // Path-variable
    @ResponseBody
    @GetMapping("/{userIdx}") // (GET) 127.0.0.1:9000/app/users/:userIdx
    public BaseResponse<GetUserRes> getUser(@PathVariable("userIdx") int userIdx) {
        // Get Users
        try{
            // 유저 유무 확인
            if(userDao.checkUserIdx(userIdx) == 0) {
                throw new BaseException(NOT_EXIST_USERS);
            }
            // 유저 탈퇴 확인
            if(userDao.checkStatusUserIdx(userIdx) == 1) {
                throw new BaseException(WITHDREW_USERS);
            }

            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            GetUserRes getUserRes = userProvider.getUser(userIdx);
            return new BaseResponse<>(getUserRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /**
     * 회원가입 API
     * [POST] /users
     * @return BaseResponse<PostUserRes>
     */
    // Body
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostUserRes> createUser(@RequestBody PostUserReq postUserReq) {
        // TODO: email 관련한 짧은 validation 예시입니다. 그 외 더 부가적으로 추가해주세요!


        if(postUserReq.getUserId() == null){
            return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
        }
        //이메일 정규표현
        if(!isRegexEmail(postUserReq.getUserId())){
            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
        }
        if(postUserReq.getUserId().length() > 30){
            return new BaseResponse<>(POST_USERS_LENGTH_EMAIL);
        }


        if(postUserReq.getUserPwd() == null){
            return new BaseResponse<>(POST_USERS_EMPTY_PASSWORD);
        }
        //비밀번호 정규표현
        if(!isRegexPassword(postUserReq.getUserPwd())){
            return new BaseResponse<>(POST_USERS_INVALID_PASSWORD);
        }


        if(postUserReq.getUserName() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_NAME);
        }
        // 이름 정규표현
        if(!isRegexName(postUserReq.getUserName())) {
            return new BaseResponse<>(POST_USERS_INVALID_NAME);
        }


        if(postUserReq.getUserPhone() == null){
            return new BaseResponse<>(POST_USERS_EMPTY_PHONE);
        }
        // 휴대폰 정규표현
        if(!isRegexPhone(postUserReq.getUserPhone())){
            return new BaseResponse<>(POST_USERS_INVALID_PHONE);
        }


        try{
            PostUserRes postUserRes = userService.createUser(postUserReq);
            return new BaseResponse<>(postUserRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /**
     * 로그인 API
     * [POST] /users/logIn
     * @return BaseResponse<PostLoginRes>
     */
    @ResponseBody
    @PostMapping("/logIn")
    public BaseResponse<PostLoginRes> logIn(@RequestBody PostLoginReq postLoginReq){
        try{
            // TODO: 로그인 값들에 대한 형식적인 validatin 처리해주셔야합니다!
            // TODO: 유저의 status ex) 비활성화된 유저, 탈퇴한 유저 등을 관리해주고 있다면 해당 부분에 대한 validation 처리도 해주셔야합니다.
            PostLoginRes postLoginRes = userProvider.logIn(postLoginReq);
            return new BaseResponse<>(postLoginRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // 카카오 로그인 API
//    @ResponseBody
//    @PostMapping("/login/kakao")
//    public BaseResponse<PostLoginRes> kakaoLogin(@RequestBody PostKakaoLoginReq postKakaoLogin) {
//
//        try {
//            // 엑세스 토큰으로 사용자 정보 받기
//            KaKaoUserInfo kaKaoUserInfo = KakaoApiService.getKakaoUserInfo(postKakaoLogin.getAccessToken());
//
//            // 로그인 or 회원가입 진행 후 jwt, userIdx 반환하기
//            PostLoginRes postLoginRes = userProvider.kakaoLogin(kaKaoUserInfo);
//            return new BaseResponse<>(postLoginRes);
//        } catch (BaseException exception) {
//            return new BaseResponse<>(exception.getStatus());
//        }
//    }


    /**
     * 유저정보변경 API
     * [PATCH] /users/:userIdx
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/{userIdx}")
    public BaseResponse<String> modifyUserName(@PathVariable("userIdx") int userIdx, @RequestBody User user){

        try {
            if(userProvider.checkCurrentUserId(user.getUserId()) == userIdx){
                throw new BaseException(PATCH_USERID_EXISTS_EMAIL);
            }

            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            //같다면 유저네임 변경
            PatchUserReq patchUserReq = new PatchUserReq(userIdx, user.getUserId(), user.getUserPwd(), user.getUserName(), user.getUserPhone());
            userService.modifyUserName(patchUserReq);

            String result = "";
        return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
