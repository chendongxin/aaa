package com.hqjy.mustang.admin.api;


import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.base.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


/**
 * @author xyq
 * @date create on 2018/9/17
 * @apiNote TEST
 */
@RestController
@RequestMapping(Constant.API_PATH_ANON)
@Slf4j
public class TestApi {

    @GetMapping(value = "/test")
    public R test() {
        return R.ok("正常访问");
    }
}
