package cn.edu.ujs.mapper;

import cn.edu.ujs.entity.ResourceType;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by DELL on 2018/3/1.
 */
@Component
public interface ResourceTypeMapper {

    //添加资源类型
    @Insert("insert into resource_type(type_name) values(#{typeName})")
    Integer addResourceType(ResourceType resourceType);

    //删除资源类型
    @Delete("delete from resource_type where id=#{resourceTypeId}")
    Integer removeResourceType(Integer resourceTypeId);

    //修改标签名称
    @Update("update resource_type set type_name=#{resourceTypeName} where id=#{resourceTypeId}")
    Integer updateResourceType(@Param("resourceTypeId") Integer resourceTypeId,
                               @Param("resourceTypeName") String resourceTypeName);

    //根据id查询
    @Select("select * from resource_type where id=#{resourceTypeId}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "type_name", property = "typeName"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime")
    })
    ResourceType findByResourceTypeId(Integer resourceTypeId);

    //查询所有资源类型
    @Select("select * from resource_type")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "type_name", property = "typeName"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime")
    })
    List<ResourceType> findAll();
}
