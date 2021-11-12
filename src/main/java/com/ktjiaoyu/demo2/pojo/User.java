package com.ktjiaoyu.demo2.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class User implements Serializable {
    private String name;
    private String password;
    private int sex;
    private String address;
}
