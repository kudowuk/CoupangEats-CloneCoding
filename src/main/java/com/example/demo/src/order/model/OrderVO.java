package com.example.demo.src.order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderVO {

    private int orderIdx;
    private String storeName;
    private String createdAt;
    private String storeProfile;

}
