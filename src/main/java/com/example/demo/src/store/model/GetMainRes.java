package com.example.demo.src.store.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetMainRes {
    private double avgReview;
    private int cntReview;
    private String storeName;
    private String storeProfile;
    private String deliveryTime;
    private int deliveryFee;
    private String categoryName;
    private String categoryImg;
}
