package com.hr.controller;

import com.hr.entity.DataAlert;
import com.hr.service.DataAlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bi/alert")
public class DataAlertController {
    @Autowired
    private DataAlertService dataAlertService;

    @PostMapping
    public DataAlert addDataAlert(@RequestBody DataAlert dataAlert) {
        return dataAlertService.addDataAlert(dataAlert);
    }

    @PutMapping
    public DataAlert updateDataAlert(@RequestBody DataAlert dataAlert) {
        return dataAlertService.updateDataAlert(dataAlert);
    }

    @DeleteMapping("/{id}")
    public void deleteDataAlert(@PathVariable Long id) {
        dataAlertService.deleteDataAlert(id);
    }

    @GetMapping("/{id}")
    public DataAlert getDataAlertById(@PathVariable Long id) {
        return dataAlertService.getDataAlertById(id);
    }

    @GetMapping("/tenant/{tenantId}")
    public List<DataAlert> getDataAlertsByTenantId(@PathVariable Long tenantId) {
        return dataAlertService.getDataAlertsByTenantId(tenantId);
    }

    @GetMapping("/type/{type}")
    public List<DataAlert> getDataAlertsByType(@PathVariable String type) {
        return dataAlertService.getDataAlertsByType(type);
    }

    @GetMapping("/active/{tenantId}")
    public List<DataAlert> getActiveDataAlerts(@PathVariable Long tenantId) {
        return dataAlertService.getActiveDataAlerts(tenantId);
    }

    @PostMapping("/trigger")
    public void triggerDataAlerts() {
        dataAlertService.triggerDataAlerts();
    }
}