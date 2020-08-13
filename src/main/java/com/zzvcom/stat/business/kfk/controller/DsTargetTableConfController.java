package com.zzvcom.stat.business.kfk.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzvcom.component.IdGenerator;
import com.zzvcom.stat.business.kfk.entity.DsRowRule;
import com.zzvcom.stat.business.kfk.entity.DsTargeTableConfInfo;
import com.zzvcom.stat.business.kfk.entity.DsTargetTableConf;
import com.zzvcom.stat.business.kfk.service.DsRowRuleService;
import com.zzvcom.stat.business.kfk.service.DsTargetTableConfService;
import com.zzvcom.wrapper.PageData;
import com.zzvcom.wrapper.ResponseWrapper;
import com.zzvcom.wrapper.WrapResponseMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author boz
 * @since 2020-05-21
 */
@RestController
@RequestMapping("/kfk/table")
public class DsTargetTableConfController {

    @Autowired
    DsTargetTableConfService dsTargetTableConfService;

    @Autowired
    DsRowRuleService dsRowRuleService;

    @Autowired
    private IdGenerator idGenerator;

    @PostMapping("add")
    @ApiOperation("增加/修改table")
    public ResponseEntity<ResponseWrapper<DsTargeTableConfInfo>> add(@RequestBody DsTargeTableConfInfo dsTargeTableConfInfo){
        LocalDateTime date = LocalDateTime.now();

        DsTargetTableConf dsTargetTableConf = new DsTargetTableConf();
        QueryWrapper<DsTargetTableConf> tsQueryWrapper = new QueryWrapper<>();
        boolean save = false;
        if(dsTargeTableConfInfo.getTabId() == null){
            //新增
            tsQueryWrapper.eq("tab_name",dsTargeTableConfInfo.getTabName()).eq("tab_database",dsTargeTableConfInfo.getTabDatabase());
            List<DsTargetTableConf> list = dsTargetTableConfService.list(tsQueryWrapper);
            if(list.size() > 0){
                return WrapResponseMapper.error("table已存在！");
            }

            long id = idGenerator.nextId();
            dsTargetTableConf.setTabName(dsTargeTableConfInfo.getTabName());
            dsTargetTableConf.setTargetTabName(dsTargeTableConfInfo.getTargetTabName());
            dsTargetTableConf.setTabDatabase(dsTargeTableConfInfo.getTabDatabase());
            dsTargetTableConf.setTabDescription(dsTargeTableConfInfo.getTabDescription());
            dsTargetTableConf.setTabId(Long.toString(id));
            dsTargetTableConf.setCreatetime(date);
            dsTargetTableConf.setUpdatetime(date);
            dsTargetTableConf.setStatus("1");

            save = dsTargetTableConfService.save(dsTargetTableConf);

            dsTargeTableConfInfo.setCreatetime(date);
            dsTargeTableConfInfo.setUpdatetime(date);
            dsTargeTableConfInfo.setTabId(Long.toString(id));
            dsTargeTableConfInfo.setStatus("1");


            List<DsRowRule> rowRuleList = dsTargeTableConfInfo.getRowRuleList();
            for(int i=0;i<rowRuleList.size();i++){
                long rid = idGenerator.nextId();
                rowRuleList.get(i).setId(Long.toString(rid));
                rowRuleList.get(i).setTabId(Long.toString(id));
                rowRuleList.get(i).setStatus("1");
                rowRuleList.get(i).setCreatetime(date);
                rowRuleList.get(i).setUpdatetime(date);
            }
            if(rowRuleList.size()>0){
                dsRowRuleService.saveBatch(rowRuleList);
            }
        }else {
            //修改
            tsQueryWrapper.and(i -> i.ne("tab_id",dsTargeTableConfInfo.getTabId()).
                    eq("tab_name",dsTargeTableConfInfo.getTabName()).
                    eq("tab_database",dsTargeTableConfInfo.getTabDatabase()));
            List<DsTargetTableConf> list = dsTargetTableConfService.list(tsQueryWrapper);
            if(list.size()>0){
                return WrapResponseMapper.error("table已存在！");
            }

            dsTargetTableConf.setTabName(dsTargeTableConfInfo.getTabName());
            dsTargetTableConf.setTargetTabName(dsTargeTableConfInfo.getTargetTabName());
            dsTargetTableConf.setTabDatabase(dsTargeTableConfInfo.getTabDatabase());
            dsTargetTableConf.setTabDescription(dsTargeTableConfInfo.getTabDescription());
            dsTargetTableConf.setUpdatetime(date);

            QueryWrapper<DsTargetTableConf> queryWrapperTable = new QueryWrapper<>();
            queryWrapperTable.eq("tab_id",dsTargeTableConfInfo.getTabId());
            save = dsTargetTableConfService.update(dsTargetTableConf, queryWrapperTable);

            List<DsRowRule> rowRuleList = dsTargeTableConfInfo.getRowRuleList();
            QueryWrapper<DsRowRule> queryWrapperRowRules = new QueryWrapper<>();
            queryWrapperRowRules.eq("tab_id",dsTargeTableConfInfo.getTabId());
            List<DsRowRule> list1 = dsRowRuleService.list(queryWrapperRowRules);
            List<String> idArr = new ArrayList<>();
            list1.forEach(item -> {
                idArr.add(item.getId());
            });

            for(int i=0;i<rowRuleList.size();i++){
                //判断是否在idArr中，如果不在就需要删除
                if(idArr.contains(rowRuleList.get(i).getId())){
                    idArr.remove(rowRuleList.get(i).getId());
                }

                rowRuleList.get(i).setUpdatetime(date);
                if(rowRuleList.get(i).getId() != null){
                    //修改
                    dsRowRuleService.saveOrUpdate(rowRuleList.get(i));
                }else {
                    //新增
                    long rid = idGenerator.nextId();
                    rowRuleList.get(i).setId(Long.toString(rid));
                    rowRuleList.get(i).setTabId(dsTargeTableConfInfo.getTabId());
                    rowRuleList.get(i).setStatus("1");
                    rowRuleList.get(i).setCreatetime(date);
                    rowRuleList.get(i).setUpdatetime(date);
                    dsRowRuleService.save(rowRuleList.get(i));
                }
            }
           if(idArr.size()>0){
               List<String> idList = new ArrayList<>();
               for(String item : idArr){
                   idList.add(item);
               }
               dsRowRuleService.removeByIds(idList);
           }
        }
        if(save){
            return WrapResponseMapper.ok(dsTargeTableConfInfo);
        }else{
            return WrapResponseMapper.error("保存失败");
        }
    }


