package cn.edu.ujs.enums;

import cn.edu.ujs.entity.Resource;
import lombok.Getter;

/**
 * Created by DELL on 2018/3/5.
 */
@Getter
public enum ResourceEnum {

    REMOVE_SUCCESS(0,"删除资源成功"),
    REMOVE_ERROR(1,"删除资源失败"),
    UPDATE_ERROR(2,"更新资源失败")
    ;
    private Integer code;

    private String message;

    ResourceEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
