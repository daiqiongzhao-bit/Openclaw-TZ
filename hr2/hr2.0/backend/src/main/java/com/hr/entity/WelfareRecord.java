package com.hr.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("hr_welfare_record")
public class WelfareRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;
    private Long tenantId;
    private Long userId;
    private String userName;
    private Long deptId;
    private String deptName;
    private Long welfareTypeId;
    private String welfareTypeName;
    private String welfareName;
    private String welfareValue;
    private LocalDate issueDate;
    private String remark;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}