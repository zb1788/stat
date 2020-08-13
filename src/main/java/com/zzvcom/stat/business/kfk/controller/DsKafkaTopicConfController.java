package com.zzvcom.stat.business.kfk.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzvcom.component.IdGenerator;
import com.zzvcom.stat.business.kfk.entity.DsKafkaTopicConf;
import com.zzvcom.stat.business.kfk.service.DsKafkaTopicConfService;
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
 * @since 2020-05-21
 */
@RestController
@RequestMapping("/kfk/topic")
public class DsKafkaTopicConfController {

    @Autowired
    DsKafkaTopicConfService dsKafkaTopicConfService;

    @Autowired
    private IdGenerator idGenerator;

    @PostMapping("add")
    @ApiOperation("增加/修改topic")
    public ResponseEntity<ResponseWrapper<Object>> add(@RequestBody DsKafkaTopicConf dsKafkaTopicConf){
        LocalDateTime date = LocalDateTime.now();

        QueryWrapper<DsKafkaTopicConf> tsQueryWrapper = new QueryWrapper<>();
        boolean save = false;
        if(dsKafkaTopicConf.getId() == null){
            //新增
            tsQueryWrapper.eq("platid",dsKafkaTopicConf.getPlatid());
            List<DsKafkaTopicConf> list = dsKafkaTopicConfService.list(tsQueryWrapper);
            if(list.size() > 0){
                return WrapResponseMapper.error("platid已存在！");
            }

            long id = idGenerator.nextId();
            dsKafkaTopicConf.setId(Long.toString(id));
            dsKafkaTopicConf.setCreatetime(date);
            dsKafkaTopicConf.setUpdatetime(date);

            save = dsKafkaTopicConfService.save(dsKafkaTopicConf);
        }else {
            //修改
            tsQueryWrapper.and(i -> i.ne("id",dsKafkaTopicConf.getId()).eq("platid",dsKafkaTopicConf.getPlatid()));
            List<DsKafkaTopicConf> list = dsKafkaTopicConfService.list(tsQueryWrapper);
            if(list.size()>0){
                return WrapResponseMapper.error("platid已存在！");
            }
            dsKafkaTopicConf.setUpdatetime(date);

            QueryWrapper<DsKafkaTopicConf> queryWrapperTopic = new QueryWrapper<>();
            queryWrapperTopic.eq("id",dsKafkaTopicConf.getId());
            save = dsKafkaTopicConfService.update(dsKafkaTopicConf, queryWrapperTopic);
        }
        if(save){
            return WrapResponseMapper.ok(dsKafkaTopicConf);
        }else{
            return WrapResponseMapper.error("保存失败");
        }
    }


    @GetMapping("listByPage")
    @ApiOperation("分页获取topic")
    public ResponseEntity<ResponseWrapper<PageData<DsKafkaTopicConf>>> listByPage(@RequestParam(value = "pageNo") int pageNo, @RequestParam(value = "pageSize") int pageSize, @RequestParam("topic") String topic){
        QueryWrapper<DsKafkaTopicConf> queryWrapper = new QueryWrapper<>();
        Page<DsKafkaTopicConf> objectPage = new Page<>(pageNo,pageSize);

        if(!"".equals(topic)){
            queryWrapper.like("topic",topic);
        }

        IPage<DsKafkaTopicConf> page = dsKafkaTopicConfService.page(objectPage, queryWrapper);

        return WrapResponseMapper.ok(PageData.of(page.getRecords(),(int)page.getTotal(),(int)page.getCurrent(),(int)page.getSize()));
    }




    @GetMapping("delTopicById/{topicCode}")
    @ApiOperation("根据id删除topic")
    public ResponseEntity<ResponseWrapper<Object>> delRuleById(@PathVariable String topicCode){

        QueryWrapper<DsKafkaTopicConf> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",topicCode);

        boolean remove = dsKafkaTopicConfService.remove(queryWrapper);
        if(remove){
            return WrapResponseMapper.ok("删除成功");
        }else {
            return WrapResponseMapper.error("删除失败");
        }
    }


    @PostMapping("delTopicsByIdList")
    @ApiOperation("根据一组id删除TOPIC")
    public ResponseEntity<ResponseWrapper<Object>> delTopicsByIdList(@RequestParam("topicCodes") String topicCodes){
        String[] topicArr = topicCodes.split(",");
        Map<String,Object> map = new HashMap<>();
        for (String item : topicArr){
            map.put("id",item);
        }

        boolean flag = dsKafkaTopicConfService.removeByMap(map);
        if(flag){
            return WrapResponseMapper.ok("删除成功");
        }else {
            return WrapResponseMapper.error("删除失败");
        }
    }
}

