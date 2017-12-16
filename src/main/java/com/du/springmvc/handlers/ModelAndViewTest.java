package com.du.springmvc.handlers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
@RequestMapping(value = "modelandview")
public class ModelAndViewTest {

    public static final String SUCCESS = "success";

    /**
     * 目标方法的返回值可以是 ModelAndView 类型
     * 其中可以包含视图和模型信息。
     * Spring MVC 会把ModelAndView的model中的数据放到request域对象中。
     * @return
     */
    @RequestMapping(value = "test")
    public ModelAndView testModelAndView(){
        String viewName = SUCCESS;
        ModelAndView modelAndView = new ModelAndView(viewName);

        //添加模型数据到ModelAndView 中
        modelAndView.addObject("time", new Date());

        return modelAndView;
    }
}
