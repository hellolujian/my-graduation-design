package cn.edu.ujs.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by DELL on 2018/2/11.
 */
@Entity
@Getter
@Setter
@DynamicUpdate
public class ResourceSuffix {

    /**资源后缀id*/
    @Id
    @GeneratedValue
    private Integer id;

    /**资源后缀名*/
    private String suffixName;

    /**后缀图片*/
    private String suffixImage;

    /**创建时间*/
    private Date createTime;

    /**更新时间*/
    private Date updateTime;

}
