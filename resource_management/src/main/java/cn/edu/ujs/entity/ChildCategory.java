package cn.edu.ujs.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class ChildCategory {

    /**子级分类id*/
    @Id
    @GeneratedValue
    private Integer id;

    @JsonIgnore
    /**父级分类id*/
    private Integer parentCategoryId;

    /**子级分类名称*/
    private String childCategoryName;

    /**创建时间*/
    private Date createTime;

    /**更新时间*/
    private Date updateTime;

}
