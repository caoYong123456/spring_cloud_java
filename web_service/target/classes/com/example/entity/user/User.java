package com.example.entity.user;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Integer id;

    private String userName;

    private String password;

    private Date createTime;

}
