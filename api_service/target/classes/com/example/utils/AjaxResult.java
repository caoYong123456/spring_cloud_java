package com.example.utils;

import lombok.Data;

import java.util.HashMap;

@Data
public class AjaxResult {

    private Integer code;

    private String message;

    private HashMap data;

}
