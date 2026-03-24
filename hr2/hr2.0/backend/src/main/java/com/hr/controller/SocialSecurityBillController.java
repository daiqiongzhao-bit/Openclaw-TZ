package com.hr.controller;

import com.hr.entity.SocialSecurityBill;
import com.hr.service.SocialSecurityBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/social/bill")
public class SocialSecurityBillController {

    @Autowired
    private SocialSecurityBillService socialSecurityBillService;

    @PostMapping
    public boolean createBill(@RequestBody SocialSecurityBill bill) {
        return socialSecurityBillService.createBill(bill);
    }

    @GetMapping("/tenant/{tenantId}")
    public List<SocialSecurityBill> getBillsByTenantId(@PathVariable Long tenantId) {
        return socialSecurityBillService.getBillsByTenantId(tenantId);
    }

    @GetMapping("/month/{billMonth}")
    public List<SocialSecurityBill> getBillsByMonth(@PathVariable String billMonth) {
        LocalDate localBillMonth = LocalDate.parse(billMonth);
        return socialSecurityBillService.getBillsByMonth(localBillMonth);
    }

    @GetMapping("/range")
    public List<SocialSecurityBill> getBillsByDateRange(@RequestParam String startDate, @RequestParam String endDate) {
        LocalDate localStartDate = LocalDate.parse(startDate);
        LocalDate localEndDate = LocalDate.parse(endDate);
        return socialSecurityBillService.getBillsByDateRange(localStartDate, localEndDate);
    }

    @GetMapping("/{id}")
    public SocialSecurityBill getBillById(@PathVariable Long id) {
        return socialSecurityBillService.getBillById(id);
    }

    @PostMapping("/verify/{id}")
    public boolean verifyBill(@PathVariable Long id, @RequestParam Integer status) {
        return socialSecurityBillService.verifyBill(id, status);
    }

    @PostMapping("/reject/{id}")
    public boolean rejectBill(@PathVariable Long id) {
        return socialSecurityBillService.rejectBill(id);
    }
}