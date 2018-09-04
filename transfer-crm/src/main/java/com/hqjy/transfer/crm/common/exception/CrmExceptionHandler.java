package com.hqjy.transfer.crm.common.exception;

import com.hqjy.transfer.common.base.constant.StatusCode;
import com.hqjy.transfer.common.base.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

/**
 * 异常处理器
 *
 * @author : heshuangshuang
 * @date : 2018/3/23 13:25
 */
@RestControllerAdvice
@Slf4j
public class CrmExceptionHandler {

    /**
     * 401 UNAUTHORIZED，无权限
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({UnauthorizedException.class})
    public R shiroException(UnauthorizedException ex, HttpServletResponse response) {
        return R.error(StatusCode.USER_UNAUTHORIZED);
    }
}
