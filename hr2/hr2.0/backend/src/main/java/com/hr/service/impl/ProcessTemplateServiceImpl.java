package com.hr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hr.dao.ProcessTemplateDao;
import com.hr.entity.ProcessTemplate;
import com.hr.service.ProcessTemplateService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcessTemplateServiceImpl extends ServiceImpl<ProcessTemplateDao, ProcessTemplate> implements ProcessTemplateService {

    @Override
    public List<ProcessTemplate> getTemplatesByTenantId(Long tenantId) {
        LambdaQueryWrapper<ProcessTemplate> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProcessTemplate::getTenantId, tenantId);
        queryWrapper.eq(ProcessTemplate::getStatus, 1);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public ProcessTemplate getTemplateById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public boolean addTemplate(ProcessTemplate template) {
        return save(template);
    }

    @Override
    public boolean updateTemplate(ProcessTemplate template) {
        return updateById(template);
    }

    @Override
    public boolean deleteTemplate(Long id) {
        return removeById(id);
    }

    @Override
    public boolean deployTemplate(Long id) {
        // 实际项目中应该使用Flowable引擎部署流程
        // 这里只是示例，实际应该调用Flowable的API
        ProcessTemplate template = baseMapper.selectById(id);
        if (template != null) {
            template.setStatus(1);
            return updateById(template);
        }
        return false;
    }

    @Override
    public boolean undeployTemplate(Long id) {
        // 实际项目中应该使用Flowable引擎取消部署流程
        // 这里只是示例，实际应该调用Flowable的API
        ProcessTemplate template = baseMapper.selectById(id);
        if (template != null) {
            template.setStatus(0);
            return updateById(template);
        }
        return false;
    }
}