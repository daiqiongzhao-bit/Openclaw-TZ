package com.hr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hr.entity.Menu;

import java.util.List;

public interface MenuService extends IService<Menu> {
    List<Menu> getAllMenus();
    List<Menu> getMenusByRoleId(Long roleId);
    List<Menu> getMenuTree();
    boolean saveMenu(Menu menu);
    boolean updateMenu(Menu menu);
    boolean deleteMenu(Long id);
}
