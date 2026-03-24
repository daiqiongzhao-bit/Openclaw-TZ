package com.hr.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hr.entity.Menu;

import java.util.List;

public interface MenuDao extends BaseMapper<Menu> {
    List<Menu> getMenusByRoleId(Long roleId);
}
