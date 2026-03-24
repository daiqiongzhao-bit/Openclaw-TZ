package com.hr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hr.dao.MenuDao;
import com.hr.entity.Menu;
import com.hr.service.MenuService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuDao, Menu> implements MenuService {

    @Override
    public List<Menu> getAllMenus() {
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Menu::getDelFlag, 0);
        wrapper.orderByAsc(Menu::getSort);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<Menu> getMenusByRoleId(Long roleId) {
        return baseMapper.getMenusByRoleId(roleId);
    }

    @Override
    public List<Menu> getMenuTree() {
        List<Menu> allMenus = getAllMenus();
        return buildMenuTree(allMenus, 0L);
    }

    private List<Menu> buildMenuTree(List<Menu> menus, Long parentId) {
        return menus.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> {
                    List<Menu> children = buildMenuTree(menus, menu.getId());
                    menu.setChildren(children);
                    return menu;
                })
                .collect(Collectors.toList());
    }

    @Override
    public boolean saveMenu(Menu menu) {
        return save(menu);
    }

    @Override
    public boolean updateMenu(Menu menu) {
        return updateById(menu);
    }

    @Override
    public boolean deleteMenu(Long id) {
        // 逻辑删除
        Menu menu = new Menu();
        menu.setId(id);
        menu.setDelFlag(1);
        return updateById(menu);
    }
}
