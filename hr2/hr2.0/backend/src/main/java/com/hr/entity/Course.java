package com.hr.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("t_course")
public class Course {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long tenantId;
    private String courseName;
    private String courseCode;
    private String category;
    private String level;
    private String type;
    private String description;
    private String objectives;
    private Integer duration;
    private String durationUnit;
    private String content;
    private String materials;
    private String status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Long createUser;
    private Long updateUser;
    private Integer isDeleted;
}