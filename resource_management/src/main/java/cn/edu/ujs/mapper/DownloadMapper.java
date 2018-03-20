package cn.edu.ujs.mapper;

import cn.edu.ujs.entity.Download;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by DELL on 2018/3/2.
 */
@Component
public interface DownloadMapper {

    //添加下载记录
    // TODO: 2018/3/2 自己也可以下载自己的，但是没有积分 
    @Insert("insert into download(user_id,resource_id) values(#{userId},#{resourceId})")
    Integer addDownload(Download download);

    //根据user_id查询下载记录
    @Select("select resource_id from download where user_id=#{userId}")
    List<Integer> findDownLoadByUserId(Integer userId);

    //查询某个资源的下载个数
    @Select("select count(*)as downloads from download where resource_id=#{resourceId}")
    Integer getDownloads(Integer resourceId);

    //查询某个用户是否下载某资源(下载了才可以评论,可以重复下载)
    @Select("select count(*) from download where user_id=#{userId} and resource_id=#{resourceId}")
    Integer findDownByuserIdAndResourceId(@Param("userId") Integer userId,
                                          @Param("resourceId") Integer resourceId);
}
