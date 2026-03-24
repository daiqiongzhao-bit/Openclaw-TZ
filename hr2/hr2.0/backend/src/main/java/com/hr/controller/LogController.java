package com.hr.controller;

import com.hr.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/log")
public class LogController {

    @Autowired
    private LogService logService;

    @GetMapping("/login")
    public Object getLoginLogs(@RequestParam Map<String, Object> params) {
        return logService.getLoginLogs(params);
    }

    @GetMapping("/operation")
    public Object getOperationLogs(@RequestParam Map<String, Object> params) {
        return logService.getOperationLogs(params);
    }

    @GetMapping("/system")
    public Object getSystemLogs(@RequestParam Map<String, Object> params) {
        return logService.getSystemLogs(params);
    }
}