//package com.example.springboot.service;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.example.springboot.entity.User;
//import com.example.springboot.exception.ServiceException;
//import com.example.springboot.mapper.UserMapper;
//import com.example.springboot.utils.TokenUtils;
//import jakarta.annotation.Resource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Objects;
//
///**
// * 功能：
// * 作者：ddh
// * 日期： 2024/9/19 14:49
// */
//@Service
//public class UserService extends ServiceImpl<UserMapper,User> {  //service层依赖于mapper层
//    @Autowired
////    @Resource
//    UserMapper userMapper;
//public User selectByUsername(String username){//        利用QueryWrapper实现更简便的查询，条件查询器
//    QueryWrapper<User>queryWrapper=new QueryWrapper<>();
//    queryWrapper.eq("username",username);  //eq=> ==where username=#{username}
//    //根据用户名查询数据库中的用户信息
//    return getOne(queryWrapper);
////    return userMapper.selectOne(queryWrapper);//select * from user where username=#{username}
//    //在service里面除了用userMapper，还可以用service本身的方法，mybatis-plus提供了service可以继承的类
//}
//    //验证用户账户是否合法
//    public User login(User user) {
//
//       User dbUser= selectByUsername(user.getUsername());
//        if(dbUser ==null){
//            //抛出自定义异常
//            throw new ServiceException("用户名或密码错误");
//        }
//        if(!Objects.equals(user.getPassword(), dbUser.getPassword())) { //if(!user.getPassword().equals(dbUser.getPassword()))
//            throw new ServiceException("用户名或密码错误");
//        }
//
//        //生成Token
//        String token = TokenUtils.createToken(dbUser.getId().toString(), dbUser.getPassword());//userid存在token中，password验证token  art+enter
//        //token通过user传递出去
//        dbUser.setToken(token);
//        return dbUser;
//    }
//
//    public void register(User user) {
//        User dbUser= selectByUsername(user.getUsername());
//        if(dbUser!=null){
//            throw new ServiceException("用户名已存在");
//        }
//        user.setName(user.getName());
//        userMapper.insert(user);
//    }
//
////    public void resetPassword(User user) {
////        User dbUser= selectByUsername(user.getUsername());
////        if(dbUser!=null){
////            throw new ServiceException("用户不存在");
////        }
////        if(!user.getPhone().equals((dbUser.getPassword()))){
////            throw new ServiceException("验证码错误");
////        }
////        dbUser.setPassword("123");
//////        userMapper.updateUser(user);
////        //更新的方法不用mapper了
////        updateById(dbUser);
////    }
//}
package com.example.onlineeducationplatform.service;

import com.example.onlineeducationplatform.common.Page;
import com.example.onlineeducationplatform.entity.User;
import com.example.onlineeducationplatform.exception.ServiceException;
import com.example.onlineeducationplatform.mapper.UserMapper;
import com.example.onlineeducationplatform.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 功能：
 * 作者：ddh
 * 日期： 2024/9/19 14:49
 */
@Service
public class UserService {
    @Autowired
    UserMapper userMapper;
    public void insertUser(User user){
        userMapper.insert(user);
    }

    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    public void deleteUser(Integer id) {
        userMapper.deleteUser(id);
    }
//批量删除
    public void batchDeleteUser(List<Integer> ids) {
        for(Integer id: ids){
            userMapper.deleteUser(id);
        }
    }

    public List<User> selectAll() {
       return userMapper.selectAll();
    }

    public User selectById(Integer id) {
       return userMapper.selectById(id);
    }
    public List<User> selectByName(String name) {
        return userMapper.selectByName(name);
    }
    public User selectByUserName(String username){return  userMapper.selectByUserName(username);}
    public List<User> selectByMore(String username, String name) {
        return userMapper.selectByMore(username,name);
    }

    public List<User> selectByMo(String username, String name) {
        return userMapper.selectByMo(username,name);
    }

//    public Map<String,Object> selectByPage(Integer pageNum, Integer pageSize, String username, String name) {
//        Integer skipNum =(pageNum-1)*pageSize;  //计算出来 1-- 0,5   2--5,5  3--10,5   4--15,5
//        Map<String, Object> result=new HashMap<>();
//        List<User> userList = userMapper.selectByPage(skipNum, pageSize, username, name);
//        Integer total=userMapper.selectCountByPage(username,name);
//        result.put("list",userList);
//        result.put("total",total);
//        return result;
//    }
    public Page<User> selectByPage(Integer pageNum, Integer pageSize, String username, String name) {
        Integer skipNum =(pageNum-1)*pageSize;  //计算出来 1-- 0,5   2--5,5  3--10,5   4--15,5
        Page<User> page=new Page<>();
        List<User> userList = userMapper.selectByPage(skipNum, pageSize, username, name);
        Integer total=userMapper.selectCountByPage(username,name);
        page.setTotal(total);
        page.setList(userList);
        return page;
    }
//验证用户账户是否合法
    public User login(User user) {
        //根据用户名查询数据库中的用户信息
       User dbUser= userMapper.selectByUserName(user.getUsername());
       if(dbUser ==null){
           //抛出自定义异常
           throw new ServiceException("用户名或密码错误");
       }
       if(!Objects.equals(user.getPassword(), dbUser.getPassword())) { //if(!user.getPassword().equals(dbUser.getPassword()))
           throw new ServiceException("用户名或密码错误");
       }

       //生成Token
        String token = TokenUtils.createToken(dbUser.getId().toString(), dbUser.getPassword());//userid存在token中，password验证token  art+enter
        //token通过user传递出去
        dbUser.setToken(token);
        return dbUser;
    }

    public void register(User user) {
       User dbUser =userMapper.selectByUserName(user.getUsername());
       if(dbUser!=null){
           throw new ServiceException("用户名已存在");
       }
       user.setName(user.getName());
       userMapper.insert(user);
    }
}
