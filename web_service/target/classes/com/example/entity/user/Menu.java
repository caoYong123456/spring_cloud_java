package com.example.entity.user;

import lombok.Data;

import java.util.List;

@Data
public class Menu {
    private Integer id;
    private String path;
    private String component;
    private String redirect;
    private String name;
    private Integer parentId;
    private String metaStr;
    private List<Menu> children;
    private Meta meta;


}
