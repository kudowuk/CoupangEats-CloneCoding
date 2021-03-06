package com.example.demo.src.address.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
@AllArgsConstructor
public class GetAddressRes {

    private int addressIdx;
    private String name;
    private String roadName;
    private String latitude;
    private String longitude;
    private String sections;

}

