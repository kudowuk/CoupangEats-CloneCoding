package com.example.demo.src.cart.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CartVo {

    private int cartIdx;
    private String roadName;
    private String sections;
    private String storeName;
    private int minAmount;
    private int couponIdx;
    private String toOwner;
    private String toDriver;
    private String disposableAt;
}
//cartIdx
//roadName
//section
//storeName
//minAmount
//couponIdx
//toOwner
//toDriver
//disposableAt
