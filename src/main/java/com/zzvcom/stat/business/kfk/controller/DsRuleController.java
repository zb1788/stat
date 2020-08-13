package com.zzvcom.stat.business.kfk.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzvcom.stat.business.kfk.entity.DsRule;
import com.zzvcom.stat.business.kfk.service.DsRuleService;
import com.zzvcom.wrapper.ResponseWrapper;
import com.zzvcom.wrapper.WrapResponseMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author boz
 * @since 2020-05-26
 */
@RestController
@RequestMapping("/kfk/rule")
public class DsRuleController {

    @Autowired
    DsRuleService dsRuleService;


    @GetMapping("/list/{type}")
    @ApiOperation("根据类型获取所有规则")
    public ResponseEntity<ResponseWrapper<List<DsRule>>> getRuleDictionary(@PathVariable String type){
        QueryWrapper<DsRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("rule_type",type);
        List<DsRule> list = dsRuleService.list(queryWrapper);
        return WrapResponseMapper.ok(list);
    }
}

