package com.example.demo.src.coupon;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.coupon.model.GetCouponRes;
import com.example.demo.src.coupon.model.PostCouponReq;
import com.example.demo.src.coupon.model.PostCouponRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/app/coupons")
public class CouponController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final CouponProvider couponProvider;
    @Autowired
    private final CouponService couponService;
    @Autowired
    private final JwtService jwtService;


    public CouponController(CouponProvider couponProvider, CouponService couponService, JwtService jwtService){
        this.couponProvider = couponProvider;
        this.couponService = couponService;
        this.jwtService = jwtService;
    }

    // GET 쿠폰 조회 하기 API
    @ResponseBody
    @GetMapping("/{userIdx}")
    public BaseResponse<List<GetCouponRes>> getCoupons(@PathVariable("userIdx") int userIdx, @PathVariable("storeIdx") int storeIdx) {
        try {

            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            List<GetCouponRes> getCouponRes = couponProvider.getCoupons(userIdx, storeIdx);
            return new BaseResponse<>(getCouponRes);

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // POST 쿠폰 등록 API
    @ResponseBody
    @PostMapping("/{userIdx}")
    public BaseResponse<PostCouponRes> createCoupon(@PathVariable("userIdx") int userIdx, @RequestBody PostCouponReq postCouponReq) {

        if(postCouponReq.getCouponNo().equals("")){
            return new BaseResponse<>(POST_COUPONS_EMPTY_NUMBER);
        }

        if(postCouponReq.getCouponNo().length() != 8 && postCouponReq.getCouponNo().length() != 16) {
            return new BaseResponse<>(POST_COUPONS_8LENGTH16_NUMBER);
        }

        try{

            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            PostCouponRes postCouponRes = couponService.createCoupon(userIdx, postCouponReq);
            return new BaseResponse<>(postCouponRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
