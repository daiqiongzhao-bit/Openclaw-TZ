package com.hr.controller;

import com.hr.entity.SysParam;
import com.hr.service.SysParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/system/param")
public class SysParamController {

    @Autowired
    private SysParamService sysParamService;

    @GetMapping("/list")
    public Map<String, Object> list() {
        List<SysParam> params = sysParamService.getAllParams();
        return Map.of("code", 200, "data", params, "message", "获取系统参数列表成功");
    }

    @PostMapping("/save")
    public Map<String, Object> save(@RequestBody SysParam param) {
        boolean success = sysParamService.saveParam(param);
        return Map.of("code", success ? 200 : 500, "message", success ? "保存成功" : "保存失败");
    }

    @PutMapping("/update")
    public Map<String, Object> update(@RequestBody SysParam param) {
        boolean success = sysParamService.updateParam(param);
        return Map.of("code", success ? 200 : 500, "message", success ? "更新成功" : "更新失败");
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, Object> delete(@PathVariable Long id) {
        boolean success = sysParamService.deleteParam(id);
        return Map.of("code", success ? 200 : 500, "message", success ? "删除成功" : "删除失败");
    }

    @GetMapping("/get/{id}")
    public Map<String, Object> get(@PathVariable Long id) {
        SysParam param = sysParamService.getById(id);
        return Map.of("code", 200, "data", param, "message", "获取系统参数信息成功");
    }

    @GetMapping("/getByKey/{paramKey}")
    public Map<String, Object> getByKey(@PathVariable String paramKey) {
        SysParam param = sysParamService.getParamByKey(paramKey);
        return Map.of("code", 200, "data", param, "message", "获取系统参数信息成功");
    }
}
