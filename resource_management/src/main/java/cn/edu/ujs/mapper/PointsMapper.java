package cn.edu.ujs.mapper;

import cn.edu.ujs.entity.Points;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by DELL on 2018/3/17.
 */
@Component
public interface PointsMapper {

    @Select("select * from points")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "resource_point", property = "point")
    })
    List<Points> findAll();
}
