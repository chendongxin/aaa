package com.hqjy.mustang.common.web.model;

import com.hqjy.mustang.common.base.constant.Constant;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date :  2018/9/4 23:30
 */
@Data
public class AuthCheckResult implements Serializable {
    private static final long serialVersionUID = 1L;
    private Constant.CheckToken check;
    private Set<String> roleSet;
    private Set<String> permSet;
}
