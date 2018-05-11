package cn.edu.ujs.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by DELL on 2018/5/5.
 */
public class TimeUtil {

    /**
     * String转Date
     * 时间格式（yyyy-MM-dd hh:mm:ss）
     * @param dateStr
     * @return
     */
    public static Date longStrToDate(String dateStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
