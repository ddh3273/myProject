package com.example.onlineeducationplatform.controller;
//ctrl+alt+o可以把无用的包导完
//ctrl+art+l可以格式化

//import com.example.springboot.common.Page;
import com.example.onlineeducationplatform.common.Page;
import com.example.onlineeducationplatform.common.Result;
import com.example.onlineeducationplatform.entity.User;
import com.example.onlineeducationplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
/**
 * 功能：用户相关
 * controller是后端与前端交互的一个窗口
 * 作者：ddh
 * 日期： 2024/10/19 14:49
 */

@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired  //从容器中拿
    UserService userService;  //首先爆红是因为userService没有注入到容器中
    //   新增用户信息
    @PostMapping("/add")  //表中设唯一值之后数据重复插入出现了500
    public Result add(@RequestBody User user){
        try {
            //插入
            userService.insertUser(user);
//            userService.save(user);
        }catch (Exception e){
            if(e instanceof DuplicateKeyException) {
                return Result.error("插入数据库错误");
            }else{
                return Result.error("系统错误");
            }
        }
        return  Result.success();
    }
    //   修改用户信息
    @PutMapping("/update")
    public Result update(@RequestBody User user){
        userService.updateUser(user);
//        userService.updateById(user);
        return  Result.success();
    }
    //   删除单个用户信息
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        System.out.println(id);
        try {
            userService.deleteUser(id);
            return Result.success(); // 删除成功返回成功响应
        } catch (Exception e) {
            return Result.error("Error deleting user: " + e.getMessage()); // 返回错误响应
        }
    }

    //批量删除用户信息
    @DeleteMapping("/delete/batch")
    public Result batchDelete(@RequestBody List<Integer> ids){
        try{
            userService.batchDeleteUser(ids);
        }catch (Exception e){}

//        userService.removeBatchByIds(ids);
        return Result.success();
    }

    //查询全部的用户信息
    @GetMapping("/selectAll")
    public Result selectAll(){
        List<User> userList=null;
        try {
            userList = userService.selectAll();//userService.selectAll();请求成功但没返回数据，因为没有设置数据
//        List<User> userList = userService.list(new QueryWrapper<User>().orderByDesc("id"));//select *from user order by id desc
            if (userList.isEmpty()) {
                return Result.error("没有查询到任何用户数据");
            }
        }catch (Exception e){}

        return Result.success(userList);
    }

//    @GetMapping("/selectAll")
//    public Result selectAll() {
//        // 使用 QueryWrapper 查询用户列表，并按 id 降序排序
//        List<User> userList = userService.list(new QueryWrapper<User>().orderByDesc("id"));
//        if (userList.isEmpty()) {
//            return Result.error("没有查询到任何用户数据");
//        }
//        return Result.success(userList);
//    }
    //条件查询 （查询某个具体的用户信息）
    @GetMapping("/selectById/{id}")  //路径查询
    public Result selectById(@PathVariable Integer id){
        System.out.println("Delete user with ID: " + id);  // 打印日志查看请求是否到达
        User user= userService.selectById(id);
//        User user= userService.getById(id);

        return Result.success(user);
    }
//  前端没用到  //注意：根据条件查询，如果不确定查询结果有几个，就返回一个List对象
    @GetMapping("/selectByName/{name}")  //路径查询  //500错误，因为在数据库返回的时候返回来6条数据，但你只要一个数据
    public Result selectByName(@PathVariable String name){
//       List<User>user= userService.selectByName(name);
        List<User>user= userService.selectByName(name);
       return Result.success(user);
    }
//    //多个查询要加@RequestParam
//    @GetMapping("/selectByMore")  //路径参数改为url参数
//    //@RequestParam多参数查询，postman请求时应该在key-value中写http://localhost:9090/user/selectByMore?username=ddm1&name=dag
//    public Result selectByMore(@RequestParam String username, @RequestParam String name){
//        List<User>user= userService.selectByMore(username,name);
//        return Result.success(user);
//    }
//    //多条件的模糊查询
//    @GetMapping("/selectByMo")
//    public Result selectByMo(@RequestParam String username, @RequestParam String name){
//        List<User>user= userService.selectByMo(username,name);
//        return Result.success(user);
//    }
    //多条件查询（分页查询）当前是哪一页pageNum当前页码，当前页总共有多少条pageSize
    @GetMapping("/selectByPage")
    //分页查询还要接受新参数
    public Result selectByPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam String username,
                               @RequestParam String name ){
//        Map<String, Object> pageMap = userService.selectByPage(pageNum, pageSize, username, name);
        //优雅写法
        Page<User> pageMap = userService.selectByPage(pageNum, pageSize, username, name);
//        QueryWrapper<User> queryWrapper = new QueryWrapper<User>().orderByDesc("id"); //条件构造器，传入了两个参数
//        queryWrapper.like(StrUtil.isNotBlank(username),"username",username);
//        queryWrapper.like(StrUtil.isNotBlank(name),"name",name);
        //select * from user where username like '%#{username}%' and name like '%#{name}%'
//       ② Page<User> pageMap = userService.page(pageNum, pageSize, username, name);
//        Page<User> page= userService.page(new Page<>(pageNum,pageSize),queryWrapper);
        //删掉common包下的Page类 ，controller类中的obj也删掉
        return Result.success(pageMap);
    }


}



