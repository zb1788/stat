package com.zzvcom.stat.business.rule.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzvcom.component.IdGenerator;
import com.zzvcom.stat.business.rule.entity.TsRuleInfo;
import com.zzvcom.stat.business.rule.entity.TsRuleRelationship;
import com.zzvcom.stat.business.rule.entity.TsTaskRule;
import com.zzvcom.stat.business.rule.mapper.TsTaskRuleMapper;
import com.zzvcom.stat.business.rule.service.TsRuleRelationshipService;
import com.zzvcom.wrapper.PageData;
import com.zzvcom.wrapper.ResponseWrapper;
import com.zzvcom.wrapper.WrapResponseMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author boz
 * @since 2020-05-20
 */
@RestController
@RequestMapping("/rule/tsTaskRule")
public class TsTaskRuleController {

    @Autowired
    TsTaskRuleMapper tsTaskRuleMapper;

    @Autowired
    TsRuleRelationshipService tsRuleRelationshipService;

    @Autowired
    private IdGenerator idGenerator;


    @GetMapping("getRuleList")
    public ResponseEntity<ResponseWrapper<PageData<TsRuleInfo>>> getRuleList(@RequestParam(value = "pageNo") int pageNo, @RequestParam(value = "pageSize") int pageSize, @RequestParam("ruleName") String ruleName){
        Page<TsRuleInfo> objectPage = new Page<>(pageNo,pageSize);
        List<TsRuleInfo> ruleList = tsTaskRuleMapper.getRuleList(objectPage);


        Map<String ,String> mapKey = new HashMap<>();
        ruleList.forEach(item->{
            System.out.println(item);
            mapKey.put(item.getRuleCode(),item.getRuleName());
        });

        ruleList.forEach(item->{
            if(item.getBeforeRuleCode() != null){
                item.setBeforeRuleName(mapKey.get(item.getBeforeRuleCode()));
            }
        });


//        Map map = new HashMap();
//        map.put("current",(int)objectPage.getCurrent());
//        map.put("size",(int)objectPage.getSize());
//        map.put("total",(int)objectPage.getTotal());
//        map.put("records",ruleList);

        return WrapResponseMapper.ok(PageData.of(ruleList,(int)objectPage.getTotal(),(int)objectPage.getCurrent(),(int)objectPage.getSize()));
    }

    @GetMapping("getRuleListNoPage/{groupCode}")
    public ResponseEntity<ResponseWrapper<List<TsTaskRule>>> getRuleListNoPage(@PathVariable String groupCode){
        QueryWrapper<TsTaskRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("group_code",groupCode).and(i->i.eq("rule_state","1"));
        List<TsTaskRule> tsTaskRules = tsTaskRuleMapper.selectList(queryWrapper);
        return WrapResponseMapper.ok(tsTaskRules);
    }

    @GetMapping("changeRuleState/{ruleCode}/{ruleState}")
    public ResponseEntity<ResponseWrapper<String>> changeRuleState(@PathVariable String ruleCode, @PathVariable Integer ruleState){
        QueryWrapper<TsRuleRelationship> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("rule_code",ruleCode).or().eq("before_rule_code",ruleCode);
        List<TsRuleRelationship> list = tsRuleRelationshipService.list(queryWrapper);
        if(list.size()>0){
            return WrapResponseMapper.error("当前规则已经关联其他规则或被其他规则关联,无法禁用！");
        }else {
            TsTaskRule tsTaskRule = new TsTaskRule();
            tsTaskRule.setRuleState(ruleState);

            QueryWrapper<TsTaskRule> queryWrapperRule = new QueryWrapper<>();
            queryWrapperRule.eq("rule_code",ruleCode);

            tsTaskRuleMapper.update(tsTaskRule,queryWrapperRule);
            return WrapResponseMapper.ok("操作成功");
        }
    }


