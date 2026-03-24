package com.hr.controller;

import com.hr.entity.ProcessTask;
import com.hr.service.ProcessTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/process/task")
public class ProcessTaskController {

    @Autowired
    private ProcessTaskService processTaskService;

    @GetMapping("/assignee/{assigneeId}")
    public List<ProcessTask> getTasksByAssigneeId(@PathVariable Long assigneeId) {
        return processTaskService.getTasksByAssigneeId(assigneeId);
    }

    @GetMapping("/process/{processInstanceId}")
    public List<ProcessTask> getTasksByProcessInstanceId(@PathVariable String processInstanceId) {
        return processTaskService.getTasksByProcessInstanceId(processInstanceId);
    }

    @GetMapping("/{id}")
    public ProcessTask getTaskById(@PathVariable Long id) {
        return processTaskService.getTaskById(id);
    }

    @GetMapping("/task/{taskId}")
    public ProcessTask getTaskByTaskId(@PathVariable String taskId) {
        return processTaskService.getTaskByTaskId(taskId);
    }

    @PostMapping("/claim/{taskId}")
    public boolean claimTask(@PathVariable String taskId, @RequestParam Long assigneeId) {
        return processTaskService.claimTask(taskId, assigneeId);
    }

    @PostMapping("/complete/{taskId}")
    public boolean completeTask(@PathVariable String taskId, @RequestBody Map<String, Object> variables, @RequestParam String comment) {
        return processTaskService.completeTask(taskId, variables, comment);
    }

    @PostMapping("/delegate/{taskId}")
    public boolean delegateTask(@PathVariable String taskId, @RequestParam Long delegateId) {
        return processTaskService.delegateTask(taskId, delegateId);
    }

    @PostMapping("/resolve/{taskId}")
    public boolean resolveTask(@PathVariable String taskId, @RequestBody Map<String, Object> variables) {
        return processTaskService.resolveTask(taskId, variables);
    }
}