package cn.edu.ujs.enums;

import lombok.Getter;

/**
 * Created by DELL on 2018/3/4.
 */
@Getter
public enum UploadEnum {

    UPLOAD_SUCCESS(0,"上传成功"),
    UPLOAD_ERROR(1,"上传失败"),
    UPLOAD_RESOURCE_IS_NULL(2,"资源文件不能为空"),
    UPLOAD_RESOURCE_IS_EXIST(3,"该资源已经存在")
    ;
    private Integer code;

    private String message;

    UploadEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
