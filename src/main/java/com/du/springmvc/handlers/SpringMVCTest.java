package com.du.springmvc.handlers;

import com.du.springmvc.entities.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Map;

@SessionAttributes(value = {"user"}, types = {String.class})
@Controller
@RequestMapping("springmvc")
public class SpringMVCTest {

    public static final String SUCCESS = "success";

    /**
     * SessionAttributes注解，除了可以通过属性名指定需要放到会话里的属性外，(根据value进行设置)
     * 还可以通过模型属性的对象类型来指定哪些模型属性需要放到会话里。(根据types执行设置)
     * 该注解只能放在类上面，不能放在方法的上面。
     * @param map
     * @return
     */
    @RequestMapping(value = "testSessionAttributes")
    public String testSessionAttributes(Map<String, Object> map){
        User user = new User("Tom", "123456", "tom@126.com", 12);
        map.put("user", user);
        map.put("school", "二中");
        return SUCCESS;
    }

    /**
     * 目标方法可以添加Map类型的参数,也可以是Model类型或ModelMap类型
     * @param map
     * @return
     */
    @RequestMapping("testMap")
    public String testMap(Map<String, Object> map){
        System.out.println(map.getClass().getName());
        map.put("names", Arrays.asList("tom", "jerry", "mike"));
        return SUCCESS;
    }


    /**
     * 可以使用Servlet原生API作为目标方法的参数
     * 具体支持一下类型
     * HttpServletRequest
     * HttpServletResponse
     * HttpSession
     * java.security.Principal
     * Locale
     * InputStream
     * OutputStream
     * Reader
     * Writer
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "testServletAPI")
    public void testServletAPI(HttpServletRequest request, HttpServletResponse response, Writer out) throws IOException {
        System.out.println("testServletAPI, "+ request+ ", "+ response);
        out.write("hello springmvc");
//        return SUCCESS;
    }

    /**
     * Spring MVC 会按请求参数名和POJO属性名进行自动匹配，
     * 自动为该对象填充属性，只会级联属性，即address.city形式，为user中address的city填充。
     * @param user
     * @return
     */
    @RequestMapping(value = "testPojo")
    public String testPojo(User user){
        System.out.println("testPojo:"+user);
        return SUCCESS;
    }

    /**
     * 了解
     * 注解 @CookieValue 映射一个Cookie值，属性同@RequestParam
     * @param sessionid
     * @return
     */
    @RequestMapping(value = "testCookieValue")
    public String testCookieValue(@CookieValue(value = "JSESSIONID") String sessionid){
        System.out.println("testCookieValue JSESSIONID:"+sessionid);
        return SUCCESS;
    }

    /**
     * 了解
     * 注解@RequestHeader 用法与@RequestParam相同
     * 作用：映射请求头信息
     * @param al
     * @return
     */
    @RequestMapping(value = "testRequestHeader")
    public String testRequestHeader(@RequestHeader(value = "Accept-Language") String al){
        System.out.println("testRequestHeader Accept-Language:"+al);
        return SUCCESS;
    }

    /**
     * 注解 @RequestParam 来映射请求参数。
     * value 为请求参数的参数名
     * required 为该参数是否必须，默认为true
     * defaultValue 请求参数的默认值
     * @param username
     * @param age
     * @return
     */
    @RequestMapping(value = "testRequestParam")
    public String testRequestParam(@RequestParam(value = "username") String username,
                                   @RequestParam(value = "age", required = false, defaultValue = "0") int age) {
        System.out.println("testRequestParam, username:"+username+" age:"+age);
        return SUCCESS;
    }


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
