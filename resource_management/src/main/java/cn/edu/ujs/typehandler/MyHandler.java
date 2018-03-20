package cn.edu.ujs.typehandler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by DELL on 2018/3/16.
 */
@MappedTypes(List.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class MyHandler implements TypeHandler<List<String>> {
    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, List<String> strings, JdbcType jdbcType) throws SQLException {
        String listStr = JSON.toJSONString(strings);
        preparedStatement.setString(i,listStr);
    }

    @Override
    public List<String> getResult(ResultSet resultSet, String s) throws SQLException {

        return JSONArray.parseArray(resultSet.getString(s),String.class);
    }

    @Override
    public List<String> getResult(ResultSet resultSet, int i) throws SQLException {
        return JSONArray.parseArray(resultSet.getString(i),String.class);
    }

    @Override
    public List<String> getResult(CallableStatement callableStatement, int i) throws SQLException {
        return JSONArray.parseArray(callableStatement.getString(i),String.class);
    }
}
