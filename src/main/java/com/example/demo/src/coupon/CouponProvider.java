package com.example.demo.src.coupon;

import com.example.demo.config.BaseException;
import com.example.demo.src.coupon.CouponDao;
import com.example.demo.src.coupon.model.GetCouponRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class CouponProvider {

    private final CouponDao couponDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public CouponProvider(CouponDao couponDao) {
        this.couponDao = couponDao;
    }

    public List<GetCouponRes> getCoupons(int userIdx, int storeIdx) throws BaseException {
        try{
            List<GetCouponRes> getCouponRes = couponDao.getCoupons(userIdx, storeIdx);
            return getCouponRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
