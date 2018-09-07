package com.hqjy.mustang.common.web.model;

import lombok.Data;

import java.util.Set;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date :  2018/9/2 18:07
 */
@Data
public class UserDetails {
    private String userName;
    private Long userId;
    private String jwt;
    private Set<String> roleSet;
    private Set<String> permSet;

    public UserDetails() {

    }

    public UserDetails(String userName, Long userId, String jwt, Set<String> roleSet, Set<String> permSet) {
        this.userName = userName;
        this.userId = userId;
        this.jwt = jwt;
        this.roleSet = roleSet;
        this.permSet = permSet;
    }
}
