package com.hr.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("t_performance_appraisal")
public class PerformanceAppraisal {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long tenantId;
    private Long templateId;
    private Long employeeId;
    private Long departmentId;
    private String cycle;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double totalScore;
    private String level;
    private String status;
    private String evaluator;
    private String feedback;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Long createUser;
    private Long updateUser;
    private Integer isDeleted;
}