package com.hr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hr.entity.SocialSecurityAccount;

import java.util.List;

public interface SocialSecurityAccountService extends IService<SocialSecurityAccount> {
    List<SocialSecurityAccount> getAccountsByTenantId(Long tenantId);
    List<SocialSecurityAccount> getAccountsByUserId(Long userId);
    SocialSecurityAccount getAccountById(Long id);
    boolean addAccount(SocialSecurityAccount account);
    boolean updateAccount(SocialSecurityAccount account);
    boolean deleteAccount(Long id);
}