//package com.example.springboot.mapper;

//操作用户数据库的接口
//
//import com.baomidou.mybatisplus.core.mapper.BaseMapper;
//import com.example.springboot.entity.User;
//
//
//import java.util.List;
//
//public interface UserMapper extends BaseMapper<User> {
//
//}
////下一步改造service








package com.example.onlineeducationplatform.mapper;

import com.example.onlineeducationplatform.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

//操作用户数据库的接口
@Mapper
public interface UserMapper {
    //实现新增
    @Insert("insert into `user` (username,password,name,phone,email,address,avatar,role) " +
            "values(#{username},#{password},#{name},#{phone},#{email},#{address},#{avatar},#{role})")
    void insert(User user);

    @Update("update `user` set username=#{username},password=#{password},name=#{name}," +
            "phone=#{phone},email=#{email},address=#{address},avatar=#{avatar} where id=#{id}")
    void updateUser(User user);

    @Delete("delete from `user` where id=#{id}")
    void deleteUser(Integer id);
    //    @Delete("delete from `user` where id in ()")
//    void batchDeleteUser(List<Integer> ids);

    @Select("select *from `user` order by id desc")
        //排序desc(倒序) asc(正序)
    List<User> selectAll();

    @Select("select *from `user` where id=#{id} order by id desc")
    User selectById(Integer id);

    @Select("select *from `user` where name=#{name} order by id desc")
    List<User> selectByName(String name);

    @Select("select *from `user` where name=#{name} and username=#{username} order by id desc")
//    List<User> selectByMore(String username, String name);//有可能参数不能解析出来，正规写法是在参数前面加一个注解@parm，告诉mybatis框架参数的意思
    List<User> selectByMore(@Param("username") String username, @Param("name") String name);//有可能参数不能解析出来，正规写法是在参数前面加一个注解@parm，告诉mybatis框架参数的意思

    @Select("select *from `user` where username like concat('%',#{username},'%') and name like concat('%',#{name},'%')  order by id desc")
        //多条件全模糊查询
    List<User> selectByMo(String username, String name);

    //注：limit不用带括号 分页查询写法
    @Select("select * from `user` where username like concat('%',#{username},'%') and name like concat('%',#{name},'%') order by id desc limit #{skipNum},#{pageSize}")
    List<User> selectByPage(@Param("skipNum") Integer skipNum, @Param("pageSize")Integer pageSize, @Param("username")String username, @Param("name")String name);
    //skipNum跳跃的参数
    @Select("select count(id) from `user` where username like concat('%',#{username},'%') and name like concat('%',#{name},'%')")
    Integer selectCountByPage(@Param("username")String username, @Param("name")String name);

    @Select("select *from `user` where username=#{username}")
    User selectByUserName(String username);
}
