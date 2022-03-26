package com.cloudalibaba.nacos.provider.service.impl;

import com.cloudalibaba.nacos.provider.domain.User;
import com.cloudalibaba.nacos.provider.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author YUDI-Corgi
 * @description
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    /**
     * 模拟 DB 保存数据
     */
    private static final Map<String, User> USER_MAP = new HashMap<>(16);
    private final Random random = new Random();
    private final int seed = 10000;

    static {
        USER_MAP.put("1", User.builder().id("1").name("尼禄").age(18).gender(1).address("天堂").build());
        USER_MAP.put("2", User.builder().id("2").name("但丁").age(16).gender(2).address("人间").build());
        USER_MAP.put("3", User.builder().id("3").name("维吉尔").age(20).gender(0).address("地狱").build());
    }

    @Override
    public User selectById(String id) {
        User user;
        if ((user = USER_MAP.get(id)) != null) {
            return user;
        }
        log.warn("USER_MAP 为空，请先添加用户信息.");
        return null;
    }

    @Override
    public List<User> selectAll() {
        return new ArrayList<>(USER_MAP.values());
    }

    @Override
    public void saveUser(User user) {
        String id = getRandomId();
        user.setId(id);
        if (USER_MAP.get(id) == null) {
            USER_MAP.put(id, user);
        } else {
            log.warn("已存在 ID:{} 的用户.", id);
        }
    }

    @Override
    public void deleteById(String id) {
        USER_MAP.remove(id);
    }

    @Override
    public void updateUser(User user) {
        USER_MAP.put(user.getId(), user);
    }

    private String getRandomId() {
        return String.valueOf(random.nextInt(seed));
    }
}
