package com.cloudalibaba.nacos.provider.service;

import com.cloudalibaba.nacos.provider.domain.User;

import java.util.List;

/**
 * @author YUDI-Corgi
 * @description
 */
public interface UserService {

    /**
     * 根据 ID 查找用户
     * @param id 用户ID
     * @return 目标用户
     */
    User selectById(String id);

    /**
     * 查询所有用户
     * @return 用户列表
     */
    List<User> selectAll();

    /**
     * 保存用户信息
     * @param user 用户信息
     */
    void saveUser(User user);

    /**
     * 根据 ID 删除用户
     * @param id 用户 ID
     */
    void deleteById(String id);

    /**
     * 根据 ID 更新用户
     * @param user 需更改的用户信息
     */
    void updateUser(User user);
}
