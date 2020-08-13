package com.zzvcom.stat.business.rule.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzvcom.stat.business.rule.entity.TsRuleInfo;
import com.zzvcom.stat.business.rule.entity.TsTaskRule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author boz
 * @since 2020-05-20
 */
@Mapper
@Component
public interface TsTaskRuleMapper extends BaseMapper<TsTaskRule> {

    @Select("SELECT r.rule_code,r.rule_name,r.group_code,r.rule_state,(SELECT g.group_name FROM ts_rule_group g WHERE g.group_code=r.group_code) as group_name,r.task_content,r.task_type,r.max_times,r.create_time,r.update_time,r.remark1,(SELECT s.before_rule_code FROM ts_rule_relationship s WHERE s.rule_code=r.rule_code) as before_rule_code FROM ts_task_rule r")
    List<TsRuleInfo> getRuleList(Page<TsRuleInfo> objectPage);
}
