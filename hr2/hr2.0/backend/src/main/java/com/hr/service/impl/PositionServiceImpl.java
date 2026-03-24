package com.hr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hr.dao.PositionDao;
import com.hr.entity.Position;
import com.hr.service.PositionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionServiceImpl extends ServiceImpl<PositionDao, Position> implements PositionService {

    @Override
    public List<Position> getPositionsByDeptId(Long deptId) {
        LambdaQueryWrapper<Position> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Position::getDeptId, deptId);
        queryWrapper.eq(Position::getStatus, 1);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<Position> getPositionsByTenantId(Long tenantId) {
        LambdaQueryWrapper<Position> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Position::getTenantId, tenantId);
        queryWrapper.eq(Position::getStatus, 1);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public Position getPositionById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public boolean addPosition(Position position) {
        return save(position);
    }

    @Override
    public boolean updatePosition(Position position) {
        return updateById(position);
    }

    @Override
    public boolean deletePosition(Long id) {
        return removeById(id);
    }
}