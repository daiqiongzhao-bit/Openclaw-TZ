package com.hr.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("t_training_plan_detail")
public class TrainingPlanDetail {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long tenantId;
    private Long planId;
    private Long courseId;
    private Long instructorId;
    private LocalDate trainingDate;
    private String startTime;
    private String endTime;
    private String location;
    private Integer maxParticipants;
    private String status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Long createUser;
    private Long updateUser;
    private Integer isDeleted;
}