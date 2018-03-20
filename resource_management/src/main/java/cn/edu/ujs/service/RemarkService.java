package cn.edu.ujs.service;

import cn.edu.ujs.VO.RemarkVO;
import cn.edu.ujs.entity.Remark;
import org.omg.CORBA.INTERNAL;

import java.util.List;

/**
 * Created by DELL on 2018/3/2.
 */
public interface RemarkService {

    /**添加评价*/
    public Remark addRemark(Remark remark);

    /**查询某个资源的评价列表*/
    public List<RemarkVO> findRemarkByResourceId(Integer resourceId);

    /**获取评分个数*/
    public Integer getScoreAmount(Integer resourceId);

    /**获取评论个数*/
    public Integer getContentAmount(Integer resourceId);

    /**获取综合评分*/
    public Double getAverageScore(Integer resourceId);

    /**判断某个用户是否已评价某资源*/
    public boolean isUserRemark(Integer userId, Integer resourceId);
}
