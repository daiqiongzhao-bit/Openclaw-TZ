package com.hr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hr.dao.ProcessTaskDao;
import com.hr.entity.ProcessTask;
import com.hr.service.ProcessTaskService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ProcessTaskServiceImpl extends ServiceImpl<ProcessTaskDao, ProcessTask> implements ProcessTaskService {

    @Override
    public List<ProcessTask> getTasksByAssigneeId(Long assigneeId) {
        LambdaQueryWrapper<ProcessTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProcessTask::getAssigneeId, assigneeId);
        queryWrapper.eq(ProcessTask::getStatus, 1); // 1-待处理
        queryWrapper.orderByDesc(ProcessTask::getCreateTime);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<ProcessTask> getTasksByProcessInstanceId(String processInstanceId) {
        LambdaQueryWrapper<ProcessTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProcessTask::getProcessInstanceId, processInstanceId);
        queryWrapper.orderByAsc(ProcessTask::getCreateTime);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public ProcessTask getTaskById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public ProcessTask getTaskByTaskId(String taskId) {
        LambdaQueryWrapper<ProcessTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProcessTask::getTaskId, taskId);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public boolean claimTask(String taskId, Long assigneeId) {
        // 实际项目中应该使用Flowable引擎签收任务
        // 这里只是示例，实际应该调用Flowable的API
        ProcessTask task = getTaskByTaskId(taskId);
        if (task != null) {
            task.setAssigneeId(assigneeId);
            task.setAssigneeName("用户" + assigneeId); // 实际应该从用户服务中获取
            task.setStatus(2); // 2-处理中
            task.setClaimTime(LocalDateTime.now());
            return updateById(task);
        }
        return false;
    }

    @Override
    public boolean completeTask(String taskId, Map<String, Object> variables, String comment) {
        // 实际项目中应该使用Flowable引擎完成任务
        // 这里只是示例，实际应该调用Flowable的API
        ProcessTask task = getTaskByTaskId(taskId);
        if (task != null) {
            task.setStatus(3); // 3-已完成
            task.setVariables(variables.toString());
            task.setComment(comment);
            task.setCompleteTime(LocalDateTime.now());
            return updateById(task);
        }
        return false;
    }

    @Override
    public boolean delegateTask(String taskId, Long delegateId) {
        // 实际项目中应该使用Flowable引擎委派任务
        // 这里只是示例，实际应该调用Flowable的API
        ProcessTask task = getTaskByTaskId(taskId);
        if (task != null) {
            task.setAssigneeId(delegateId);
            task.setAssigneeName("用户" + delegateId); // 实际应该从用户服务中获取
            task.setStatus(1); // 1-待处理
            return updateById(task);
        }
        return false;
    }

    @Override
    public boolean resolveTask(String taskId, Map<String, Object> variables) {
        // 实际项目中应该使用Flowable引擎解决委派任务
        // 这里只是示例，实际应该调用Flowable的API
        ProcessTask task = getTaskByTaskId(taskId);
        if (task != null) {
            task.setStatus(3); // 3-已完成
            task.setVariables(variables.toString());
            task.setCompleteTime(LocalDateTime.now());
            return updateById(task);
        }
        return false;
    }
}