package cn.edu.ujs.util;

import com.alibaba.fastjson.JSONArray;

import java.util.List;

/**
 * Created by DELL on 2018/3/16.
 */
public class JSONUtil {

    public static List<Object> JSONStr2List(String jsonStr) {
        return JSONArray.parseArray(jsonStr);
    }
}
