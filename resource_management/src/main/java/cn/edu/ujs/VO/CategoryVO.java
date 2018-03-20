package cn.edu.ujs.VO;

import cn.edu.ujs.entity.ChildCategory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * Created by DELL on 2018/3/6.
 */
@Getter
@Setter
//子级分类为空则不参与序列化
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryVO {

    /**父级分类id*/
    private Integer id;

    /**父级分类名称*/
    private String parentCategoryName;

    /**相对应的子级分类*/
    private List<ChildCategory> childCategoryList;

    /**创建时间*/
    private Date createTime;

    /**更新时间*/
    private Date updateTime;
}
