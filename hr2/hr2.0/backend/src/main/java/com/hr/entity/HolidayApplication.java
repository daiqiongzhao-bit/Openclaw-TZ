package com.hr.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("hr_holiday_application")
public class HolidayApplication implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;
    private Long tenantId;
    private Long userId;
    private String userName;
    private Long deptId;
    private String deptName;
    private Long holidayTypeId;
    private String holidayTypeName;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer days;
    private String reason;
    private String attachments;
    private Long processInstanceId;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}