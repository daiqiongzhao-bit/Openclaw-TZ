package com.hr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hr.dao.TenantDao;
import com.hr.entity.Tenant;
import com.hr.service.TenantService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TenantServiceImpl extends ServiceImpl<TenantDao, Tenant> implements TenantService {

    @Override
    public List<Tenant> getAllTenants() {
        LambdaQueryWrapper<Tenant> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Tenant::getDelFlag, 0);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public boolean saveTenant(Tenant tenant) {
        return save(tenant);
    }

    @Override
    public boolean updateTenant(Tenant tenant) {
        return updateById(tenant);
    }

    @Override
    public boolean deleteTenant(Long id) {
        // 逻辑删除
        Tenant tenant = new Tenant();
        tenant.setId(id);
        tenant.setDelFlag(1);
        return updateById(tenant);
    }

    @Override
    public boolean enableTenant(Long id) {
        Tenant tenant = new Tenant();
        tenant.setId(id);
        tenant.setStatus(1);
        return updateById(tenant);
    }

    @Override
    public boolean disableTenant(Long id) {
        Tenant tenant = new Tenant();
        tenant.setId(id);
        tenant.setStatus(0);
        return updateById(tenant);
    }

    @Override
    public Tenant getTenantByCode(String tenantCode) {
        LambdaQueryWrapper<Tenant> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Tenant::getTenantCode, tenantCode);
        wrapper.eq(Tenant::getDelFlag, 0);
        return baseMapper.selectOne(wrapper);
    }
}
