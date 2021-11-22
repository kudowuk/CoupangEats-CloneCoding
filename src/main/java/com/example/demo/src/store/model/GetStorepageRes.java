package com.example.demo.src.store.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetStorepageRes{
    private String storeoName;
    private String storeAddress;
    private String storeHours;
    private int deliveryFee;
    private String deliveryInfo;
    private String deliveryTime;
    private int minAmount;
    private String menuCategory;
    private String menuName;
    private int menuPrice;
    private String menuIntro;
    private String ReviewContent;
    private String reviewImg1;
    private double avgScore;
    private int reviewCunt;

//    private List<String> storeName;
}