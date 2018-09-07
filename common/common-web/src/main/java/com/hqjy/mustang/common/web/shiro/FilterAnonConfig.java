package com.hqjy.mustang.common.web.shiro;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date :  2018/9/2 18:07
 */
@Data
@Configuration
@ConditionalOnExpression("!'${shiro.anon}'.isEmpty()")
@ConfigurationProperties(prefix = "shiro.anon")
public class FilterAnonConfig {
    private List<String> path = new ArrayList<>();
}
