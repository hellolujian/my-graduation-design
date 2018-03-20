package cn.edu.ujs.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.jpa.criteria.ValueHandlerFactory;
import org.omg.CORBA.INTERNAL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.List;

/**
 * Created by DELL on 2018/2/11.
 */
@Getter
@Setter
@DynamicUpdate
public class Resource {

    /**资源id*/
    private Integer id;

    /**资源标题*/
    private String title;

    /**资源类型id*/
    private Integer typeId;

    /**资源类别id*/
    private Integer categoryId;

    /**资源后缀名*/
    private String suffixName;

    /**所属用户*/
    private Integer userId;

    /**资源自定义标签*/
    private List<String> tagList;

    //private List<String> tagList;

    /**审核状态,默认0未审核*/
    private Integer checkStatus = 0;

    /**资源存放路径*/
    private String position;

    /**资源大小*/
    private String size;

    /**下载资源所需积分*/
    private Integer points;

    /**摘要*/
    private String description;

    /**创建时间*/
    private Date createTime;

    /**更新时间*/
    private Date updateTime;

}
