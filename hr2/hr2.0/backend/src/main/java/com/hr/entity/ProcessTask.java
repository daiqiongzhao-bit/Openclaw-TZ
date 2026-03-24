package com.hr.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("hr_process_task")
public class ProcessTask implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;
    private Long tenantId;
    private String taskId;
    private String processInstanceId;
    private String taskName;
    private String taskDefinitionKey;
    private Long assigneeId;
    private String assigneeName;
    private Integer status;
    private String variables;
    private String comment;
    private LocalDateTime createTime;
    private LocalDateTime claimTime;
    private LocalDateTime completeTime;
}