package com.hr.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("hr_interview_record")
public class InterviewRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;
    private Long tenantId;
    private Long resumeId;
    private String candidateName;
    private String positionApplied;
    private Integer interviewRound;
    private LocalDate interviewDate;
    private String interviewer;
    private Integer score;
    private String evaluation;
    private Integer result;
    private String remark;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}