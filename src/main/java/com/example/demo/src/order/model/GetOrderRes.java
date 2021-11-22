package com.example.demo.src.order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetOrderRes {

    private int orderIdx;
    private String storeName;
    private String createdAt;
    private String storeProfile;

    private List<Menu> menuList;

}
