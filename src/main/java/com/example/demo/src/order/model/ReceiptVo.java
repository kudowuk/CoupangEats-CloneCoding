package com.example.demo.src.order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReceiptVo {

    private int orderIdx;
    private String storeName;
    private String createdAt;
    private int deliveryFee;
    private int discountRate;
    private String paidType;
    private int paidAmount;

}
