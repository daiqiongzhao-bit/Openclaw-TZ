package com.hr.controller;

import com.hr.entity.Menu;
import com.hr.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/system/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    public Map<String, Object> list() {
        List<Menu> menus = menuService.getAllMenus();
        return Map.of("code", 200, "data", menus, "message", "获取菜单列表成功");
    }

    @GetMapping("/tree")
    public Map<String, Object> tree() {
        List<Menu> menuTree = menuService.getMenuTree();
        return Map.of("code", 200, "data", menuTree, "message", "获取菜单树成功");
    }

    @PostMapping("/save")
    public Map<String, Object> save(@RequestBody Menu menu) {
        boolean success = menuService.saveMenu(menu);
        return Map.of("code", success ? 200 : 500, "message", success ? "保存成功" : "保存失败");
    }

    @PutMapping("/update")
    public Map<String, Object> update(@RequestBody Menu menu) {
        boolean success = menuService.updateMenu(menu);
        return Map.of("code", success ? 200 : 500, "message", success ? "更新成功" : "更新失败");
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, Object> delete(@PathVariable Long id) {
        boolean success = menuService.deleteMenu(id);
        return Map.of("code", success ? 200 : 500, "message", success ? "删除成功" : "删除失败");
    }

    @GetMapping("/get/{id}")
    public Map<String, Object> get(@PathVariable Long id) {
        Menu menu = menuService.getById(id);
        return Map.of("code", 200, "data", menu, "message", "获取菜单信息成功");
    }

    @GetMapping("/getByRoleId/{roleId}")
    public Map<String, Object> getByRoleId(@PathVariable Long roleId) {
        List<Menu> menus = menuService.getMenusByRoleId(roleId);
        return Map.of("code", 200, "data", menus, "message", "获取角色菜单成功");
    }
}
