package cn.edu.ujs.enums;

import cn.edu.ujs.entity.Download;
import lombok.Getter;

/**
 * Created by DELL on 2018/3/5.
 */
@Getter
public enum DownloadEnum {

    DOWNLOAD_SUCCESS(0,"下载成功"),
    DOWNLOAD_ERROR(1,"下载失败"),
    DOWNLOAD_NOT_ENOUGHT_POINTS(2,"积分不够")
    ;
    private Integer code;

    private String message;

    DownloadEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
