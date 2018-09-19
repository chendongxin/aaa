package com.hqjy.mustang.admin.shiro;

import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.base.constant.StatusCode;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.common.redis.utils.RedisKeys;
import com.hqjy.mustang.common.redis.utils.RedisUtils;
import com.hqjy.mustang.admin.model.dto.LoginUserDTO;
import com.hqjy.mustang.common.web.shiro.AuthToken;
import com.hqjy.mustang.common.web.utils.ResponseUtils;
import com.hqjy.mustang.common.web.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/7/29 18:05
 */
@Slf4j
public class AuthFilter extends AccessControlFilter {
    private final static String TOKEN_PARAM_KEY = "token";

    @Autowired
    private RedisUtils redisUtils;

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String accessToken = ((HttpServletRequest) request).getHeader(TOKEN_PARAM_KEY);
        log.error("url:{} ,accessToken:{}", ((HttpServletRequest) request).getRequestURL(), accessToken);
        try {
            Long userId = TokenUtils.tokenInfo(accessToken, Constant.JWT_TOKEN_USERID, Long.class);
            String userName = TokenUtils.tokenInfo(accessToken, Constant.JWT_TOKEN_USERNAME, String.class);
            log.error("userId:{}", userId);
            //读取redis中token信息，但是不改变原来的超时时间
            LoginUserDTO userDTO = redisUtils.get(RedisKeys.User.token(userId), LoginUserDTO.class, -1);
            if (null != userDTO) {
                //验证jwt token的完整性和有效性
                TokenUtils.verify(accessToken, Constant.JWT_SIGN_KEY);
                String jti = TokenUtils.tokenInfo(accessToken, Constant.JWT_ID, String.class);

                if (!jti.equals(userDTO.getJti())) {
                    ResponseUtils.response(httpResponse, HttpStatus.OK.value(), R.error(StatusCode.TOKEN_OUT));
                    return false;
                }

                //委托给Realm进行登录
                try {
                    getSubject(request, response).login(new AuthToken(userId, userName, accessToken));
                } catch (Exception e) {
                    // userToken验证失败
                    log.debug("[ userId:" + userId + "] userToken验证失败：" + accessToken);
                    ResponseUtils.response(httpResponse, HttpStatus.OK.value(), R.error(StatusCode.TOKEN_OUT));
                    return false;
                }
                //token续命
                redisUtils.set(RedisKeys.User.token(userId), userDTO, TokenUtils.getProlong());
                return true;
            }
            // token不在缓存中
            ResponseUtils.response(httpResponse, HttpStatus.OK.value(), R.error(StatusCode.TOKEN_OVERDUE));
        } catch (Exception e) {
            ResponseUtils.response(httpResponse, HttpStatus.FORBIDDEN.value(), R.error(StatusCode.TOKEN_FAULT));
        }
        return false;
    }

}

