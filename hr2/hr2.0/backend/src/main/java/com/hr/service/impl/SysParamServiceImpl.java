package com.hr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hr.dao.SysParamDao;
import com.hr.entity.SysParam;
import com.hr.service.SysParamService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysParamServiceImpl extends ServiceImpl<SysParamDao, SysParam> implements SysParamService {

    @Override
    public List<SysParam> getAllParams() {
        LambdaQueryWrapper<SysParam> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysParam::getDelFlag, 0);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public SysParam getParamByKey(String paramKey) {
        LambdaQueryWrapper<SysParam> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysParam::getParamKey, paramKey);
        wrapper.eq(SysParam::getDelFlag, 0);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public boolean saveParam(SysParam param) {
        return save(param);
    }

    @Override
    public boolean updateParam(SysParam param) {
        return updateById(param);
    }

    @Override
    public boolean deleteParam(Long id) {
        // 逻辑删除
        SysParam param = new SysParam();
        param.setId(id);
        param.setDelFlag(1);
        return updateById(param);
    }
}
