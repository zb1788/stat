package com.zzvcom.stat.business.kfk.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzvcom.stat.business.kfk.entity.DsRowRule;
import com.zzvcom.stat.business.kfk.service.DsRowRuleService;
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
@RequestMapping("/kfk/rowRule")
public class DsRowRuleController {

    @Autowired
    DsRowRuleService dsRowRuleService;


    @GetMapping("/list/{tabId}")
    @ApiOperation("根据表id获取表规则")
    public ResponseEntity<ResponseWrapper<List<DsRowRule>>> getRowRuleByTabId(@PathVariable String tabId){
        QueryWrapper<DsRowRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tab_id",tabId).eq("status","1");

        List<DsRowRule> list = dsRowRuleService.list(queryWrapper);
        return WrapResponseMapper.ok(list);
    }

}

