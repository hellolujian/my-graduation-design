package cn.edu.ujs.mapper;

import cn.edu.ujs.entity.Tag;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import javax.persistence.Table;
import javax.swing.text.html.HTML;
import java.util.List;

/**
 * Created by DELL on 2018/2/14.
 */
@Component
public interface TagMapper {

    //添加标签
    @Insert("insert into tag(tag_name) values(#{tagName})")
    Integer addTag(Tag tag);

    //删除标签
    @Delete("delete from tag where id=#{tagId}")
    Integer removeTag(Integer tagId);

    //修改标签名称
    @Update("update tag set tag_name=#{tagName} where id=#{tagId}")
    Integer updateTag(@Param("tagId") Integer tagId,
                      @Param("tagName") String tagName);

    //根据标签id查询
    @Select("select * from tag where id=#{tagId}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "tag_name", property = "tagName"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime")
    })
    Tag findByTagId(Integer tagId);

    //查询所有标签
    @Select("select * from tag")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "tag_name", property = "tagName"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime")
    })
    List<Tag> findAll();
}
