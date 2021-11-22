package com.example.demo.src.address.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PatchAddressReq {

    private int addressIdx;
    private int userIdx;
    private String name;
    private String roadName;
    private String latitude;
    private String longitude;
    private String sections;
}
