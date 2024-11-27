package com.example.onlineeducationplatform.controller;

import cn.hutool.core.util.StrUtil;
import com.example.onlineeducationplatform.common.AuthAccess;
import com.example.onlineeducationplatform.common.Result;
import com.example.onlineeducationplatform.entity.User;
import com.example.onlineeducationplatform.exception.ServiceException;
import com.example.onlineeducationplatform.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * 功能：提供接口返回数据
 * 作者：ddh
 * 日期： 2024/9/19 9:05
 */
@RestController

public class WebController {

//    @Autowired   @Resource都可以注入

    @Resource
    UserService userService;
//    @RequestMapping (method = RequestMethod.POST)
@AuthAccess
    @GetMapping("/")    //合成之后就是/web/hello
    public Result hello(){
        return Result.success("success");
    }
//    @ResponseBody  //这里会报错
//    @PostMapping("/login")
//    public Result login(@RequestBody User user){
//
//        String username = user.getUsername();
//        String password = user.getPassword();
//        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
//            return Result.error("数据输入不合法");
//        }
//        user= userService.login(user);
//
//        return Result.success(user);
//    }
@AuthAccess
    @ResponseBody
    @PostMapping("/logininfo")
    public Result login(@RequestBody User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return Result.error("数据输入不合法");
        }
        try {
            user = userService.login(user);
            return Result.success(user);
        } catch (ServiceException e) {
            return Result.error("401", e.getMessage()); // 自定义错误处理
        }
    }
    @AuthAccess
    @ResponseBody
    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        String role=user.getRole();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)|| StrUtil.isBlank(role)) {
            return Result.error("数据输入不合法");
        }
        if (user.getUsername().length()>10 || user.getPassword().length()>10) {
            return Result.error("数据输入不合法");
        }
        try {
            userService.register(user);
            return Result.success();
        } catch (ServiceException e) {
            return Result.error("401", e.getMessage()); // 自定义错误处理
        }
    }


}




































//package com.example.springboot.controller;
//
//import com.example.springboot.common.Result;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
///**
// * 功能：提供接口返回数据
// * 作者：ddh
// * 日期： 2024/9/19 9:05
// */
//@RestController           //表示这是一个返回json,返回数据的controller
//
////如果要定义二级路由，
////@RequestMapping("/web")
//
//public class WebController {
////    @RequestMapping (method = RequestMethod.POST)     //表示这个方法是提供数据的 它是一种默认为get请求的请求方式
//    @RequestMapping("/")    //合成之后就是/web/hello
//    public Result hello(){
//        return Result.success("success");
//    }
////前后端交互约定的格式是Json，Json有两种，json对象和json数组，后端提供一种很通用的json对象（json数组包含json对象比较麻烦）
//
////    @PostMapping("/post")  //提交数据 定义一个对象来接收数据  /web/post
////    public Result post(Obj obj){   //http://localhost:9090/web/post?name=1111 换成/报错    http://localhost:9090/web/post?name=111&age=3  URL参数
////        return  Result.success(obj);
////    }
//
//        @PostMapping("/post")  //提交数据 定义一个对象来接收数据  /web/post
//    public Result post(@RequestBody Obj obj){   //提交json 将前端提交的json变成一个java对象
//        System.out.println(obj.getName() == null? "是null" : obj.getName().isEmpty());
//        return  Result.success(obj);
//    }
//
//    @PutMapping("/put")   //更新更改数据
//    public Result put(@RequestBody Obj obj){   //提交json 将前端提交的json变成一个java对象
//        return  Result.success(obj);
//    }
//
////    @DeleteMapping("/delete/{id}")  //用id删除，路径参数 http://localhost:9090/web/delete/1     /1  =>/{id}  路径参数
////    public Result delete(@PathVariable Integer id){   //提交json 将前端提交的json变成一个java对象
////        return  Result.success(id);
////    }
//    //delete也可以传json,我们批量删除可以用json
//
//    @DeleteMapping("/delete")
//    public Result delete(@RequestBody List<Integer> ids){
//        return  Result.success(ids);
//    }
//
////    @DeleteMapping("/delete")
////    public Result delete(@RequestBody Obj obj){
////        return  Result.success(obj);
////    }
//
//
//}
