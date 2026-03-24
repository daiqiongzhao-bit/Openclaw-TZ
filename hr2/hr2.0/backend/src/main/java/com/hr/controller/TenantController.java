package com.hr.controller;

import com.hr.entity.Tenant;
import com.hr.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/system/tenant")
public class TenantController {

    @Autowired
    private TenantService tenantService;

    @GetMapping("/list")
    public Map<String, Object> list() {
        List<Tenant> tenants = tenantService.getAllTenants();
        return Map.of("code", 200, "data", tenants, "message", "获取租户列表成功");
    }

    @PostMapping("/save")
    public Map<String, Object> save(@RequestBody Tenant tenant) {
        boolean success = tenantService.saveTenant(tenant);
        return Map.of("code", success ? 200 : 500, "message", success ? "保存成功" : "保存失败");
    }

    @PutMapping("/update")
    public Map<String, Object> update(@RequestBody Tenant tenant) {
        boolean success = tenantService.updateTenant(tenant);
        return Map.of("code", success ? 200 : 500, "message", success ? "更新成功" : "更新失败");
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, Object> delete(@PathVariable Long id) {
        boolean success = tenantService.deleteTenant(id);
        return Map.of("code", success ? 200 : 500, "message", success ? "删除成功" : "删除失败");
    }

    @PutMapping("/enable/{id}")
    public Map<String, Object> enable(@PathVariable Long id) {
        boolean success = tenantService.enableTenant(id);
        return Map.of("code", success ? 200 : 500, "message", success ? "启用成功" : "启用失败");
    }

    @PutMapping("/disable/{id}")
    public Map<String, Object> disable(@PathVariable Long id) {
        boolean success = tenantService.disableTenant(id);
        return Map.of("code", success ? 200 : 500, "message", success ? "禁用成功" : "禁用失败");
    }

    @GetMapping("/get/{id}")
    public Map<String, Object> get(@PathVariable Long id) {
        Tenant tenant = tenantService.getById(id);
        return Map.of("code", 200, "data", tenant, "message", "获取租户信息成功");
    }

    @GetMapping("/getByCode/{tenantCode}")
    public Map<String, Object> getByCode(@PathVariable String tenantCode) {
        Tenant tenant = tenantService.getTenantByCode(tenantCode);
        return Map.of("code", 200, "data", tenant, "message", "获取租户信息成功");
    }
}
