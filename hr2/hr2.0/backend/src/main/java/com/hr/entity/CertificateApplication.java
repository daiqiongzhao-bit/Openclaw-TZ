package com.hr.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("t_certificate_application")
public class CertificateApplication {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long tenantId;
    private Long employeeId;
    private String certificateType;
    private String purpose;
    private LocalDate applicationDate;
    private String status;
    private String approver;
    private String rejectionReason;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Long createUser;
    private Long updateUser;
    private Integer isDeleted;
}