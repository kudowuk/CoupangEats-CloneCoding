package com.example.demo.src.like.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetLikeRes {

    private int likeIdx;
    private int userIdx;
    private int storeIdx;
}
