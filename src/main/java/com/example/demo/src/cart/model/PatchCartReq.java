package com.example.demo.src.cart.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PatchCartReq {

    private int cartIdx;
    private int userIdx;
    private int storeIdx;
    private int couponIdx;
    private String toOwner;
    private String toDriver;
    private String disposableAt;
}