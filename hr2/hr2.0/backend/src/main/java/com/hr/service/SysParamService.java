package com.hr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hr.entity.SysParam;

import java.util.List;

public interface SysParamService extends IService<SysParam> {
    List<SysParam> getAllParams();
    SysParam getParamByKey(String paramKey);
    boolean saveParam(SysParam param);
    boolean updateParam(SysParam param);
    boolean deleteParam(Long id);
}
