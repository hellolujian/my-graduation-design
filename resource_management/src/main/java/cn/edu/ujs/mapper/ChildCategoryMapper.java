package cn.edu.ujs.mapper;

import cn.edu.ujs.entity.ChildCategory;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by DELL on 2018/3/1.
 */
@Component
public interface ChildCategoryMapper {

    //添加子级分类
    @Insert("insert into child_category(parent_category_id,child_category_name) values(#{parentCategoryId},#{childCategoryName})")
    Integer addChildCategory(ChildCategory childCategory);

    //删除子级分类
    @Delete("delete from child_category where id=#{childCategoryId}")
    Integer removeChildCategory(Integer childCategoryId);

    //根据parent_category_id删除
    @Delete("delete from child_category where parent_category_id=#{parentCategoryId}")
    Integer removeChildCategoryByParentId(Integer parentCategoryId);

    //修改子级分类
    @Update("update child_category set child_category_name=#{childCategoryName} where id=#{childCategoryId}")
    Integer updateChildCategory(@Param("childCategoryId") Integer childCategoryId,
                                @Param("childCategoryName") String childCategoryName);

    //根据子级分类id查询
    @Select("select * from child_category where id=#{childCategoryId}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "child_category_name", property = "childCategoryName"),
            @Result(column = "parent_category_id", property = "parentCategoryId"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime")
    })
    ChildCategory findByChildCategoryId(Integer childCategoryId);

    //根据父级分类id查询
    @Select("select * from child_category where parent_category_id=#{parentCategoryId}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "child_category_name", property = "childCategoryName"),
            @Result(column = "parent_category_id", property = "parentCategoryId"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime")
    })
    List<ChildCategory> findByParentCategoryId(Integer parentCategoryId);
}
