package cn.edu.ujs.service.impl;

import cn.edu.ujs.entity.User;
import cn.edu.ujs.mapper.UserMapper;
import cn.edu.ujs.service.UserService;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DELL on 2018/2/11.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public User findByUserId(Integer userId) {

        return userMapper.findByUserId(userId);
    }

    @Override
    public boolean isExist(String username) {

        User user = userMapper.findByUsername(username);
        if (user != null)
            return true;
        return false;
    }

    @Override
    public User addUser(User user) {

        boolean existFlag = isExist(user.getUsername());
        if (!existFlag) {
            Integer result = userMapper.addUser(user);
            if (result == 1) {
                user = findByUsername(user.getUsername());
                return user;
            }

        }
        return null;
    }

    @Override
    public User removeUser(Integer userId) {

        User user = userMapper.findByUserId(userId);
        Integer result = userMapper.removeUser(userId);
        if (result == 1)
            return user;
        return null;
    }

    @Override
    public User updatePassword(Integer userId, String password) {

        User user = userMapper.findByUserId(userId);
        Integer result = userMapper.updatePassword(password, userId);
        if (result == 1)
            return user;
        return null;
    }

    @Override
    public User updateEmail(Integer userId, String email) {

        User user = userMapper.findByUserId(userId);
        Integer result = userMapper.updateEmail(email, userId);
        if (result == 1)
            return user;
        return null;
    }

    @Override
    public User updatePhone(Integer userId, String phone) {

        User user = userMapper.findByUserId(userId);
        Integer result = userMapper.updatePhone(phone, userId);
        if (result == 1)
            return user;
        return null;
    }

    @Override
    public User login(String username, String password) {

        User user = userMapper.findByUserIdAndPassword(username,password);
        if (user != null)
            return user;
        return null;
    }

    @Override
    public User register(User user) {

        Integer result = userMapper.addUser(user);
        if (result == 1)
            return user;
        return null;
    }

    @Override
    public List<User> findAll() {

        List<User> userList = userMapper.findAll();
        return userList;
    }

    @Override
    public Map<String,Object> findByPage(Integer pageNum, Integer pageSize) {

        Map<String,Object> map = new HashMap<>();
        PageHelper.startPage(pageNum,pageSize,true);
        List<User> userList = userMapper.findAll();
        PageInfo pageInfo = new PageInfo(userList);
        map.put("data",userList);
        map.put("count",pageInfo.getTotal());
        return map;
    }

    @Override
    public boolean isEnabledToDownload(Integer userId, Integer needPoints) {

        Integer userPoints = userMapper.findPointsByUserId(userId);
        if (userPoints >= needPoints)
            return true;
        return false;
    }

    @Override
    public User addUserPoints(Integer userId, Integer points) {

        Integer userPoints = userMapper.findPointsByUserId(userId);
        userPoints = userPoints + points;
        Integer result = userMapper.updatePoints(userId,userPoints);
        User user = userMapper.findByUserId(userId);
        if (result == 1)
            return user;
        return null;

    }

    @Override
    public User subUserPoints(Integer userId, Integer points) {

        Integer userPoints = userMapper.findPointsByUserId(userId);
        userPoints = userPoints - points;
        Integer result = userMapper.updatePoints(userId,userPoints);
        User user = userMapper.findByUserId(userId);
        if (result == 1)
            return user;
        return null;

    }

}
