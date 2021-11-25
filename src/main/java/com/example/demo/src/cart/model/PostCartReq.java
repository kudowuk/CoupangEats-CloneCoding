package com.example.demo.src.cart.model;

import com.example.demo.src.order.model.Menu;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class PostCartReq {

   private int storeIdx;
   private int couponIdx;
   private String toOwner;
   private String toDriver;
   private String disposableAt;
   private List<PostCartDetailReq> cartMenuList = new ArrayList<>();
}
