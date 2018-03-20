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
public class Collection {

    /**收藏id*/
    @Id
    @GeneratedValue
    private Integer id;

    /**用户id*/
    private Integer userId;

    /**资源id*/
    private Integer resourceId;

    /**收藏时间*/
    private Date createTime;

    /**更新时间*/
    private Date updateTime;

}
