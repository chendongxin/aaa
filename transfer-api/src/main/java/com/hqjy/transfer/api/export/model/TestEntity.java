package com.hqjy.transfer.api.export.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : heshuangshuang
 * @date : 2018/9/4 14:15
 */
@Data
public class TestEntity implements Serializable {
    private String name;
    private int age;
    private String sex;
}
