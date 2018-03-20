package cn.edu.ujs.VO;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by DELL on 2018/3/2.
 */
@Getter
@Setter
public class RemarkVO {

    private Integer id;

    private String username;

    private String avatar;

    private String content;

    private Integer score;

    private Date createTime;
}
