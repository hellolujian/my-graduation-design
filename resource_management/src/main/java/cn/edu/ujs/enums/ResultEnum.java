package cn.edu.ujs.enums;

import lombok.Getter;

/**
 * Created by DELL on 2018/3/4.
 */
@Getter
public enum ResultEnum {


    SUCCESS(0,"成功"),
    ERROR(1,"失败")
    ;
    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
