package cn.edu.ujs.controller;

import cn.edu.ujs.VO.ResultVO;
import cn.edu.ujs.mapper.PointsMapper;
import cn.edu.ujs.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by DELL on 2018/3/17.
 */
@RestController
@RequestMapping("/points")
public class PointsController {

    @Autowired
    private PointsMapper pointsMapper;

    @GetMapping(value = "/")
    public ResultVO getPoints() {
        return ResultVOUtil.success(pointsMapper.findAll());
    }
}
