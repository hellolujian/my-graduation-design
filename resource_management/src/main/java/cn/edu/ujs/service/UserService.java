package cn.edu.ujs.service;

import cn.edu.ujs.entity.User;

import java.util.List;
import java.util.Map;

/**
 * Created by DELL on 2018/2/11.
 */
public interface UserService {

    /**根据用户名查询*/
    public User findByUsername(String username);

    /**根据用户id查询*/
    public User findByUserId(Integer userId);

    /**判断用户名是否存在*/
    public boolean isExist(String username);

    /**添加用户*/
    public User addUser(User user);

    /**删除用户*/
    public User removeUser(Integer userId);

    /**更新密码*/
    public User updatePassword(Integer userId, String password);

    /**更新邮箱*/
    public User updateEmail(Integer userId, String email);

    /**更新手机号码*/
    public User updatePhone(Integer userId, String phone);

    /**用户登录操作*/
    public User login(String username, String password);

    /**用户注册*/
    public User register(User user);

    /**用户列表*/
    public List<User> findAll();

    /**分页查询用户列表*/
    public Map<String,Object> findByPage(Integer pageNum, Integer pageSize);

    /**判断用户积分够不够*/
    public boolean isEnabledToDownload(Integer userId, Integer needPoints);

    /**增加用户积分*/
    public User addUserPoints(Integer userId, Integer points);

    /**减少用户积分*/
    public User subUserPoints(Integer userId, Integer points);
}
