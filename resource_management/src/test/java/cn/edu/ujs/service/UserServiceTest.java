package cn.edu.ujs.service;

import cn.edu.ujs.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by DELL on 2018/2/14.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    public void testIsExist() throws Exception {

        boolean flag = userService.isExist("lujian");
        Assert.assertNotEquals(false,flag);
    }

    @Test
    public void testAddUser() throws Exception {

        User user = new User();
        user.setUsername("lujian3");
        user.setPassword("123456");
        User newUser = userService.addUser(user);
        Assert.assertNotNull(newUser);
    }

    @Test
    public void testRemoveUser() throws Exception {

        User user = userService.removeUser(5);
        Assert.assertNotNull(user);
    }

    @Test
    public void testUpdatePassword() throws Exception {

        User user = userService.updatePassword(4,"123456");
        Assert.assertNotNull(user);
    }

    @Test
    public void testUpdateEmail() throws Exception {

        User user = userService.updateEmail(4,"email@email.com");
        Assert.assertNotNull(user);
    }

    @Test
    public void testUpdatePhone() throws Exception {

        User user = userService.updatePhone(4,"18822221111");
        Assert.assertNotNull(user);
    }

    @Test
    public void testLogin() throws Exception {

        User user = userService.login("lujian","123");
        Assert.assertNotNull(user);
    }

    @Test
    public void testRegister() throws Exception {

        User user = new User();
        user.setUsername("lujian4");
        user.setPassword("123456");
        Assert.assertNotNull(userService.register(user));
    }

    @Test
    public void testFindAll() throws Exception {

        List<User> userList = userService.findAll();
        Assert.assertNotEquals(0,userList.size());
    }
}