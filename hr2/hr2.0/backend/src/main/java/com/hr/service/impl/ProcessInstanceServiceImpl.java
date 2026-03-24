package com.hr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hr.dao.ProcessInstanceDao;
import com.hr.entity.ProcessInstance;
import com.hr.service.ProcessInstanceService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ProcessInstanceServiceImpl extends ServiceImpl<ProcessInstanceDao, ProcessInstance> implements ProcessInstanceService {

    @Override
    public String startProcess(Long templateId, Map<String, Object> variables, Long initiatorId, String initiatorName) {
        // 实际项目中应该使用Flowable引擎启动流程
        // 这里只是示例，实际应该调用Flowable的API
        String processInstanceId = UUID.randomUUID().toString();
        
        ProcessInstance instance = new ProcessInstance();
        instance.setTenantId(1L); // 假设当前租户ID为1
        instance.setProcessInstanceId(processInstanceId);
        instance.setTemplateId(templateId);
        instance.setTemplateName("请假流程"); // 实际应该从模板中获取
        instance.setBusinessKey(UUID.randomUUID().toString());
        instance.setTitle("请假申请"); // 实际应该从变量中获取
        instance.setInitiatorId(initiatorId);
        instance.setInitiatorName(initiatorName);
        instance.setStatus(1); // 1-运行中
        instance.setVariables(variables.toString());
        instance.setStartTime(LocalDateTime.now());
        instance.setCreateTime(LocalDateTime.now());
        
        save(instance);
        return processInstanceId;
    }

    @Override
    public List<ProcessInstance> getInstancesByTenantId(Long tenantId) {
        LambdaQueryWrapper<ProcessInstance> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProcessInstance::getTenantId, tenantId);
        queryWrapper.orderByDesc(ProcessInstance::getCreateTime);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<ProcessInstance> getInstancesByUserId(Long userId) {
        LambdaQueryWrapper<ProcessInstance> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProcessInstance::getInitiatorId, userId);
        queryWrapper.orderByDesc(ProcessInstance::getCreateTime);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public ProcessInstance getInstanceById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public ProcessInstance getInstanceByProcessInstanceId(String processInstanceId) {
        LambdaQueryWrapper<ProcessInstance> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProcessInstance::getProcessInstanceId, processInstanceId);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public boolean terminateProcess(String processInstanceId) {
        // 实际项目中应该使用Flowable引擎终止流程
        // 这里只是示例，实际应该调用Flowable的API
        ProcessInstance instance = getInstanceByProcessInstanceId(processInstanceId);
        if (instance != null) {
            instance.setStatus(3); // 3-已终止
            instance.setEndTime(LocalDateTime.now());
            return updateById(instance);
        }
        return false;
    }

    @Override
    public boolean suspendProcess(String processInstanceId) {
        // 实际项目中应该使用Flowable引擎挂起流程
        // 这里只是示例，实际应该调用Flowable的API
        ProcessInstance instance = getInstanceByProcessInstanceId(processInstanceId);
        if (instance != null) {
            instance.setStatus(4); // 4-已挂起
            return updateById(instance);
        }
        return false;
    }

    @Override
    public boolean activateProcess(String processInstanceId) {
        // 实际项目中应该使用Flowable引擎激活流程
        // 这里只是示例，实际应该调用Flowable的API
        ProcessInstance instance = getInstanceByProcessInstanceId(processInstanceId);
        if (instance != null) {
            instance.setStatus(1); // 1-运行中
            return updateById(instance);
        }
        return false;
    }
}