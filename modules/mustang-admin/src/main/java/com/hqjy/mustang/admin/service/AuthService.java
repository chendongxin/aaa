package com.hqjy.mustang.admin.service;

import com.hqjy.mustang.common.web.model.AuthCheckResult;

import java.util.Set;

/**
 * @author : heshuangshuang
 * @date : 2018/10/10 11:27
 */
public interface AuthService {
    AuthCheckResult checkToken(Long userId, String jti);

    Set<String> permSet(Long userId);

    Set<String> roleSet(Long userId);
}
