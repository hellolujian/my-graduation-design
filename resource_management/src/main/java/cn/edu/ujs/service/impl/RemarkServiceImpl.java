package cn.edu.ujs.service.impl;

import cn.edu.ujs.VO.RemarkVO;
import cn.edu.ujs.entity.Remark;
import cn.edu.ujs.mapper.RemarkMapper;
import cn.edu.ujs.service.RemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by DELL on 2018/3/2.
 */
@Service
public class RemarkServiceImpl implements RemarkService {

    @Autowired
    private RemarkMapper remarkMapper;

    @Override
    public Remark addRemark(Remark remark) {
// TODO: 2018/3/2 添加评价前要先判断是否已评价 
        Integer result = remarkMapper.addRemark(remark);
        if (result == 1)
            return remark;
        return null;
    }

    @Override
    public List<RemarkVO> findRemarkByResourceId(Integer resourceId) {

        return remarkMapper.findRemarkByResourceId(resourceId);
    }

    @Override
    public Integer getScoreAmount(Integer resourceId) {

        return remarkMapper.getScoreAmount(resourceId);
    }

    @Override
    public Integer getContentAmount(Integer resourceId) {

        return remarkMapper.getContentAmount(resourceId);
    }

    @Override
    public Double getAverageScore(Integer resourceId) {

        return remarkMapper.getAverageScore(resourceId);
    }

    @Override
    public boolean isUserRemark(Integer userId, Integer resourceId) {

        Integer result = remarkMapper.findRemarkByuserIdAndResourceId(userId, resourceId);
        if (result == 1)
            return true;
        return false;
    }
}
