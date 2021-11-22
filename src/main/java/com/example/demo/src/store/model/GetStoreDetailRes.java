package com.example.demo.src.store.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetStoreDetailRes {
    private String storeName;
    private String storePhone;
    private String storeAddress;
    private String storeInfo;
    private String storeHours;
    private String storeIntro;
    private String storeNotice;
    private String originInfo;
}
