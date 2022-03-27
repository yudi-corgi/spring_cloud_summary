package com.cloudalibaba.nacos.api.domain;

import lombok.Builder;
import lombok.Data;

/**
 * @author YUDI-Corgi
 * @description User 实体
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