    @GetMapping("listByPage")
    @ApiOperation("分页获取table")
    public ResponseEntity<ResponseWrapper<PageData<DsTargetTableConf>>> listByPage(@RequestParam(value = "pageNo") int pageNo, @RequestParam(value = "pageSize") int pageSize, @RequestParam("tabName") String tabName){
        QueryWrapper<DsTargetTableConf> queryWrapper = new QueryWrapper<>();
        Page<DsTargetTableConf> objectPage = new Page<>(pageNo,pageSize);

        if(!"".equals(tabName)){
            queryWrapper.eq("status","1").like("tab_name",tabName).orderByAsc("createtime");
        }else{
            queryWrapper.eq("status","1").orderByAsc("createtime");
        }

        IPage<DsTargetTableConf> page = dsTargetTableConfService.page(objectPage, queryWrapper);
        return WrapResponseMapper.ok(PageData.of(page.getRecords(),(int)page.getTotal(),(int)page.getCurrent(),(int)page.getSize()));
    }




    @GetMapping("delTabById/{tabCode}")
    @ApiOperation("根据id删除table")
    public ResponseEntity<ResponseWrapper<Object>> delTabById(@PathVariable String tabCode){
        QueryWrapper<DsTargetTableConf> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tab_id",tabCode);

        boolean remove = dsTargetTableConfService.remove(queryWrapper);

        QueryWrapper<DsRowRule> queryWrapperRowRule = new QueryWrapper<>();
        queryWrapperRowRule.eq("tab_id",tabCode);
        dsRowRuleService.remove(queryWrapperRowRule);

        if(remove){
            return WrapResponseMapper.ok("删除成功");
        }else {
            return WrapResponseMapper.error("删除失败");
        }
    }


    @PostMapping("delTabsByIdList")
    @ApiOperation("根据一组id删除Table")
    public ResponseEntity<ResponseWrapper<String>> delTabsByIdList(@RequestParam("tabCodes") String tabCodes){
        String[] tabArr = tabCodes.split(",");

        boolean remove = false;
        for (String item : tabArr){
            QueryWrapper<DsTargetTableConf> queryWrapperTab = new QueryWrapper<>();
            queryWrapperTab.eq("tab_id",item);
            remove = dsTargetTableConfService.remove(queryWrapperTab);

            QueryWrapper<DsRowRule> queryWrapperRowRule = new QueryWrapper<>();
            queryWrapperRowRule.eq("tab_id",item);
            remove = dsRowRuleService.remove(queryWrapperRowRule);
        }

        if(remove){
            return WrapResponseMapper.ok("删除成功");
        }else {
            return WrapResponseMapper.error("删除失败");
        }
    }
    
}

