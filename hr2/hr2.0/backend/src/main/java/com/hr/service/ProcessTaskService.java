package com.hr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hr.entity.ProcessTask;

import java.util.List;
import java.util.Map;

public interface ProcessTaskService extends IService<ProcessTask> {
    List<ProcessTask> getTasksByAssigneeId(Long assigneeId);
    List<ProcessTask> getTasksByProcessInstanceId(String processInstanceId);
    ProcessTask getTaskById(Long id);
    ProcessTask getTaskByTaskId(String taskId);
    boolean claimTask(String taskId, Long assigneeId);
    boolean completeTask(String taskId, Map<String, Object> variables, String comment);
    boolean delegateTask(String taskId, Long delegateId);
    boolean resolveTask(String taskId, Map<String, Object> variables);
}