package com.hr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hr.entity.DataAlert;

import java.util.List;

public interface DataAlertService extends IService<DataAlert> {
    DataAlert addDataAlert(DataAlert dataAlert);
    DataAlert updateDataAlert(DataAlert dataAlert);
    void deleteDataAlert(Long id);
    DataAlert getDataAlertById(Long id);
    List<DataAlert> getDataAlertsByTenantId(Long tenantId);
    List<DataAlert> getDataAlertsByType(String type);
    List<DataAlert> getActiveDataAlerts(Long tenantId);
    void triggerDataAlerts();
}