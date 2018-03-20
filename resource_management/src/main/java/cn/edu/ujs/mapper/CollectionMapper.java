package cn.edu.ujs.mapper;

import cn.edu.ujs.entity.Collection;
import lombok.experimental.Delegate;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by DELL on 2018/3/2.
 */
@Component
public interface CollectionMapper {

    //添加收藏记录
    @Insert("insert into collection(user_id,resource_id) values(#{userId},#{resourceId})")
    Integer addCollection(Collection collection);

    //删除收藏记录
    @Delete("delete from collection where id=#{collectionId}")
    Integer removeCollection(Integer collectionId);

    //根据id查询收藏记录
    @Select("select * from collection where id=#{collectionId}")
    Collection findByCollectionId(Integer collectionId);

    //根据user_id查询收藏记录
    @Select("select resource_id from collection where user_id=#{userId}")
    List<Integer> findCollectionByUserId(Integer userId);

    //查询某个用户是否收藏了某资源
    @Select("select count(*) from collection where user_id=#{userId} and resource_id=#{resourceId}")
    Integer findByUserIdAndResourceId(@Param("userId") Integer userId,
                                      @Param("resourceId") Integer resourceId);

    //查询某个资源的收藏个数
    @Select("select count(*) from collection where resource_id=#{resourceId}")
    Integer findByResourceId(Integer resourceId);

}
