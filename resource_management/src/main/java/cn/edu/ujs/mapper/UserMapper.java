package cn.edu.ujs.mapper;

import cn.edu.ujs.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by DELL on 2018/2/12.
 */
@Component
public interface UserMapper {

    //添加用户
    @Insert("insert into user(username,password,email,phone) values(#{username},#{password},#{email},#{phone})")
    Integer addUser(User user);

    //删除用户
    @Delete("delete from user where id=#{userId}")
    Integer removeUser(Integer userId);

    //更新密码
    @Update("update user set password=#{password} where id=#{userId}")
    Integer updatePassword(@Param("password") String password,
                           @Param("userId") Integer userId);

    //更新邮箱
    @Update("update user set email=#{email} where id=#{userId}")
    Integer updateEmail(@Param("email") String email,
                        @Param("userId") Integer userId);

    //更新手机号码
    @Update("update user set phone=#{phone} where id=#{userId}")
    Integer updatePhone(@Param("phone") String phone,
                        @Param("userId") Integer userId);

    //根据用户名查询
    @Select("select * from user where username=#{username}")
    User findByUsername(String username);

    //根据用户id查询
    @Select("select * from user where id=#{userId}")
    User findByUserId(Integer userId);

    //根据用户名与密码查询
    @Select("select * from user where username=#{username} and password=#{password}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "username", property = "username"),
            @Result(column = "email", property = "email"),
            @Result(column = "phone", property = "phone"),
            @Result(column = "avatar", property = "avatar"),
            @Result(column = "points", property = "points"),
            @Result(column = "tags", property = "tags")
    })
    User findByUserIdAndPassword(@Param("username") String username,
                                 @Param("password") String password);

    //查询所有用户
    @Select("select * from user")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "username", property = "username"),
            @Result(column = "email", property = "email"),
            @Result(column = "phone", property = "phone"),
            @Result(column = "avatar", property = "avatar"),
            @Result(column = "points", property = "points"),
            @Result(column = "tags", property = "tags"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime")
    })
    List<User> findAll();

    //查询用户积分
    @Select("select points from user where id=#{userId}")
    Integer findPointsByUserId(Integer userId);

    //更新用户积分
    @Update("update user set points=#{point} where id=#{userId}")
    Integer updatePoints(@Param("userId") Integer userId,
                                @Param("point") Integer point);
}
