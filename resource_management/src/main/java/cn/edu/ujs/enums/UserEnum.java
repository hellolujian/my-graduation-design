package cn.edu.ujs.enums;

import lombok.Getter;

/**
 * Created by DELL on 2018/3/6.
 */
@Getter
public enum UserEnum {

    LOGIN_SUCCESS(0,"登录成功"),
    LOGIN_ERROR(1,"用户名或密码不正确")
    ;
    private Integer code;

    private String message;

    UserEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
