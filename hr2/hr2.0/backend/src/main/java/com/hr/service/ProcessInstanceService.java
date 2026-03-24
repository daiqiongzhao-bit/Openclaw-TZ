package com.hr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hr.entity.ProcessInstance;

import java.util.List;
import java.util.Map;

public interface ProcessInstanceService extends IService<ProcessInstance> {
    String startProcess(Long templateId, Map<String, Object> variables, Long initiatorId, String initiatorName);
    List<ProcessInstance> getInstancesByTenantId(Long tenantId);
    List<ProcessInstance> getInstancesByUserId(Long userId);
    ProcessInstance getInstanceById(Long id);
    ProcessInstance getInstanceByProcessInstanceId(String processInstanceId);
    boolean terminateProcess(String processInstanceId);
    boolean suspendProcess(String processInstanceId);
    boolean activateProcess(String processInstanceId);
}