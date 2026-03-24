package com.hr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hr.entity.User;

import java.util.List;

public interface UserService extends IService<User> {
    User getUserByUsername(String username);
    List<User> getUsersByRoleId(Long roleId);
    boolean saveUser(User user);
    boolean updateUser(User user);
    boolean deleteUser(Long id);
}
