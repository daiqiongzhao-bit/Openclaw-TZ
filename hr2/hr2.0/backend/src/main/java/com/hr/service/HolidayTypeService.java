package com.hr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hr.entity.HolidayType;

import java.util.List;

public interface HolidayTypeService extends IService<HolidayType> {
    List<HolidayType> getTypesByTenantId(Long tenantId);
    HolidayType getTypeById(Long id);
    boolean addType(HolidayType type);
    boolean updateType(HolidayType type);
    boolean deleteType(Long id);
}