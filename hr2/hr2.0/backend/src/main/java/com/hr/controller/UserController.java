package com.hr.controller;

import com.hr.entity.User;
import com.hr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/system/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public Map<String, Object> list() {
        List<User> users = userService.list();
        return Map.of("code", 200, "data", users, "message", "获取用户列表成功");
    }

    @PostMapping("/save")
    public Map<String, Object> save(@RequestBody User user) {
        boolean success = userService.saveUser(user);
        return Map.of("code", success ? 200 : 500, "message", success ? "保存成功" : "保存失败");
    }

    @PutMapping("/update")
    public Map<String, Object> update(@RequestBody User user) {
        boolean success = userService.updateUser(user);
        return Map.of("code", success ? 200 : 500, "message", success ? "更新成功" : "更新失败");
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, Object> delete(@PathVariable Long id) {
        boolean success = userService.deleteUser(id);
        return Map.of("code", success ? 200 : 500, "message", success ? "删除成功" : "删除失败");
    }

    @GetMapping("/get/{id}")
    public Map<String, Object> get(@PathVariable Long id) {
        User user = userService.getById(id);
        return Map.of("code", 200, "data", user, "message", "获取用户信息成功");
    }
}
