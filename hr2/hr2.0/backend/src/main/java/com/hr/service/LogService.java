package com.hr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hr.entity.LoginLog;
import com.hr.entity.OperationLog;
import com.hr.entity.SystemLog;

import java.util.List;
import java.util.Map;

public interface LogService {
    // 登录日志
    void saveLoginLog(LoginLog loginLog);
    List<LoginLog> getLoginLogs(Map<String, Object> params);
    
    // 操作日志
    void saveOperationLog(OperationLog operationLog);
    List<OperationLog> getOperationLogs(Map<String, Object> params);
    
    // 系统日志
    void saveSystemLog(SystemLog systemLog);
    List<SystemLog> getSystemLogs(Map<String, Object> params);
}