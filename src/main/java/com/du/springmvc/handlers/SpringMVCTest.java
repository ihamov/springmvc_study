package com.du.springmvc.handlers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("springmvc")
public class SpringMVCTest {

    public static final String SUCCESS = "success";

    @RequestMapping(value = "testAntAndPath/*/abc")
    public String testAntAndPath(){
        System.out.println("testAntAndPath");
        return SUCCESS;
    }

    /**
     * 了解：可以使用params和headers来更加精确的映射请求，params和headers支持简单的表大式
     * @return
     */
    @RequestMapping(value = "testParamsAndHeaders",
            params = {"username", "age!=10"}, headers = {"Accept-Language=zh-CN"})
    public String testParamsAndHeaders(){
        System.out.println("testParamsAndHeaders");
        return SUCCESS;
    }

    /**
     * 使用method属性来指定请求方式
     * @return
     */
    @RequestMapping(value = "testMethod", method = RequestMethod.POST)
    public String testMethod(){
        System.out.println("testMethod");
        return SUCCESS;
    }

    /**
     * 1、@RequestMapping 除了修饰方法还可以修饰类
     * 2、两种情况：
     * 1). 类定义处: 提供初步的请求映射信息。相对于 WEB 应用的根目录
     * 2). 方法处: 提供进一步的细分映射信息。 相对于类定义处的 URL。
     * 若类定义处未标注 @RequestMapping，则方法处标记的 URL相对于 WEB 应用的根目录
     * @return
     */
    @RequestMapping("/testRequestMapping")
    public String testRequestMapping(){
        System.out.println("testRequestMapping");
        return SUCCESS;
    }
}
