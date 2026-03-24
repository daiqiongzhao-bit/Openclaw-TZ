package com.hr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hr.dao.SocialSecurityAccountDao;
import com.hr.entity.SocialSecurityAccount;
import com.hr.service.SocialSecurityAccountService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SocialSecurityAccountServiceImpl extends ServiceImpl<SocialSecurityAccountDao, SocialSecurityAccount> implements SocialSecurityAccountService {

    @Override
    public List<SocialSecurityAccount> getAccountsByTenantId(Long tenantId) {
        LambdaQueryWrapper<SocialSecurityAccount> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SocialSecurityAccount::getTenantId, tenantId);
        queryWrapper.eq(SocialSecurityAccount::getStatus, 1);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<SocialSecurityAccount> getAccountsByUserId(Long userId) {
        LambdaQueryWrapper<SocialSecurityAccount> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SocialSecurityAccount::getUserId, userId);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public SocialSecurityAccount getAccountById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public boolean addAccount(SocialSecurityAccount account) {
        account.setTenantId(1L); // 假设当前租户ID为1
        account.setStatus(1); // 1-正常
        account.setCreateTime(LocalDateTime.now());
        account.setUpdateTime(LocalDateTime.now());
        return save(account);
    }

    @Override
    public boolean updateAccount(SocialSecurityAccount account) {
        account.setUpdateTime(LocalDateTime.now());
        return updateById(account);
    }

    @Override
    public boolean deleteAccount(Long id) {
        SocialSecurityAccount account = baseMapper.selectById(id);
        if (account != null) {
            account.setStatus(0); // 0-禁用
            account.setUpdateTime(LocalDateTime.now());
            return updateById(account);
        }
        return false;
    }
}