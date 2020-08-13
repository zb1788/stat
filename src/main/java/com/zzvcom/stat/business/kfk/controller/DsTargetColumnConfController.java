package com.zzvcom.stat.business.kfk.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzvcom.component.IdGenerator;
import com.zzvcom.stat.business.kfk.entity.DsColumnRule;
import com.zzvcom.stat.business.kfk.entity.DsTargetColumnConf;
import com.zzvcom.stat.business.kfk.entity.DsTargetColumnConfInfo;
import com.zzvcom.stat.business.kfk.service.DsColumnRuleService;
import com.zzvcom.stat.business.kfk.service.DsTargetColumnConfService;
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
@RequestMapping("/kfk/column")
public class DsTargetColumnConfController {

    @Autowired
    DsTargetColumnConfService dsTargetColumnConfService;

    @Autowired
    DsColumnRuleService dsColumnRuleService;

    @Autowired
    private IdGenerator idGenerator;

    @GetMapping("listByPage")
    @ApiOperation("分页获取column")
    public ResponseEntity<ResponseWrapper<PageData<DsTargetColumnConf>>> listByPage(@RequestParam(value = "pageNo") int pageNo, @RequestParam(value = "pageSize") int pageSize, @RequestParam("colName") String colName, @RequestParam(value="tabId") String tabId){
        QueryWrapper<DsTargetColumnConf> queryWrapper = new QueryWrapper<>();
        Page<DsTargetColumnConf> objectPage = new Page<>(pageNo,pageSize);

        if(!"".equals(colName)){
            queryWrapper.eq("tab_id",tabId).eq("status","1").like("col_name",colName);
        }else{
            queryWrapper.eq("tab_id",tabId).eq("status","1");
        }

        IPage<DsTargetColumnConf> page = dsTargetColumnConfService.page(objectPage, queryWrapper);
        return WrapResponseMapper.ok(PageData.of(page.getRecords(),(int)page.getTotal(),(int)page.getCurrent(),(int)page.getSize()));
    }


