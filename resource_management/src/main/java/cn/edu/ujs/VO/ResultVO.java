package cn.edu.ujs.VO;

import lombok.Getter;
import lombok.Setter;

/**
 * http请求返回的最外层对象
 * Created by DELL on 2018/3/3.
 */
@Getter
@Setter
public class ResultVO<T> {

    /**错误码*/
    private Integer code;

    /**提示信息*/
    private String msg;

    /**具体内容*/
    private T data;
}
