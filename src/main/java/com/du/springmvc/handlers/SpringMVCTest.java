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


    @RequestMapping(value = "testRedirect")
    public String testRedirect(){
        System.out.println("testRedirect");
        return "redirect:/index.jsp";
    }

    @RequestMapping(value = "testView")
    public String testView(){
        System.out.println("testView");
        return "helloView";
    }


    @RequestMapping(value = "testViewAndViewResolver")
    public String testViewAndViewResolver(){
        System.out.println("testViewAndViewResolver");
        return SUCCESS;
    }

    /**
     * 1. 由ModelAttribute注解标记的方法，会在每个目标方法执行之前被Spring MVC 调用
     * 2. @ModelAttribute 注解也可以来修饰目标方法 POJO 类型的入参, 其 value 属性值有如下的作用:
     * 1). SpringMVC 会使用 value 属性值在 implicitModel 中查找对应的对象, 若存在则会直接传入到目标方法的入参中.
     * 2). SpringMVC 会一 value 为 key, POJO 类型的对象为 value, 存入到 request 中.
     * @param id
     * @param map
     */
    @ModelAttribute
    public void getUser(@RequestParam(value = "id", required = false) Integer id, Map<String, Object> map){
        if (id != null){
            //模拟从数据库中获取User对象
            User user = new User(1, "TOM", "123456", "tom.@126.com", 12);
            map.put("user", user);
            System.out.println("从数据库中获取对象："+user);
        }
    }

    /**
     * 运行流程：
     * 1.执行@ModelAttribute注解修饰的方法：从数据库中取出对象，把对象放入到Map中，键为：user
     * 2.Spring MVC 从Map中取出User，并把表单的请求参数赋值给该user对象的对应属性。
     * 3.Spring MVC 把上述对象传入目标方法参数
     *
     * 注意：在@ModelAttribute注解修饰的方法中，放入Map时的键需要和目标方法入参类型的第一个字母小写的字符串一致。
     *
     * SpringMVC 确定目标方法 POJO 类型入参的过程
     * 1. 确定一个 key:
     *   1). 若目标方法的 POJO 类型的参数木有使用 @ModelAttribute 作为修饰, 则 key 为 POJO 类名第一个字母的小写
     *   2). 若使用了  @ModelAttribute 来修饰, 则 key 为 @ModelAttribute 注解的 value 属性值.
     * 2. 在 implicitModel 中查找 key 对应的对象, 若存在, 则作为入参传入
     *  1). 若在 @ModelAttribute 标记的方法中在 Map 中保存过, 且 key 和 1 确定的 key 一致, 则会获取到.
     * 3. 若 implicitModel 中不存在 key 对应的对象, 则检查当前的 Handler 是否使用 @SessionAttributes 注解修饰,
     *  若使用了该注解, 且 @SessionAttributes 注解的 value 属性值中包含了 key, 则会从 HttpSession 中来获取 key 所
     *  对应的 value 值, 若存在则直接传入到目标方法的入参中. 若不存在则将抛出异常.
     * 4. 若 Handler 没有标识 @SessionAttributes 注解或 @SessionAttributes 注解的 value 值中不包含 key, 则
     *  会通过反射来创建 POJO 类型的参数, 传入为目标方法的参数
     * 5. SpringMVC 会把 key 和 POJO 类型的对象保存到 implicitModel 中, 进而会保存到 request 中.
     *
     * 源代码分析的流程
     * 1. 调用 @ModelAttribute 注解修饰的方法. 实际上把 @ModelAttribute 方法中 Map 中的数据放在了 implicitModel 中.
     * 2. 解析请求处理器的目标参数, 实际上该目标参数来自于 WebDataBinder 对象的 target 属性
     *  1). 创建 WebDataBinder 对象:
     * ①. 确定 objectName 属性: 若传入的 attrName 属性值为 "", 则 objectName 为类名第一个字母小写.
     * 注意: attrName. 若目标方法的 POJO 属性使用了 @ModelAttribute 来修饰, 则 attrName 值即为 @ModelAttribute
     * 的 value 属性值
     *
     * ②. 确定 target 属性:
     * 	> 在 implicitModel 中查找 attrName 对应的属性值. 若存在, ok
     * 	> *若不存在: 则验证当前 Handler 是否使用了 @SessionAttributes 进行修饰, 若使用了, 则尝试从 Session 中
     * 获取 attrName 所对应的属性值. 若 session 中没有对应的属性值, 则抛出了异常.
     * 	> 若 Handler 没有使用 @SessionAttributes 进行修饰, 或 @SessionAttributes 中没有使用 value 值指定的 key
     * 和 attrName 相匹配, 则通过反射创建了 POJO 对象
     *
     * 2). SpringMVC 把表单的请求参数赋给了 WebDataBinder 的 target 对应的属性.
     * 3). *SpringMVC 会把 WebDataBinder 的 attrName 和 target 给到 implicitModel.
     * 近而传到 request 域对象中.
     * 4). 把 WebDataBinder 的 target 作为参数传递给目标方法的入参.
     * @param user
     * @return
     */
    @RequestMapping(value = "testModelAttribute")
    public String testModelAttribute(User user){

        System.out.println("修改："+user);
        return SUCCESS;
    }

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
