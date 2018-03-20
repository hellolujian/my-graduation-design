package cn.edu.ujs.mapper;

import cn.edu.ujs.entity.ParentCategory;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by DELL on 2018/3/1.
 */
@Component
public interface ParentCategoryMapper {

    //添加父级分类
    @Insert("insert into parent_category(parent_category_name) values(#{parentCategoryName})")
    Integer addParentCategory(ParentCategory parentCategory);

    //删除父级分类（注：一般不会删除，不考虑级联删除）
    @Delete("delete from parent_category where id=#{parentCategoryId}")
    Integer removeParentCategory(Integer parentCategoryId);

    //修改父级分类
    @Update("update parent_category set parent_category_name=#{parentCategoryName} where id=#{parentCategoryId}")
    Integer updateParentCategory(@Param("parentCategoryId") Integer parentCategoryId,
                                 @Param("parentCategoryName") String parentCategoryName);

    //根据id查询
    @Select("select * from parent_category where id=#{parentCategoryId}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "parent_category_name", property = "parentCategoryName"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime")
    })
    ParentCategory findByParentCategoryId(Integer parentCategoryId);

    //查询所有资源类型
    @Select("select * from parent_category")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "parent_category_name", property = "parentCategoryName"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime")
    })
    List<ParentCategory> findAll();
}
