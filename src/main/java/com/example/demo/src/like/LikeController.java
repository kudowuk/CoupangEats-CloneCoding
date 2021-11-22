package com.example.demo.src.like;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.like.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/app/likes")
public class LikeController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final LikeProvider likeProvider;
    @Autowired
    private final LikeService likeService;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final LikeDao likeDao;

    public LikeController(LikeProvider likeProvider, LikeService likeService, JwtService jwtService, LikeDao likeDao){
        this.likeProvider = likeProvider;
        this.likeService = likeService;
        this.jwtService = jwtService;
        this.likeDao = likeDao;
    }

    // 유저가 즐겨찾기한 가게 조회하기
    @ResponseBody
    @GetMapping("/{userIdx}") // (GET) 127.0.0.1:9000/app/likes/userIdx?=1
    public BaseResponse<List<GetLikeRes>> getLikes(@PathVariable("userIdx") int userIdx, @RequestParam String sort) {

        if(sort.isEmpty()) {
            return new BaseResponse<>(GET_LIKES_EMPTY_SORT);
        } else if (!sort.equalsIgnoreCase("recentAdd")) {
            return new BaseResponse<>(GET_LIKES_INVALID_SORT);
        }

        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            List<GetLikeRes> getLikeRes = likeProvider.getLikes(userIdx, sort);
            return new BaseResponse<>(getLikeRes);

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 즐겨찾기 등록 API
    @ResponseBody
    @PostMapping("/{userIdx}") // (POST) 127.0.0.1:9000/app/likes/userIdx?=1
    public BaseResponse<PostLikeRes> createLike(@PathVariable("userIdx") int userIdx, @RequestBody PostLikeReq postLikeReq) {

        if(postLikeReq.getStoreIdx() == null) {
            return new BaseResponse<>(POST_LIKES_EMPTY_STOREIDX);
        }

        try{

            // 가게 유무 확인
            if(likeDao.checkStoreIdx(postLikeReq.getStoreIdx()) == 0) {
                throw new BaseException(NOT_EXIST_LIKES);
            }
            // 가게 활성화 확인
            if(likeDao.checkStatusStoreIdx(postLikeReq.getStoreIdx()) == 1) {
                throw new BaseException(INACTIVATION_LIKES);
            }
//            // 이미 즐겨찾기 들어가 있으면 뱉어주기
//            if(likeDao.checkDuplicateStoreIdx() == userIdx) {
//                throw new BaseException(INACTIVATION_LIKES);
//            }

            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            PostLikeRes postLikeRes = likeService.createLike(userIdx, postLikeReq);
            return new BaseResponse<>(postLikeRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 즐겨찾기 수정 API
    @ResponseBody
    @PatchMapping("/{userIdx}/{likeIdx}")
    public BaseResponse<String> modifyLike(@PathVariable("userIdx") int userIdx, @PathVariable("likeIdx") int likeIdx, @RequestBody Like like){

        if (like.getStatus().isEmpty()) {
            return new BaseResponse<>(PATCH_LIKES_EMPTY_STATUS);
        }
        if (!like.getStatus().equals("Y") && ! like.getStatus().equals("N")) {
            return new BaseResponse<>(PATCH_LIKES_YN_STATUS);
        }


        try {

            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            PatchLikeReq patchLikeReq = new PatchLikeReq(userIdx, likeIdx, like.getStatus());
            likeService.modifyLike(patchLikeReq);

            String result = "";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
