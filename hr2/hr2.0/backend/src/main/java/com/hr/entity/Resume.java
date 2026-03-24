package com.hr.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("hr_resume")
public class Resume implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;
    private Long tenantId;
    private String resumeNumber;
    private String name;
    private String gender;
    private LocalDate birthday;
    private String phone;
    private String email;
    private String education;
    private String major;
    private String school;
    private String workExperience;
    private Long expectedSalary;
    private String positionApplied;
    private String resumeUrl;
    private Integer status;
    private String remark;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}