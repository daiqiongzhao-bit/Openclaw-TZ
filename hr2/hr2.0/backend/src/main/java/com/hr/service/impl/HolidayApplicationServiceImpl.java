package com.hr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hr.dao.HolidayApplicationDao;
import com.hr.entity.HolidayApplication;
import com.hr.service.HolidayApplicationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HolidayApplicationServiceImpl extends ServiceImpl<HolidayApplicationDao, HolidayApplication> implements HolidayApplicationService {

    @Override
    public boolean submitApplication(HolidayApplication application) {
        application.setTenantId(1L); // 假设当前租户ID为1
        application.setStatus(1); // 1-待审批
        application.setCreateTime(LocalDateTime.now());
        application.setUpdateTime(LocalDateTime.now());
        return save(application);
    }

    @Override
    public List<HolidayApplication> getApplicationsByUserId(Long userId) {
        LambdaQueryWrapper<HolidayApplication> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(HolidayApplication::getUserId, userId);
        queryWrapper.orderByDesc(HolidayApplication::getCreateTime);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<HolidayApplication> getApplicationsByDeptId(Long deptId) {
        LambdaQueryWrapper<HolidayApplication> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(HolidayApplication::getDeptId, deptId);
        queryWrapper.orderByDesc(HolidayApplication::getCreateTime);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<HolidayApplication> getApplicationsByStatus(Integer status) {
        LambdaQueryWrapper<HolidayApplication> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(HolidayApplication::getStatus, status);
        queryWrapper.orderByDesc(HolidayApplication::getCreateTime);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public HolidayApplication getApplicationById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public boolean approveApplication(Long id, Integer status, String comment) {
        HolidayApplication application = baseMapper.selectById(id);
        if (application != null) {
            application.setStatus(status); // 2-已批准
            application.setUpdateTime(LocalDateTime.now());
            return updateById(application);
        }
        return false;
    }

    @Override
    public boolean rejectApplication(Long id, String comment) {
        HolidayApplication application = baseMapper.selectById(id);
        if (application != null) {
            application.setStatus(3); // 3-已拒绝
            application.setUpdateTime(LocalDateTime.now());
            return updateById(application);
        }
        return false;
    }
}