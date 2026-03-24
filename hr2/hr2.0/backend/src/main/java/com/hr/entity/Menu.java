package com.hr.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@TableName("sys_menu")
public class Menu implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;
    private Long parentId;
    private String menuName;
    private String path;
    private String component;
    private String icon;
    private Integer type;
    private Integer sort;
    private String permission;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer delFlag;
    
    // 非数据库字段，用于构建菜单树
    private transient List<Menu> children;
}
