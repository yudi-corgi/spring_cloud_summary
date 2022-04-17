package com.cloudalibaba.seata.account.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author YUDI-Corgi
 * @description Account 实体
 */
@Data
public class Account {
    @TableId
    private Long id;
    private String userId;
    private Integer money;
}
