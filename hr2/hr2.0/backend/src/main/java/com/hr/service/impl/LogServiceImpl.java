package com.hr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hr.dao.LoginLogDao;
import com.hr.dao.OperationLogDao;
import com.hr.dao.SystemLogDao;
import com.hr.entity.LoginLog;
import com.hr.entity.OperationLog;
import com.hr.entity.SystemLog;
import com.hr.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LoginLogDao loginLogDao;
    
    @Autowired
    private OperationLogDao operationLogDao;
    
    @Autowired
    private SystemLogDao systemLogDao;

    @Override
    public void saveLoginLog(LoginLog loginLog) {
        loginLogDao.insert(loginLog);
    }

    @Override
    public List<LoginLog> getLoginLogs(Map<String, Object> params) {
        LambdaQueryWrapper<LoginLog> queryWrapper = new LambdaQueryWrapper<>();
        // 根据参数构建查询条件
        if (params.containsKey("tenantId")) {
            queryWrapper.eq(LoginLog::getTenantId, params.get("tenantId"));
        }
        if (params.containsKey("userId")) {
            queryWrapper.eq(LoginLog::getUserId, params.get("userId"));
        }
        if (params.containsKey("status")) {
            queryWrapper.eq(LoginLog::getStatus, params.get("status"));
        }
        queryWrapper.orderByDesc(LoginLog::getLoginTime);
        return loginLogDao.selectList(queryWrapper);
    }

    @Override
    public void saveOperationLog(OperationLog operationLog) {
        operationLogDao.insert(operationLog);
    }

    @Override
    public List<OperationLog> getOperationLogs(Map<String, Object> params) {
        LambdaQueryWrapper<OperationLog> queryWrapper = new LambdaQueryWrapper<>();
        // 根据参数构建查询条件
        if (params.containsKey("tenantId")) {
            queryWrapper.eq(OperationLog::getTenantId, params.get("tenantId"));
        }
        if (params.containsKey("userId")) {
            queryWrapper.eq(OperationLog::getUserId, params.get("userId"));
        }
        if (params.containsKey("module")) {
            queryWrapper.eq(OperationLog::getModule, params.get("module"));
        }
        queryWrapper.orderByDesc(OperationLog::getCreateTime);
        return operationLogDao.selectList(queryWrapper);
    }

    @Override
    public void saveSystemLog(SystemLog systemLog) {
        systemLogDao.insert(systemLog);
    }

    @Override
    public List<SystemLog> getSystemLogs(Map<String, Object> params) {
        LambdaQueryWrapper<SystemLog> queryWrapper = new LambdaQueryWrapper<>();
        // 根据参数构建查询条件
        if (params.containsKey("level")) {
            queryWrapper.eq(SystemLog::getLevel, params.get("level"));
        }
        if (params.containsKey("module")) {
            queryWrapper.eq(SystemLog::getModule, params.get("module"));
        }
        queryWrapper.orderByDesc(SystemLog::getCreateTime);
        return systemLogDao.selectList(queryWrapper);
    }
}