package cn.edu.ujs.controller;

import cn.edu.ujs.VO.ResultVO;
import cn.edu.ujs.entity.User;
import cn.edu.ujs.enums.UserEnum;
import cn.edu.ujs.mapper.UserMapper;
import cn.edu.ujs.service.UserService;
import cn.edu.ujs.util.HttpClientUtil;
import cn.edu.ujs.util.ResultVOUtil;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DELL on 2018/3/6.
 */
@RestController
@RequestMapping(value = "/users")
public class UserController {

    //打印日志
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    
    @Autowired
    private UserService userService;

    //用户列表
    @GetMapping("/")
    public ResultVO getUsers() {
        ResultVO resultVO = null;
        List<User> userList = userService.findAll();
        resultVO = ResultVOUtil.success(userList);
        return resultVO;
    }

    //分页查询
    @GetMapping("/page/{pageNum}/{pageSize}")
    public ResultVO getUsersPage(@PathVariable(value = "pageNum") Integer pageNum,
                                 @PathVariable(value = "pageSize") Integer pageSize) {

        logger.info("页数:{},每页数量:{}",pageNum,pageSize);
        ResultVO resultVO = ResultVOUtil.success(userService.findByPage(pageNum, pageSize)) ;
        return resultVO;
    }

    @PostMapping("/remove/{userId}")
    public ResultVO removeUser(@PathVariable(value = "userId") Integer userId) {
        ResultVO resultVO = null;
        User user = userService.removeUser(userId);
        if (user != null)
            resultVO = ResultVOUtil.success(user);
        else
            resultVO = ResultVOUtil.error();
        return resultVO;
    }

    //用户信息
    @GetMapping("/my")
    public ResultVO gerUserInfo(HttpServletRequest httpServletRequest) {

        ResultVO resultVO = null;
        HttpSession session = httpServletRequest.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        resultVO = ResultVOUtil.success(userId);
        return resultVO;
    }

    //用户注册

    //用户登录
    @RequestMapping(value = "/login")
    public ResultVO login(@RequestParam(value = "username") String username,
                          @RequestParam(value = "password") String password,
                          HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        ResultVO resultVO = null;
        User user = userService.login(username, password);
        if (user != null) {
            resultVO = ResultVOUtil.success(user);
            logger.info("userInfo:{}",resultVO);
            // TODO: 2018/3/6 记录session信息
            session.setAttribute("userId", user.getId());
            session.setMaxInactiveInterval(100);
        } else
            resultVO = ResultVOUtil.error(UserEnum.LOGIN_ERROR.getCode(),
                    UserEnum.LOGIN_ERROR.getMessage());
        return resultVO;

    }

    //用户登出

    //更新密码

    //更新邮箱

    //更新手机号码

    //请求验证码
    @GetMapping(value = "/code")
    public ResultVO getCode(@RequestParam(value = "phone") String phone,
                            HttpServletRequest httpServletRequest) {

        Map<String, Integer> map = null;
        Integer code = null;
        try {
            map = HttpClientUtil.sendMessage(phone);
            code = map.get("mobile_code");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (code != null) {
            HttpSession session = httpServletRequest.getSession();
            session.setAttribute("code",code);
        }

        ResultVO resultVO = ResultVOUtil.success(code);
        return resultVO;
    }
}
