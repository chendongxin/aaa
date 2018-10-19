package com.hqjy.mustang.common.web.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : heshuangshuang
 * @date : 2018/1/18 21:08
 */
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

    /**
     * 注入fastJson
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        //fast参数配置
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        // 自定义时间格式 TODO 微服务调用反序列化问题,记得加上 @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")，因为历史原因问题，其实可以不对时间进行统一格式化
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        //禁用循环引用检测
        fastJsonConfig.setSerializerFeatures(SerializerFeature.DisableCircularReferenceDetect);
        fastConverter.setFastJsonConfig(fastJsonConfig);
        //返回字符串格式
        List<MediaType> fastMediaTypes = new ArrayList<MediaType>() {{
            add(MediaType.APPLICATION_JSON_UTF8);
        }};
        fastConverter.setSupportedMediaTypes(fastMediaTypes);
        converters.add(fastConverter);
        super.configureMessageConverters(converters);
    }

}