    @PostMapping("add")
    @ApiOperation("增加/修改column")
    public ResponseEntity<ResponseWrapper<DsTargetColumnConfInfo>> add(@RequestBody DsTargetColumnConfInfo dsTargetColumnConfInfo){
        LocalDateTime date = LocalDateTime.now();

        DsTargetColumnConf dsTargetColumnConf = new DsTargetColumnConf();
        QueryWrapper<DsTargetColumnConf> tsQueryWrapper = new QueryWrapper<>();
        boolean save = false;
        if(dsTargetColumnConfInfo.getId() == null){
            //新增
            tsQueryWrapper.eq("tab_id",dsTargetColumnConfInfo.getTabId()).eq("col_name",dsTargetColumnConfInfo.getColName());
            List<DsTargetColumnConf> list = dsTargetColumnConfService.list(tsQueryWrapper);
            if(list.size() > 0){
                return WrapResponseMapper.error("column已存在！");
            }

            if("".equals(dsTargetColumnConfInfo.getTabId())){
                return WrapResponseMapper.error("tabId不能为空！");
            }

            long id = idGenerator.nextId();
            dsTargetColumnConf.setId(Long.toString(id));
            dsTargetColumnConf.setTabId(dsTargetColumnConfInfo.getTabId());
            dsTargetColumnConf.setColName(dsTargetColumnConfInfo.getColName());
            dsTargetColumnConf.setColLength(dsTargetColumnConfInfo.getColLength());
            dsTargetColumnConf.setStatus("1");
            dsTargetColumnConf.setCreatetime(date);
            dsTargetColumnConf.setUpdatetime(date);


            save = dsTargetColumnConfService.save(dsTargetColumnConf);

            dsTargetColumnConfInfo.setCreatetime(date);
            dsTargetColumnConfInfo.setUpdatetime(date);
            dsTargetColumnConfInfo.setId(Long.toString(id));
            dsTargetColumnConfInfo.setStatus("1");


            List<DsColumnRule> columnRuleList = dsTargetColumnConfInfo.getColumnRuleList();
            for(int i=0;i<columnRuleList.size();i++){
                long rid = idGenerator.nextId();
                columnRuleList.get(i).setId(Long.toString(rid));
                columnRuleList.get(i).setColumnId(Long.toString(id));
                columnRuleList.get(i).setStatus("1");
                columnRuleList.get(i).setCreatetime(date);
                columnRuleList.get(i).setUpdatetime(date);
            }
            if(columnRuleList.size()>0){
                dsColumnRuleService.saveBatch(columnRuleList);
            }
        }else {
            //修改
            tsQueryWrapper.and(i -> i.ne("id",dsTargetColumnConfInfo.getId()).
                    eq("tab_id",dsTargetColumnConfInfo.getTabId()).
                    eq("col_name",dsTargetColumnConfInfo.getColName()));

            List<DsTargetColumnConf> list = dsTargetColumnConfService.list(tsQueryWrapper);
            if(list.size()>0){
                return WrapResponseMapper.error("column已存在！");
            }


            dsTargetColumnConf.setColName(dsTargetColumnConfInfo.getColName());
            dsTargetColumnConf.setColLength(dsTargetColumnConfInfo.getColLength());
            dsTargetColumnConf.setStatus("1");
            dsTargetColumnConf.setUpdatetime(date);

            QueryWrapper<DsTargetColumnConf> queryWrapperColumn = new QueryWrapper<>();
            queryWrapperColumn.eq("id",dsTargetColumnConfInfo.getId());
            save = dsTargetColumnConfService.update(dsTargetColumnConf,queryWrapperColumn);

            List<DsColumnRule> columnRuleList = dsTargetColumnConfInfo.getColumnRuleList();
            QueryWrapper<DsColumnRule> queryWrapperColumnRules = new QueryWrapper<>();
            queryWrapperColumnRules.eq("column_id",dsTargetColumnConfInfo.getId());
            List<DsColumnRule> list1 = dsColumnRuleService.list(queryWrapperColumnRules);
            List<String> idArr = new ArrayList<>();
            list1.forEach(item -> {
                idArr.add(item.getId());
            });

            for(int i=0;i<columnRuleList.size();i++){
                //判断是否在idArr中，如果不在就需要删除
                if(idArr.contains(columnRuleList.get(i).getId())){
                    idArr.remove(columnRuleList.get(i).getId());
                }

                columnRuleList.get(i).setUpdatetime(date);
                if(columnRuleList.get(i).getId() != null){
                    //修改
                    dsColumnRuleService.saveOrUpdate(columnRuleList.get(i));
                }else {
                    //新增
                    long rid = idGenerator.nextId();
                    columnRuleList.get(i).setId(Long.toString(rid));
                    columnRuleList.get(i).setColumnId(dsTargetColumnConfInfo.getId());
                    columnRuleList.get(i).setStatus("1");
                    columnRuleList.get(i).setCreatetime(date);
                    columnRuleList.get(i).setUpdatetime(date);
                    dsColumnRuleService.save(columnRuleList.get(i));
                }
            }
            if(idArr.size()>0){
                List<String> idList = new ArrayList<>();
                for(String item : idArr){
                    idList.add(item);
                }
                dsColumnRuleService.removeByIds(idList);
            }
        }
        if(save){
            return WrapResponseMapper.ok(dsTargetColumnConfInfo);
        }else{
            return WrapResponseMapper.error("保存失败");
        }
    }



    @GetMapping("delColumnById/{colCode}")
    @ApiOperation("根据id删除column")
    public ResponseEntity<ResponseWrapper<String>> delColumnById(@PathVariable String colCode){
        QueryWrapper<DsTargetColumnConf> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",colCode);

        boolean remove = dsTargetColumnConfService.remove(queryWrapper);

        QueryWrapper<DsColumnRule> queryWrapperColRule = new QueryWrapper<>();
        queryWrapperColRule.eq("column_id",colCode);
        dsColumnRuleService.remove(queryWrapperColRule);

        if(remove){
            return WrapResponseMapper.ok("删除成功");
        }else {
            return WrapResponseMapper.error("删除失败");
        }
    }


    @PostMapping("delColsByIdList")
    @ApiOperation("根据一组id删除column")
    public ResponseEntity<ResponseWrapper<String>> delColsByIdList(@RequestParam("colCodes") String colCodes){
        String[] colArr = colCodes.split(",");

        boolean remove = false;
        for (String item : colArr){
            QueryWrapper<DsTargetColumnConf> queryWrapperCol = new QueryWrapper<>();
            queryWrapperCol.eq("id",item);
            remove = dsTargetColumnConfService.remove(queryWrapperCol);

            QueryWrapper<DsColumnRule> queryWrapperColRule = new QueryWrapper<>();
            queryWrapperColRule.eq("column_id",item);
            remove = dsColumnRuleService.remove(queryWrapperColRule);
        }

        if(remove){
            return WrapResponseMapper.ok("删除成功");
        }else {
            return WrapResponseMapper.error("删除失败");
        }
    }


}

