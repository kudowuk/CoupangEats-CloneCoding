package com.example.demo.src.order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetReceiptRes {

    private int orderIdx;
    private String storeName;
    private String createdAt;
    private int deliveryFee;
    private int discountRate;
    private String paidType;
    private int paidAmount;

    private List<Menu> menuList;

}
