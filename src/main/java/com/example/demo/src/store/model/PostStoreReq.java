package com.example.demo.src.store.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class PostStoreReq {
    private int storeIdx;
    private int categoryIdx;
    private int brandIdx;
    private String storeProfile;
    private String storeName;
    private String storePhone;
    private String storeAddress;
    private String storeInfo;
    private String storeHours;
    private String storeIntro;
    private String storeNotice;
    private String originInfo;
    private String deliveryTime;
    private String deliveryFee;
    private String deliveryInfo;
    private String minAmount;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String tag;
    private String status;
}
