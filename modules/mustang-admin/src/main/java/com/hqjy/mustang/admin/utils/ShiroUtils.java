package com.hqjy.mustang.admin.utils;

import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.base.constant.SystemId;
import com.hqjy.mustang.admin.model.dto.LoginUserDTO;
import org.apache.shiro.SecurityUtils;

import java.util.Optional;

/**
 * @author : heshuangshuang
 * @date : 2018/9/4 17:25
 */
public class ShiroUtils {
    public static LoginUserDTO getUser() {
        return (LoginUserDTO) SecurityUtils.getSubject().getPrincipal();
    }

    public static Boolean isGeneralSeat() {
        return SecurityUtils.getSubject().hasRole(Constant.Role.SALE.getCode());
    }

    public static Boolean isGeneralNetSales() {
        return SecurityUtils.getSubject().hasRole(Constant.Role.NET_SALES_ORDINARY.getCode());
    }

    public static Boolean isSuperAdmin() {
        return SystemId.SUPER_ADMIN.equals(getUserId());
    }

    public static Boolean isInspection() {
        return SecurityUtils.getSubject().hasRole(Constant.Role.INSPECTION.getCode());
    }

    public static Boolean isService() {
        return SecurityUtils.getSubject().hasRole(Constant.Role.SERVICE.getCode());
    }

    public static Long getUserId() {
        return Optional.ofNullable(getUser()).map(LoginUserDTO::getUserId).orElse(SystemId.User.NO_CREATE_ID.getValue());
    }

}
