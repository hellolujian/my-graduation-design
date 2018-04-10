package cn.edu.ujs.VO;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * Created by DELL on 2018/3/1.
 */
@Getter
@Setter
public class ResourceVO {

    private Integer id;

    private String title;

    private String description;

    //下载次数
    private Integer downloads = 0;

    private String size;

    private String suffixName;

    private String image;

    private String position;

    private String points;

    private Integer checkStatus;

    private String userName;

    private Integer userId;

    //以json格式存储
    private List<String> tagList;

    private Date uploadTime;

    private Date updateTime;

    private String name;

    private String category;

    private String type;

}
