package com.hr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hr.entity.SalaryItem;

import java.util.List;

public interface SalaryItemService extends IService<SalaryItem> {
    List<SalaryItem> getItemsByStructureId(Long structureId);
    List<SalaryItem> getItemsByTenantId(Long tenantId);
    SalaryItem getItemById(Long id);
    boolean addItem(SalaryItem item);
    boolean updateItem(SalaryItem item);
    boolean deleteItem(Long id);
}