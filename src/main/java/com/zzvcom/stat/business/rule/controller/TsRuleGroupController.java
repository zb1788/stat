package com.zzvcom.stat.business.rule.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzvcom.component.IdGenerator;
import com.zzvcom.stat.business.rule.entity.TsRuleGroup;
import com.zzvcom.stat.business.rule.entity.TsTaskRule;
import com.zzvcom.stat.business.rule.service.TsRuleGroupService;
import com.zzvcom.stat.business.rule.service.TsTaskRuleService;
import com.zzvcom.wrapper.PageData;
import com.zzvcom.wrapper.ResponseWrapper;
import com.zzvcom.wrapper.WrapResponseMapper;
import io.swagger.annotations.ApiOperation;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
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
 * @since 2020-05-19
 */
@RestController
@RequestMapping("/rule/tsRuleGroup")
public class TsRuleGroupController {

    @Autowired
    TsRuleGroupService tsRuleGroupService;

    @Autowired
    TsTaskRuleService tsTaskRuleService;

    @Autowired
    private IdGenerator idGenerator;



    @PostMapping("addGroup")
    @ApiOperation("添加/修改分组")
    public ResponseEntity<ResponseWrapper<TsRuleGroup>> addGroup(@RequestBody TsRuleGroup tsRuleGroup) throws ParseException {
        LocalDateTime date = LocalDateTime.now();
        boolean validExpression = CronExpression.isValidExpression(tsRuleGroup.getGroupRule());
        if(!validExpression){
            return WrapResponseMapper.error("Cron表达式格式错误！");
        }
        QueryWrapper<TsRuleGroup> tsQueryWrapper = new QueryWrapper<>();

        boolean save = false;
        if(tsRuleGroup.getGroupCode() == null){
            //新增

            //判断名字和表达式是否重复
            tsQueryWrapper.eq("group_name",tsRuleGroup.getGroupName()).or().eq("group_rule",tsRuleGroup.getGroupRule());
            List<TsRuleGroup> list = tsRuleGroupService.list(tsQueryWrapper);
            if(list.size()>0){
                return WrapResponseMapper.error("分组名称或者Cron表达式已存在！");
            }

            long id = idGenerator.nextId();
            tsRuleGroup.setGroupCode(Long.toString(id));
            tsRuleGroup.setCreateTime(date);
            tsRuleGroup.setUpdateTime(date);
            save = tsRuleGroupService.save(tsRuleGroup);
        }else {
            //修改

            tsQueryWrapper.ne("group_code",tsRuleGroup.getGroupCode()).and(i -> i.eq("group_name",tsRuleGroup.getGroupName()).or().eq("group_rule",tsRuleGroup.getGroupRule()));
            List<TsRuleGroup> list = tsRuleGroupService.list(tsQueryWrapper);
            if(list.size()>0){
                return WrapResponseMapper.error("分组名称或者Cron表达式已存在！");
            }

            tsRuleGroup.setUpdateTime(date);
            QueryWrapper<TsRuleGroup> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("group_code",tsRuleGroup.getGroupCode());

            save = tsRuleGroupService.update(tsRuleGroup, queryWrapper);
        }

        if(save){
            return WrapResponseMapper.ok(tsRuleGroup);
        }else{
            return WrapResponseMapper.error("保存失败");
        }
    }


    @GetMapping("list")
    @ApiOperation("分页获取所有分组")
    public ResponseEntity<ResponseWrapper<PageData<TsRuleGroup>>> getData(@RequestParam(value = "pageNo") int pageNo, @RequestParam(value = "pageSize") int pageSize, @RequestParam("groupName") String groupName){
        QueryWrapper<TsRuleGroup> queryWrapper = new QueryWrapper<>();
        Page<TsRuleGroup> objectPage = new Page<>(pageNo,pageSize);

        if(!"".equals(groupName)){
            queryWrapper.like("group_name",groupName);
        }

        IPage<TsRuleGroup> page = tsRuleGroupService.page(objectPage, queryWrapper);

//        List<TsRuleGroup> list = tsRuleGroupService.list();

        return WrapResponseMapper.ok(PageData.of(page.getRecords(),(int)page.getTotal(),(int)page.getCurrent(),(int)page.getSize()));
    }


    @GetMapping("delRuleById/{groupCode}")
    @ApiOperation("根据id删除分组")
    public ResponseEntity<ResponseWrapper<Object>> delRuleById(@PathVariable String groupCode){

        //判断规则表是否使用此分组
        QueryWrapper<TsTaskRule> queryWrapperRule = new QueryWrapper<>();
        queryWrapperRule.eq("group_code",groupCode);
        List<TsTaskRule> list = tsTaskRuleService.list(queryWrapperRule);
        if(list.size()>0){
            return WrapResponseMapper.error("此分组已被使用，请先删除分组下的规则!");
        }


        QueryWrapper<TsRuleGroup> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("group_code",groupCode);

        boolean remove = tsRuleGroupService.remove(queryWrapper);
        if(remove){
            return WrapResponseMapper.ok("删除成功");
        }else {
            return WrapResponseMapper.error("删除失败");
        }
    }


    @PostMapping("delRuleByIdList")
    @ApiOperation("根据一组id删除分组")
    public ResponseEntity<ResponseWrapper<String>> delRuleByList(@RequestParam("groupCodes") String groupCodes){
        String[] groupArr = groupCodes.split(",");
        Map<String,Object> map = new HashMap<>();

        StringBuffer errMsgList = new StringBuffer();
        for (String item : groupArr){
            map.put("group_code",item);
            QueryWrapper<TsTaskRule> queryWrapperRule = new QueryWrapper<>();
            queryWrapperRule.eq("group_code",item);
            List<TsTaskRule> list = tsTaskRuleService.list(queryWrapperRule);
            if(list.size()>0){
                errMsgList.append("分组ID"+item+"已被使用,无法删除;");
            }else{
                tsRuleGroupService.removeByMap(map);
            }
        }

        if("".equals(errMsgList.toString())){
            return WrapResponseMapper.ok("删除成功");
        }else {
            return WrapResponseMapper.error(errMsgList.toString());
        }
    }


}

