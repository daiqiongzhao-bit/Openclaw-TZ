package com.hr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hr.entity.Position;

import java.util.List;

public interface PositionService extends IService<Position> {
    List<Position> getPositionsByDeptId(Long deptId);
    List<Position> getPositionsByTenantId(Long tenantId);
    Position getPositionById(Long id);
    boolean addPosition(Position position);
    boolean updatePosition(Position position);
    boolean deletePosition(Long id);
}