package com.hr.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("hr_salary_calculation")
public class SalaryCalculation implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;
    private Long tenantId;
    private Long userId;
    private String userName;
    private Long deptId;
    private String deptName;
    private Long structureId;
    private String structureName;
    private LocalDate salaryMonth;
    private Long basicSalary;
    private Long allowances;
    private Long overtimePay;
    private Long bonus;
    private Long deductions;
    private Long tax;
    private Long netSalary;
    private Integer status;
    private String remark;
    private LocalDateTime calculateTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}