package com.hr.controller;

import com.hr.entity.Role;
import com.hr.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/system/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    public Map<String, Object> list() {
        List<Role> roles = roleService.getAllRoles();
        return Map.of("code", 200, "data", roles, "message", "获取角色列表成功");
    }

    @PostMapping("/save")
    public Map<String, Object> save(@RequestBody Role role) {
        boolean success = roleService.saveRole(role);
        return Map.of("code", success ? 200 : 500, "message", success ? "保存成功" : "保存失败");
    }

    @PutMapping("/update")
    public Map<String, Object> update(@RequestBody Role role) {
        boolean success = roleService.updateRole(role);
        return Map.of("code", success ? 200 : 500, "message", success ? "更新成功" : "更新失败");
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, Object> delete(@PathVariable Long id) {
        boolean success = roleService.deleteRole(id);
        return Map.of("code", success ? 200 : 500, "message", success ? "删除成功" : "删除失败");
    }

    @GetMapping("/get/{id}")
    public Map<String, Object> get(@PathVariable Long id) {
        Role role = roleService.getById(id);
        return Map.of("code", 200, "data", role, "message", "获取角色信息成功");
    }

    @PostMapping("/assignMenus")
    public Map<String, Object> assignMenus(@RequestBody Map<String, Object> params) {
        Long roleId = Long.valueOf(params.get("roleId").toString());
        List<Long> menuIds = (List<Long>) params.get("menuIds");
        boolean success = roleService.assignMenus(roleId, menuIds);
        return Map.of("code", success ? 200 : 500, "message", success ? "分配菜单成功" : "分配菜单失败");
    }
}
