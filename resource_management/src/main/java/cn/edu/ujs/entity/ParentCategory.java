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
public class ParentCategory {

    /**父级分类*/
    @Id
    @GeneratedValue
    private Integer id;

    /**父级分类名称*/
    private String parentCategoryName;

    /**创建时间*/
    private Date createTime;

    /**更新时间*/
    private Date updateTime;
}
