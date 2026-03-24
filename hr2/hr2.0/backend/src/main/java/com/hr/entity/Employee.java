package com.hr.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("t_employee")
public class Employee {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long tenantId;
    private String employeeId;
    private String name;
    private String gender;
    private LocalDate birthday;
    private String idCard;
    private String phone;
    private String email;
    private Long departmentId;
    private Long positionId;
    private Long gradeId;
    private String entryDate;
    private String probationEndDate;
    private String regularDate;
    private String leaveDate;
    private String status;
    private String address;
    private String emergencyContact;
    private String emergencyPhone;
    private String education;
    private String major;
    private String school;
    private String workExperience;
    private String skills;
    private String bankAccount;
    private String bankName;
    private String socialSecurityNumber;
    private String housingFundNumber;
    private String taxNumber;
    private String password;
    private String avatar;
    private String remark;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Long createUser;
    private Long updateUser;
    private Integer isDeleted;
}