package com.du.springmvc.handlers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("springmvc")
public class SpringMVCTest {

    public static final String SUCCESS = "success";


    /**
     * REST 风格的 URL
     * 以CRUD为例：
     * 新增：/order POST
     * 修改：/order/1 PUT
     * 获取：/order/1 GET
     * 删除：/order/1 DELETE
     * 如何发送 PUT 和 DELETE 请求？
     * 1、需要配置HiddenHttpMethodFilter
     * 2、需要发送POST请求
     * 3、需要在发送POST请求时携带一个 name="_method" 的隐藏域,值为DELETE或PUT
     * 例如
     * <form action="springmvc/testRest/1" method="post">
     *  <input type="hidden" name="_method" value="PUT">
     *  <input type="submit" value="Test Rest PUT">
     * </form>
     * 使用PathVariable注解获取该值。
     * @param id
     * @return
     */
    @RequestMapping(value = "testRest/{id}", method = RequestMethod.PUT)
    public String testRestPUT(@PathVariable(value = "id") Integer id){
        System.out.println("testRestPUT:"+id);
        return SUCCESS;
    }

    @RequestMapping(value = "testRest/{id}", method = RequestMethod.DELETE)
    public String testRestDELETE(@PathVariable(value = "id") Integer id){
        System.out.println("testRestDELETE:"+id);
        return SUCCESS;
    }

    @RequestMapping(value = "testRest", method = RequestMethod.POST)
    public String testRestPOST(){
        System.out.println("testRestPOST");
        return SUCCESS;
    }

    @RequestMapping(value = "testRest/{id}", method = RequestMethod.GET)
    public String testRestGET(@PathVariable(value = "id") Integer id){
        System.out.println("testRestGET:"+id);
        return SUCCESS;
    }


    /**
     * @PathVariable 注解可以映射URL中的占位符到目标方法的参数中
     * @param id
     * @return
     */
    @RequestMapping(value = "testPathVariable/{id}")
    public String testPathVariable(@PathVariable(value ="id") Integer id){
        System.out.println("testPathVariable:"+id);
        return SUCCESS;
    }

    /**
     * ANT路径
     * @return
     */
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
