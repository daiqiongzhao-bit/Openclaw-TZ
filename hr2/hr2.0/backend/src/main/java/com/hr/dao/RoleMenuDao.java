package com.hr.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hr.entity.RoleMenu;

import java.util.List;

public interface RoleMenuDao extends BaseMapper<RoleMenu> {
    List<Long> getMenuIdsByRoleId(Long roleId);
}
