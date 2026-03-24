package com.hr.controller;

import com.hr.entity.SocialSecurityAccount;
import com.hr.service.SocialSecurityAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/social/account")
public class SocialSecurityAccountController {

    @Autowired
    private SocialSecurityAccountService socialSecurityAccountService;

    @GetMapping("/tenant/{tenantId}")
    public List<SocialSecurityAccount> getAccountsByTenantId(@PathVariable Long tenantId) {
        return socialSecurityAccountService.getAccountsByTenantId(tenantId);
    }

    @GetMapping("/user/{userId}")
    public List<SocialSecurityAccount> getAccountsByUserId(@PathVariable Long userId) {
        return socialSecurityAccountService.getAccountsByUserId(userId);
    }

    @GetMapping("/{id}")
    public SocialSecurityAccount getAccountById(@PathVariable Long id) {
        return socialSecurityAccountService.getAccountById(id);
    }

    @PostMapping
    public boolean addAccount(@RequestBody SocialSecurityAccount account) {
        return socialSecurityAccountService.addAccount(account);
    }

    @PutMapping
    public boolean updateAccount(@RequestBody SocialSecurityAccount account) {
        return socialSecurityAccountService.updateAccount(account);
    }

    @DeleteMapping("/{id}")
    public boolean deleteAccount(@PathVariable Long id) {
        return socialSecurityAccountService.deleteAccount(id);
    }
}