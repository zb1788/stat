package com.zzvcom.stat.business.kfk.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzvcom.stat.business.kfk.entity.DsColumnRule;
import com.zzvcom.stat.business.kfk.service.DsColumnRuleService;
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
@RequestMapping("/kfk/colRule")
public class DsColumnRuleController {

    @Autowired
    DsColumnRuleService dsColumnRuleService;

    @GetMapping("/list/{colId}")
    @ApiOperation("获取当前字段规则")
    public ResponseEntity<ResponseWrapper<List<DsColumnRule>>> getColRuleByColId(@PathVariable String colId){
        QueryWrapper<DsColumnRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("column_id",colId).eq("status","1");

        List<DsColumnRule> list = dsColumnRuleService.list(queryWrapper);
        return WrapResponseMapper.ok(list);
    }
}

