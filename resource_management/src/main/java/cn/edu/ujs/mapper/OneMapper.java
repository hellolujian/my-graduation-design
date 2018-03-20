package cn.edu.ujs.mapper;

import cn.edu.ujs.entity.One;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by DELL on 2018/3/16.
 */
@Component
public interface OneMapper {

    //添加标签
    @Insert("insert into one(list) values(#{list})")
    Integer addOne(One one);

    //查询所有标签
    @Select("select * from one")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "list", property = "list", javaType = List.class, jdbcType = JdbcType.VARCHAR,typeHandler = cn.edu.ujs.typehandler.MyHandler.class)
    })
    List<One> findAll();
}
