package com.hr.controller;

import com.hr.entity.ProcessInstance;
import com.hr.service.ProcessInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/process/instance")
public class ProcessInstanceController {

    @Autowired
    private ProcessInstanceService processInstanceService;

    @PostMapping("/start")
    public String startProcess(@RequestParam Long templateId, @RequestBody Map<String, Object> variables, @RequestParam Long initiatorId, @RequestParam String initiatorName) {
        return processInstanceService.startProcess(templateId, variables, initiatorId, initiatorName);
    }

    @GetMapping("/tenant/{tenantId}")
    public List<ProcessInstance> getInstancesByTenantId(@PathVariable Long tenantId) {
        return processInstanceService.getInstancesByTenantId(tenantId);
    }

    @GetMapping("/user/{userId}")
    public List<ProcessInstance> getInstancesByUserId(@PathVariable Long userId) {
        return processInstanceService.getInstancesByUserId(userId);
    }

    @GetMapping("/{id}")
    public ProcessInstance getInstanceById(@PathVariable Long id) {
        return processInstanceService.getInstanceById(id);
    }

    @GetMapping("/process/{processInstanceId}")
    public ProcessInstance getInstanceByProcessInstanceId(@PathVariable String processInstanceId) {
        return processInstanceService.getInstanceByProcessInstanceId(processInstanceId);
    }

    @PostMapping("/terminate/{processInstanceId}")
    public boolean terminateProcess(@PathVariable String processInstanceId) {
        return processInstanceService.terminateProcess(processInstanceId);
    }

    @PostMapping("/suspend/{processInstanceId}")
    public boolean suspendProcess(@PathVariable String processInstanceId) {
        return processInstanceService.suspendProcess(processInstanceId);
    }

    @PostMapping("/activate/{processInstanceId}")
    public boolean activateProcess(@PathVariable String processInstanceId) {
        return processInstanceService.activateProcess(processInstanceId);
    }
}