package cn.edu.ujs.mapper;

import cn.edu.ujs.entity.ResourceSuffix;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by DELL on 2018/3/1.
 */
@Component
public interface ResourceSuffixMapper {

    // TODO: 2018/3/3 资源的图片应该放在suffix表里
    //添加资源后缀
    @Insert("insert into resource_suffix(suffix_name,suffix_image) values(#{suffixName},#{suffixImage})")
    Integer addResourceSuffix(ResourceSuffix resourceSuffix);

    //删除资源后缀
    @Delete("delete from resource_suffix where id=#{resourceSuffixId}")
    Integer removeResourceSuffix(Integer resourceSuffixId);

    //修改资源后缀
    @Update("update resource_suffix set suffix_name=#{resourceSuffixName},suffix_image=#{resourceSuffixImage} where id=#{resourceSuffixId}")
    Integer updateResourceSuffix(@Param("resourceSuffixId") Integer resourceSuffixId,
                                 @Param("resourceSuffixName") String resourceSuffixName,
                                 @Param("resourceSuffixImage") String resourceSuffixImage);

    //根据id查询
    @Select("select * from resource_suffix where id=#{resourceSuffixId}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "suffix_name", property = "suffixName"),
            @Result(column = "suffix_image", property = "suffixImage"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime")
    })
    ResourceSuffix findByResourceSuffixId(Integer resourceSuffixId);

    //查询所有资源类型
    @Select("select * from resource_suffix")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "suffix_name", property = "suffixName"),
            @Result(column = "suffix_image", property = "suffixImage"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime")
    })
    List<ResourceSuffix> findAll();

    //根据后缀名查询id
    @Select("select id from resource_suffix where suffix_name=#{suffixName}")
    Integer findSuffixIdBySuffixName(String suffixName);
}