    @PostMapping("addRule")
    public ResponseEntity<ResponseWrapper<Object>> addRule(@RequestBody TsRuleInfo tsRuleInfo){
        LocalDateTime date = LocalDateTime.now();
        QueryWrapper<TsTaskRule> queryWrapper = new QueryWrapper<>();


        TsTaskRule tsTaskRule = new TsTaskRule();
        tsTaskRule.setRuleName(tsRuleInfo.getRuleName());
        tsTaskRule.setGroupCode(tsRuleInfo.getGroupCode());
        tsTaskRule.setTaskType(tsRuleInfo.getTaskType());
        tsTaskRule.setTaskContent(tsRuleInfo.getTaskContent());
        tsTaskRule.setMaxTimes(tsRuleInfo.getMaxTimes());
        tsTaskRule.setRemark1(tsRuleInfo.getRemark1());
        tsTaskRule.setRemark2(tsRuleInfo.getRemark2());
        tsTaskRule.setRemark3(tsRuleInfo.getRemark3());

        int save = 0;
        if(tsRuleInfo.getRuleCode() == null){
            //新增

            queryWrapper.eq("rule_name",tsRuleInfo.getRuleName());
            List<TsTaskRule> tsTaskRules = tsTaskRuleMapper.selectList(queryWrapper);
            if(tsTaskRules.size() > 0){
                return WrapResponseMapper.error("规则名称已存在！");
            }

            long id = idGenerator.nextId();
            tsTaskRule.setRuleCode(Long.toString(id));
            tsTaskRule.setCreateTime(date);
            tsTaskRule.setUpdateTime(date);
            save = tsTaskRuleMapper.insert(tsTaskRule);

            if(!"".equals(tsRuleInfo.getBeforeRuleCode())){
                TsRuleRelationship tsRuleRelationship = new TsRuleRelationship();
                tsRuleRelationship.setRuleCode(tsTaskRule.getRuleCode());
                tsRuleRelationship.setBeforeRuleCode(tsRuleInfo.getBeforeRuleCode());
                tsRuleRelationship.setGroupCode(tsRuleInfo.getGroupCode());
                tsRuleRelationship.setCreateTime(date);
                tsRuleRelationship.setUpdateTime(date);
                tsRuleRelationshipService.save(tsRuleRelationship);
            }
        }else {
            //修改

            queryWrapper.ne("rule_code",tsRuleInfo.getRuleCode()).eq("rule_name",tsRuleInfo.getRuleName());
            List<TsTaskRule> tsTaskRules = tsTaskRuleMapper.selectList(queryWrapper);
            if(tsTaskRules.size() > 0){
                return WrapResponseMapper.error("规则名称已存在！");
            }

            tsTaskRule.setUpdateTime(date);

            QueryWrapper<TsTaskRule> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("rule_code",tsRuleInfo.getRuleCode());
            save = tsTaskRuleMapper.update(tsTaskRule, queryWrapper1);

            if(!"".equals(tsRuleInfo.getBeforeRuleCode())){
                QueryWrapper<TsRuleRelationship> queryWrapperRelation = new QueryWrapper<>();
                //1.查找是不是有前置条件，如果没有新增，有了update
                queryWrapperRelation.eq("rule_code",tsRuleInfo.getRuleCode());
                List<TsRuleRelationship> list = tsRuleRelationshipService.list(queryWrapperRelation);

                TsRuleRelationship tsRuleRelationship = new TsRuleRelationship();
                tsRuleRelationship.setBeforeRuleCode(tsRuleInfo.getBeforeRuleCode());
                tsRuleRelationship.setGroupCode(tsRuleInfo.getGroupCode());
                tsRuleRelationship.setUpdateTime(date);
                if(list.size() > 0){
                    //update
                    tsRuleRelationshipService.update(tsRuleRelationship,queryWrapperRelation);
                }else {
                    //add
                    tsRuleRelationship.setCreateTime(date);
                    tsRuleRelationship.setRuleCode(tsRuleInfo.getRuleCode());
                    tsRuleRelationship.setGroupCode(tsRuleInfo.getGroupCode());
                    tsRuleRelationshipService.save(tsRuleRelationship);
                }
            }else{
                //查询tsRuleRelationship是否有数据，有了删掉
                QueryWrapper<TsRuleRelationship> queryWrapperRelation = new QueryWrapper<>();
                queryWrapperRelation.eq("rule_code",tsRuleInfo.getRuleCode());
                tsRuleRelationshipService.remove(queryWrapperRelation);
            }
        }
        if(save>0){
            return WrapResponseMapper.ok(tsTaskRule);
        }else{
            return WrapResponseMapper.error("保存失败");
        }
    }

    @GetMapping("deleteRuleById/{ruleCode}")
    @ApiOperation("根据id删除规则")
    public ResponseEntity<ResponseWrapper<Object>> deleteRuleById(@PathVariable String ruleCode){
        QueryWrapper<TsTaskRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("rule_code",ruleCode);
        int delete = tsTaskRuleMapper.delete(queryWrapper);

        QueryWrapper<TsRuleRelationship> queryWrapperRelation = new QueryWrapper<>();
        queryWrapperRelation.eq("rule_code",ruleCode);
        tsRuleRelationshipService.remove(queryWrapperRelation);

        if(delete>0){
            return WrapResponseMapper.ok("删除成功");
        }else {
            return WrapResponseMapper.error("删除失败");
        }
    }

    @PostMapping("delRulesByIdList")
    @ApiOperation("根据一组id删除规则")
    public ResponseEntity<ResponseWrapper<String>> delRulesByIdList(@RequestParam("ruleCodes") String ruleCodes){
        String[] ruleArr = ruleCodes.split(",");
        Map<String,Object> map = new HashMap<>();
        for (String item : ruleArr){
            map.put("rule_code",item);
        }

        int i = tsTaskRuleMapper.deleteByMap(map);
        tsRuleRelationshipService.removeByMap(map);
        if(i>0){
            return WrapResponseMapper.ok("删除成功");
        }else {
            return WrapResponseMapper.error("删除失败");
        }
    }

}

