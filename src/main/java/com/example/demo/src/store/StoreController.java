package com.example.demo.src.store;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.store.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.INVALID_USER_JWT;

@RestController
@RequestMapping("/app/stores")
public class StoreController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final StoreProvider storeProvider;
    @Autowired
    private final StoreService storeService;
    @Autowired
    private final JwtService jwtService;

    public StoreController(StoreProvider storeProvider, StoreService storeService, JwtService jwtService){
        this.storeProvider = storeProvider;
        this.storeService = storeService;
        this.jwtService = jwtService;
    }

    /**
     * 가게 조회 API
     * [GET] /stores
     * 가게 인덱스 및 가게명 검색 조회 API
     * [GET] /stores? storeName=
     * @return BaseResponse<List < GetStoreRes>>
     */
    //Query String
    // 태그로 가게들 조회하기
    @ResponseBody
    @GetMapping("/tag") // (GET) 127.0.0.1:9000/app/stoes/tag?tag=중식
    public BaseResponse<List<GetStoreRes>> getStores(@RequestParam(required = false) String tag) {
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();

            if(userIdxByJwt != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }

                List<GetStoreRes> getStoreRes = storeProvider.getStores(tag);
                return new BaseResponse<>(getStoreRes);

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


// 검색 API
//    @ResponseBody
//    @GetMapping("") // (GET) 127.0.0.1:9000/app/stores?storeName=BBQ
//    public BaseResponse<List<GetStoreRes>> getStores(@RequestParam(required = false) String tag) {
//        try {
//            if (storeName == null) {
//                List<GetStoreRes> getStoreRes = storeProvider.getStores();
//                return new BaseResponse<>(getStoreRes);
//            }
//            // Get Stores
//            List<GetStoreRes> getStoreRes = storeProvider.getStoresByTag(tag);
//            return new BaseResponse<>(getStoreRes);
//        } catch (BaseException exception) {
//            return new BaseResponse<>((exception.getStatus()));
//        }
//    }

//


    /**
     * 특정가게 상세조회 API
     * [GET] /store/:storeIdx
     *
     * @return BaseResponse<GetStoreRes>
     */
    // Path-variable
    @ResponseBody
    @GetMapping("/{storeIdx}/detail") // (GET) 127.0.0.1:9000/app/stores/:storeIdx/detail
    public BaseResponse<GetStoreDetailRes> getStoreDetail(@PathVariable("storeIdx") int storeIdx) {
        // Get Stores
        try {

            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();

            if(userIdxByJwt != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            GetStoreDetailRes getStoreDetailRes = storeProvider.getStoreDetail(storeIdx);
            return new BaseResponse<>(getStoreDetailRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }


    /** SSAC
     * 특정 가게(매뉴, 리뷰 등) 조회 API
     * [GET] /store/:storeIdx
     *
     * @return BaseResponse<GetStoreRes>
     */
    // Path-variable
    @ResponseBody
    @GetMapping("/{storeIdx}") // (GET) 127.0.0.1:9000/app/stores/:storeIdx
    public BaseResponse<GetStorepageRes> getStorepage(@PathVariable("storeIdx") int storeIdx) {
        // Get Stores
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();

            if(userIdxByJwt != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            GetStorepageRes getStorepageRes = storeProvider.getStorepage(storeIdx);
            return new BaseResponse<>(getStorepageRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }


    /** SSAC
     * 메인 조회 API
     * [GET] /stores
     * 가게 인덱스 및 가게명 검색 조회 API
     * [GET] /stores? storeName=
     * @return BaseResponse<List < GetStoreRes>>
     */
    //Query String
    @ResponseBody
    @GetMapping("") // (GET) 127.0.0.1:9000/app/stores/
    public BaseResponse<List<GetMainRes>> getMain(@RequestParam(required = false) String storeName) {
        try {

            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();

            if(userIdxByJwt != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            if (storeName == null) {
                List<GetMainRes> getMainRes = storeProvider.getMain();
                return new BaseResponse<>(getMainRes);
            }
            // Get
            List<GetMainRes> getMainRes = storeProvider.getMainByStoreName(storeName);
            return new BaseResponse<>(getMainRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}

