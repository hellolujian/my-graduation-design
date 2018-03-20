package cn.edu.ujs.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.List;

/**
 * Created by DELL on 2018/2/11.
 */
@DynamicUpdate
@Getter
@Setter
public class User {

    /**用户id*/
    private Integer id;

    /**用户名*/
    private String username;

    /**密码*/
    @JsonIgnore
    private String password;

    /**邮箱*/
    private String email;

    /**号码*/
    private String phone;

    /**头像*/
    private String avatar;

    /**积分*/
    private Integer points;

    /**标签*/
    private List<String> tags;

    /**注册时间*/
    private Date createTime;

    /**更新时间*/
    private Date updateTime;
}
