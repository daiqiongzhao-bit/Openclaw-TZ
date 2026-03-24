package com.hr.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("hr_recruitment_requirement")
public class RecruitmentRequirement implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;
    private Long tenantId;
    private String reqNumber;
    private String positionName;
    private Long deptId;
    private String deptName;
    private Integer recruitNum;
    private Long expectedSalaryMin;
    private Long expectedSalaryMax;
    private String education;
    private String workExperience;
    private String jobDescription;
    private String requirements;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer status;
    private Long createBy;
    private String createByName;
    private Long processInstanceId;
    private String remark;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}