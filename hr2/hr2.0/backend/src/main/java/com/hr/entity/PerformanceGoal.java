package com.hr.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("t_performance_goal")
public class PerformanceGoal {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long tenantId;
    private Long appraisalId;
    private Long employeeId;
    private String goalContent;
    private String weight;
    private Double score;
    private String status;
    private String feedback;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Long createUser;
    private Long updateUser;
    private Integer isDeleted;
}