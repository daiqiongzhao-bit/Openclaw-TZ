package com.hr.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("hr_social_security_adjustment")
public class SocialSecurityAdjustment implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;
    private Long tenantId;
    private Long userId;
    private String userName;
    private Long accountId;
    private Long oldSocialSecurityBase;
    private Long newSocialSecurityBase;
    private Long oldHousingFundBase;
    private Long newHousingFundBase;
    private LocalDate effectiveDate;
    private String reason;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}