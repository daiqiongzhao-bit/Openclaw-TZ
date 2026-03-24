package com.hr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hr.dao.RoleDao;
import com.hr.dao.RoleMenuDao;
import com.hr.entity.Role;
import com.hr.entity.RoleMenu;
import com.hr.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleDao, Role> implements RoleService {

    @Autowired
    private RoleMenuDao roleMenuDao;

    @Override
    public List<Role> getAllRoles() {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Role::getDelFlag, 0);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public boolean saveRole(Role role) {
        return save(role);
    }

    @Override
    public boolean updateRole(Role role) {
        return updateById(role);
    }

    @Override
    public boolean deleteRole(Long id) {
        // 逻辑删除
        Role role = new Role();
        role.setId(id);
        role.setDelFlag(1);
        return updateById(role);
    }

    @Override
    @Transactional
    public boolean assignMenus(Long roleId, List<Long> menuIds) {
        // 先删除原有菜单关联
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getRoleId, roleId);
        roleMenuDao.delete(wrapper);

        // 新增菜单关联
        for (Long menuId : menuIds) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenuDao.insert(roleMenu);
        }
        return true;
    }
}
