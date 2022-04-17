package com.cloudalibaba.seata.storage.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author YUDI-Corgi
 * @description Storage 实体
 */
@Data
public class Storage {

    @TableId
    private Long id;
    private String commodityCode;
    private Integer count;

}
