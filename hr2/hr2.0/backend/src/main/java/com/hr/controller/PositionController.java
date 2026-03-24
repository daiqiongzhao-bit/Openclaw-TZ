package com.hr.controller;

import com.hr.entity.Position;
import com.hr.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/position")
public class PositionController {

    @Autowired
    private PositionService positionService;

    @GetMapping("/dept/{deptId}")
    public List<Position> getPositionsByDeptId(@PathVariable Long deptId) {
        return positionService.getPositionsByDeptId(deptId);
    }

    @GetMapping("/tenant/{tenantId}")
    public List<Position> getPositionsByTenantId(@PathVariable Long tenantId) {
        return positionService.getPositionsByTenantId(tenantId);
    }

    @GetMapping("/{id}")
    public Position getPositionById(@PathVariable Long id) {
        return positionService.getPositionById(id);
    }

    @PostMapping
    public boolean addPosition(@RequestBody Position position) {
        return positionService.addPosition(position);
    }

    @PutMapping
    public boolean updatePosition(@RequestBody Position position) {
        return positionService.updatePosition(position);
    }

    @DeleteMapping("/{id}")
    public boolean deletePosition(@PathVariable Long id) {
        return positionService.deletePosition(id);
    }
}