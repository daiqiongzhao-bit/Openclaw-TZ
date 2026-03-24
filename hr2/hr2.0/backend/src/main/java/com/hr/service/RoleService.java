package com.hr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hr.entity.Role;

import java.util.List;

public interface RoleService extends IService<Role> {
    List<Role> getAllRoles();
    boolean saveRole(Role role);
    boolean updateRole(Role role);
    boolean deleteRole(Long id);
    boolean assignMenus(Long roleId, List<Long> menuIds);
}
