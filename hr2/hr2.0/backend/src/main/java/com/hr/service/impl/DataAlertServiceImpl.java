package com.hr.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hr.dao.DataAlertDao;
import com.hr.entity.DataAlert;
import com.hr.service.DataAlertService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataAlertServiceImpl extends ServiceImpl<DataAlertDao, DataAlert> implements DataAlertService {
    @Override
    public DataAlert addDataAlert(DataAlert dataAlert) {
        dataAlert.setStatus("active");
        save(dataAlert);
        return dataAlert;
    }

    @Override
    public DataAlert updateDataAlert(DataAlert dataAlert) {
        updateById(dataAlert);
        return dataAlert;
    }

    @Override
    public void deleteDataAlert(Long id) {
        removeById(id);
    }

    @Override
    public DataAlert getDataAlertById(Long id) {
        return getById(id);
    }

    @Override
    public List<DataAlert> getDataAlertsByTenantId(Long tenantId) {
        return lambdaQuery().eq(DataAlert::getTenantId, tenantId).list();
    }

    @Override
    public List<DataAlert> getDataAlertsByType(String type) {
        return lambdaQuery().eq(DataAlert::getType, type).list();
    }

    @Override
    public List<DataAlert> getActiveDataAlerts(Long tenantId) {
        return lambdaQuery().eq(DataAlert::getTenantId, tenantId).eq(DataAlert::getStatus, "active").list();
    }

    @Override
    public void triggerDataAlerts() {
        // 实现数据预警触发逻辑
    }
}