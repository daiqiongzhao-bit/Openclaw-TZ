package com.hr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hr.entity.WelfareType;

import java.util.List;

public interface WelfareTypeService extends IService<WelfareType> {
    List<WelfareType> getTypesByTenantId(Long tenantId);
    WelfareType getTypeById(Long id);
    boolean addType(WelfareType type);
    boolean updateType(WelfareType type);
    boolean deleteType(Long id);
}