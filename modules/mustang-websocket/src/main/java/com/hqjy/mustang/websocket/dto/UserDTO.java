package com.hqjy.mustang.websocket.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userId;
    private String password;
    private String token;
    private String jti;
}
