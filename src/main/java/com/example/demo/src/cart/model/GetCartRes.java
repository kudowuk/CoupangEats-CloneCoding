package com.example.demo.src.cart.model;

import com.example.demo.src.order.model.Menu;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetCartRes {
    // 모든 것 다쨔기

   private int cartIdx;
   private String roadName;
   private String sections;
   private String storeName;
   private int minAmount;
   private int couponIdx;
   private String toOwner;
   private String toDriver;
   private String disposableAt;

   private List<Menu>menuList;
}
