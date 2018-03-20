package cn.edu.ujs.mapper;

import cn.edu.ujs.VO.ResourceVO;
import cn.edu.ujs.VO.ResultVO;
import cn.edu.ujs.entity.Resource;
import cn.edu.ujs.mapper.MapperProvider.ResourceMapperProvider;
import lombok.experimental.Delegate;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by DELL on 2018/3/1.
 */
@Component
public interface ResourceMapper {

    //添加资源
    @Insert("insert into resource(resource_title,resource_type_id,resource_category_id," +
            "resource_suffix_name,user_id,tag_list,check_status,resource_position," +
            "resource_size,points,description) values(#{title},#{typeId},#{categoryId},#{suffixName},#{userId}," +
            "#{tagList},#{checkStatus},#{position},#{size},#{points},#{description})")
    Integer addResource(Resource resource);

    //删除资源
    @Delete("delete from resource where id=#{resourceId}")
    Integer removeResource(Integer resourceId);

    //修改资源
    @Update("update resource set resource_title=#{title},resource_type_id=#{typeId}," +
            "resource_category_id=#{categoryId}," +
            "resource_suffix_name=#{suffixName},user_id=#{userId},tag_list=#{tagList},check_status=#{checkStatus},resource_position=#{position}," +
            "resource_size=#{size},points=#{points},description=#{description} where id=#{id}")
    Integer updateResource(Resource resource);

    //修改资源(不确定字段)
    @UpdateProvider(type = ResourceMapperProvider.class, method = "updateResourceMultiFieldSql")
    Integer updateResourceMultiField(Resource resource);

    //查询资源是否已存在
    @Select("select count(*) from resource where resource_title=#{title} and resource_suffix_name=#{suffixName}")
    Integer findByTitleAndSuffixName(@Param("title") String title,
                                     @Param("suffixName") String suffixName);

    //根据id查询
    @Select("select * from resource where id=#{resourceId}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "resource_title", property = "title"),
            @Result(column = "resource_type_id", property = "typeId"),
            @Result(column = "resource_category_id", property = "categoryId"),
            @Result(column = "resource_suffix_name", property = "suffixName"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "tag_list", property = "tagList"),
            @Result(column = "check_status", property = "checkStatus"),
            @Result(column = "resource_position", property = "position"),
            @Result(column = "resource_size", property = "size"),
            @Result(column = "points", property = "points"),
            @Result(column = "description", property = "description"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime")
    })
    Resource findByResourceId(Integer resourceId);

    //根据id查询，返回ResourceVO
    @Select("select * from resource where id=#{resourceId}")
    @Results(id = "resourceVOMap", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "resource_title", property = "title"),
            @Result(column = "downloads", property = "downloads"),
            @Result(column = "resource_suffix_name", property = "suffixName"),
            @Result(column = "username", property = "userName"),
            @Result(column = "tag_list", property = "tagList"),
            @Result(column = "check_status", property = "checkStatus"),
            @Result(column = "resource_position", property = "position"),
            @Result(column = "suffix_image", property = "image"),
            @Result(column = "resource_size", property = "size"),
            @Result(column = "points", property = "points"),
            @Result(column = "description", property = "description"),
            @Result(column = "create_time", property = "uploadTime"),
            @Result(column = "update_time", property = "updateTime")
    })
    ResourceVO getOneResourceVO(Integer resourceId);

    //查询所有资源(最新)
    @Select("select r.id,r.resource_title,r.description,ifnull(d.downloads,0)as downloads," +
            "r.resource_size,r.resource_suffix_name,s.suffix_image,r.resource_position,r.points," +
            "r.check_status,u.username,r.tag_list,r.create_time,r.update_time " +
            "from resource r left join " +
            "(select resource_id,count(user_id)as downloads from download group by resource_id) d " +
            "on r.id=d.resource_id " +
            "left join user u on r.user_id=u.id " +
            "left join resource_suffix s on r.resource_suffix_name=s.suffix_name order by update_time asc")
    @ResultMap("resourceVOMap")
    List<ResourceVO> findAllOrderByUpdateTime();

    //根据审核状态查询所有资源(最新)
    @Select("select r.id,r.resource_title,r.description,ifnull(d.downloads,0)as downloads," +
            "r.resource_size,r.resource_suffix_name,s.suffix_image,r.resource_position,r.points," +
            "r.check_status,u.username,r.tag_list,r.create_time,r.update_time " +
            "from resource r left join " +
            "(select resource_id,count(user_id)as downloads from download group by resource_id) d " +
            "on r.id=d.resource_id " +
            "left join user u on r.user_id=u.id " +
            "left join resource_suffix s on r.resource_suffix_name=s.suffix_name where r.check_status=#{checkStatus} order by update_time asc")
    @ResultMap("resourceVOMap")
    List<ResourceVO> findAllOrderByCheckStatus(Integer checkStatus);

    //多条件查询
    @SelectProvider(type = ResourceMapperProvider.class, method = "multiConditionalQuery")
    @ResultMap("resourceVOMap")
    List<ResourceVO> findAllByMultiCondition(@Param("parentCategoryId") Integer parentCategoryId,
                                             @Param("childCategoryId") Integer childCategoryId,
                                             @Param("resourceTypeId") Integer resourceTypeId,
                                             @Param("checkStatus") Integer checkStatus,
                                             @Param("sortType") Integer sortType,
                                             @Param("keyword") String keyword);

    //根据user_id和resource_id查询
    @Select("select count(*) from resource where user_id=#{userId} and resource_id=#{resourceId}")
    Integer findByUserIdAndResourceId(@Param("userId") Integer userId,
                                      @Param("resourceId") Integer resourceId);

    //#{}自带引号，${}不带引号
    @Select("select * from resource where resource_title like '%${title}%' or id like '%${id}%'")
    List<ResourceVO> getResourceVO(@Param("title") String title,
                                 @Param("id") Integer id);

    //根据user_id查询
    @SelectProvider(type = ResourceMapperProvider.class, method = "findByUserId")
    @ResultMap("resourceVOMap")
    List<ResourceVO> findResourcesByUserId(Integer userId);

}
