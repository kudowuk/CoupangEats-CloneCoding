package com.example.demo.src.coupon;

import com.example.demo.config.BaseException;
import com.example.demo.src.coupon.model.PostCouponReq;
import com.example.demo.src.coupon.model.PostCouponRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@Transactional(rollbackFor = BaseException.class)
public class CouponService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CouponDao couponDao;
    private final CouponProvider couponProvider;

    @Autowired
    public CouponService(CouponDao couponDao, CouponProvider couponProvider) {
        this.couponDao = couponDao;
        this.couponProvider = couponProvider;
    }

    //POST
    public PostCouponRes createCoupon(int userIdx, PostCouponReq postCouponReq) throws BaseException {
        try {
            int couponIdx = couponDao.createCoupon(userIdx, postCouponReq);
            return new PostCouponRes(couponIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
