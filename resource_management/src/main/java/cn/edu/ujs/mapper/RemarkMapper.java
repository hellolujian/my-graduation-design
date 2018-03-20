package cn.edu.ujs.mapper;

import cn.edu.ujs.VO.RemarkVO;
import cn.edu.ujs.entity.Remark;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by DELL on 2018/3/2.
 */
@Component
public interface RemarkMapper {

    //添加评价
    @Insert("insert into remark(user_id,resource_id,content,score) values(#{userId},#{resourceId},#{content},#{score})")
    Integer addRemark(Remark remark);

    //根据resource_id查询评价
    @Select("select u.username,u.avatar,r.id,r.content,r.score,r.create_time from user u,remark r where u.id=r.user_id and r.resource_id=#{resourceId} and r.content is not null")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "username", property = "username"),
            @Result(column = "avatar", property = "avatar"),
            @Result(column = "content", property = "content"),
            @Result(column = "create_time", property = "createTime")
    })
    List<RemarkVO> findRemarkByResourceId(Integer resourceId);

    //获取评分个数
    @Select("select count(*)as scoreAmount from remark where resource_id=#{resourceId}")
    Integer getScoreAmount(Integer resourceId);

    //获取评论个数
    @Select("select count(*)as contentAmount from remark where resource_id=#{resourceId} and content is not null")
    Integer getContentAmount(Integer resourceId);

    //获取综合评分
    @Select("select format(avg(score),1) from remark where resource_id=#{resourceId}")
    Double getAverageScore(Integer resourceId);

    //查询某个用户是否已评价某资源
    @Select("select count(*) from remark where user_id=#{userId} and resource_id=#{resourceId}")
    Integer findRemarkByuserIdAndResourceId(@Param("userId") Integer userId,
                                            @Param("resourceId") Integer resourceId);

}
