package com.hr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hr.dao.SalaryCalculationDao;
import com.hr.entity.SalaryCalculation;
import com.hr.service.SalaryCalculationService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SalaryCalculationServiceImpl extends ServiceImpl<SalaryCalculationDao, SalaryCalculation> implements SalaryCalculationService {

    @Override
    public boolean calculateSalary(Long userId, LocalDate salaryMonth) {
        // 实际项目中应该根据薪资结构、考勤数据、绩效数据等进行薪资核算
        // 这里只是示例，实际应该调用相关服务获取数据
        SalaryCalculation calculation = new SalaryCalculation();
        calculation.setTenantId(1L); // 假设当前租户ID为1
        calculation.setUserId(userId);
        calculation.setUserName("用户" + userId); // 实际应该从用户服务中获取
        calculation.setDeptId(1L); // 实际应该从用户服务中获取
        calculation.setDeptName("技术部"); // 实际应该从用户服务中获取
        calculation.setStructureId(1L); // 实际应该从用户服务中获取
        calculation.setStructureName("标准薪资结构"); // 实际应该从薪资结构服务中获取
        calculation.setSalaryMonth(salaryMonth);
        calculation.setBasicSalary(10000L); // 实际应该根据薪资结构计算
        calculation.setAllowances(2000L); // 实际应该根据薪资结构计算
        calculation.setOvertimePay(1000L); // 实际应该根据考勤数据计算
        calculation.setBonus(3000L); // 实际应该根据绩效数据计算
        calculation.setDeductions(500L); // 实际应该根据考勤数据计算
        calculation.setTax(500L); // 实际应该根据税率计算
        calculation.setNetSalary(15000L); // 实际应该根据以上数据计算
        calculation.setStatus(1); // 1-已核算
        calculation.setCalculateTime(LocalDateTime.now());
        calculation.setCreateTime(LocalDateTime.now());
        calculation.setUpdateTime(LocalDateTime.now());
        return save(calculation);
    }

    @Override
    public boolean batchCalculateSalary(List<Long> userIds, LocalDate salaryMonth) {
        for (Long userId : userIds) {
            calculateSalary(userId, salaryMonth);
        }
        return true;
    }

    @Override
    public List<SalaryCalculation> getCalculationsByUserId(Long userId) {
        LambdaQueryWrapper<SalaryCalculation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SalaryCalculation::getUserId, userId);
        queryWrapper.orderByDesc(SalaryCalculation::getSalaryMonth);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<SalaryCalculation> getCalculationsByDeptId(Long deptId) {
        LambdaQueryWrapper<SalaryCalculation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SalaryCalculation::getDeptId, deptId);
        queryWrapper.orderByDesc(SalaryCalculation::getSalaryMonth);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<SalaryCalculation> getCalculationsByMonth(LocalDate salaryMonth) {
        LambdaQueryWrapper<SalaryCalculation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SalaryCalculation::getSalaryMonth, salaryMonth);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public SalaryCalculation getCalculationById(Long id) {
        return baseMapper.selectById(id);
    }
}