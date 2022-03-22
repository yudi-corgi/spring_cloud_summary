package com.cloudalibaba.nacos.provider.domain;

import lombok.Builder;
import lombok.Data;

/**
 * @author YUDI-Corgi
 * @description 用户实体
 */
@Data
@Builder
public class User {

    private Integer id;
    private String name;
    private Integer age;
    private Integer gender;
    private String address;
    private String email;
}
