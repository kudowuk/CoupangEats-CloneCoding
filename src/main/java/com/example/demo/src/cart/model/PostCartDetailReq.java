package com.example.demo.src.cart.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostCartDetailReq {

   private int menuIdx;
   private int quantity;
   private int optionTypeIdx;
   private int optionIdx;
}
