package cn.edu.ujs.enums;

import lombok.Getter;
import org.omg.CORBA.INTERNAL;

/**
 * Created by DELL on 2018/3/2.
 */
@Getter
public enum CheckStatusEnum {

    CHECK_FAIL(0,"未审核"),
    CHECK_SUCCESS(1,"审核通过");

    private Integer code;

    private String message;

    CheckStatusEnum(Integer code, String message) {

        this.code = code;
        this.message = message;
    }

}
