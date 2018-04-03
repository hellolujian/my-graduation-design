package cn.edu.ujs.enums;

import lombok.Getter;

/**
 * Created by DELL on 2018/3/30.
 */
@Getter
public enum SortEnum {
    SORT_DEFAULT(0,"默认排序"),
    SORT_LATEST(1,"最新上传"),
    SORT_MOST_DOWNLOAD(2,"下载最多"),
    SORT_CODE(3,"代码资源")
    ;
    private Integer code;

    private String message;

    SortEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
