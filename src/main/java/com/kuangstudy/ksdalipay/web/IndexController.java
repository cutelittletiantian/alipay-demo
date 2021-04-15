package com.kuangstudy.ksdalipay.web;

import com.kuangstudy.ksdalipay.config.AlipayConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @description:
 * @author: xuke
 * @time: 2021/4/2 0:35
 */
@Controller
public class IndexController {

    @Autowired
    private AlipayConfig alipayConfig;

    @GetMapping("/index")
    public String index(){
        return "index";
    }

    /**
     * @Author xuke
     * @Description 产品查询页面
     * @Date 10:51 2021/4/2
     * @Param []
     * @return java.lang.String
    **/ 
    @GetMapping("/main")
    public String main(){
        return "main";
    }
}
