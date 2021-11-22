package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private int userIdx;
    private String userId;
    private String userPwd;
    private String userName;
    private String userPhone;
}
