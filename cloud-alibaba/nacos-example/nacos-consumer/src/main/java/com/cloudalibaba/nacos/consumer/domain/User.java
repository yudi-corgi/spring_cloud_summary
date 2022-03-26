package com.cloudalibaba.nacos.consumer.domain;

import lombok.Builder;
import lombok.Data;

/**
 * @author YUDI-Corgi
 * @description 用户实体，实例类应当抽象到单独服务模块，避免每个服务重复创建相同类
 */
@Data
@Builder
public class User {

    private String id;
    private String name;
    private Integer age;
    private Integer gender;
    private String address;
    private String email;
}
