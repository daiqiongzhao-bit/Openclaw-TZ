package com.hr.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("t_training_plan")
public class TrainingPlan {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long tenantId;
    private String planName;
    private String planCode;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private String targetAudience;
    private Integer estimatedParticipants;
    private String budget;
    private String objectives;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Long createUser;
    private Long updateUser;
    private Integer isDeleted;
}