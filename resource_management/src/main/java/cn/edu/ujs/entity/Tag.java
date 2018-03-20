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
public class Tag {

    /**标签id*/
    @Id
    @GeneratedValue
    private Integer id;

    /**标签名称*/
    private String tagName;

    /**创建时间*/
    private Date createTime;

    /**更新时间*/
    private Date updateTime;

}
