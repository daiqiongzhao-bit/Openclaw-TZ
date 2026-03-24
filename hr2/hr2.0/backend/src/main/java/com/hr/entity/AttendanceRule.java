package com.hr.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("hr_attendance_rule")
public class AttendanceRule implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;
    private Long tenantId;
    private String ruleName;
    private String ruleCode;
    private String workStartTime;
    private String workEndTime;
    private String noonStart;
    private String noonEnd;
    private Integer lateMinutes;
    private Integer earlyLeaveMinutes;
    private Integer absentMinutes;
    private String restDays;
    private Integer status;
    private String description;
    private Long createBy;
    private Long updateBy;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer delFlag;
}