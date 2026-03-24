package com.hr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hr.dao.SocialSecurityBillDao;
import com.hr.entity.SocialSecurityBill;
import com.hr.service.SocialSecurityBillService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class SocialSecurityBillServiceImpl extends ServiceImpl<SocialSecurityBillDao, SocialSecurityBill> implements SocialSecurityBillService {

    @Override
    public boolean createBill(SocialSecurityBill bill) {
        bill.setTenantId(1L); // 假设当前租户ID为1
        bill.setBillNumber("SSB" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        bill.setStatus(1); // 1-待核对
        bill.setCreateTime(LocalDateTime.now());
        bill.setUpdateTime(LocalDateTime.now());
        return save(bill);
    }

    @Override
    public List<SocialSecurityBill> getBillsByTenantId(Long tenantId) {
        LambdaQueryWrapper<SocialSecurityBill> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SocialSecurityBill::getTenantId, tenantId);
        queryWrapper.orderByDesc(SocialSecurityBill::getBillMonth);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<SocialSecurityBill> getBillsByMonth(LocalDate billMonth) {
        LambdaQueryWrapper<SocialSecurityBill> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SocialSecurityBill::getBillMonth, billMonth);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<SocialSecurityBill> getBillsByDateRange(LocalDate startDate, LocalDate endDate) {
        LambdaQueryWrapper<SocialSecurityBill> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.between(SocialSecurityBill::getBillMonth, startDate, endDate);
        queryWrapper.orderByDesc(SocialSecurityBill::getBillMonth);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public SocialSecurityBill getBillById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public boolean verifyBill(Long id, Integer status) {
        SocialSecurityBill bill = baseMapper.selectById(id);
        if (bill != null) {
            bill.setStatus(status); // 2-已核对
            bill.setUpdateTime(LocalDateTime.now());
            return updateById(bill);
        }
        return false;
    }

    @Override
    public boolean rejectBill(Long id) {
        SocialSecurityBill bill = baseMapper.selectById(id);
        if (bill != null) {
            bill.setStatus(3); // 3-已拒绝
            bill.setUpdateTime(LocalDateTime.now());
            return updateById(bill);
        }
        return false;
    }
}