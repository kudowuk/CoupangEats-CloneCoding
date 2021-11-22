package com.example.demo.src.coupon.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class GetCouponRes {

    private String storeName;
    private String couponName;
    private int minAmount;
    private int discountRate;
    private Date expiryDate;

}
