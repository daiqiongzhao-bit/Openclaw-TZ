package com.hr.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("t_employee_care")
public class EmployeeCare {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long tenantId;
    private Long employeeId;
    private String type;
    private String title;
    private String content;
    private LocalDate careDate;
    private String status;
    private String caregiver;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Long createUser;
    private Long updateUser;
    private Integer isDeleted;
}