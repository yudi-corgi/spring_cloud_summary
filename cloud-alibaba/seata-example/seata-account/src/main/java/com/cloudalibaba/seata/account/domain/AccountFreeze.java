package com.cloudalibaba.seata.account.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author YUDI-Corgi
 * @description 账户金额冻结实体
 */
@Data
public class AccountFreeze {

    @TableId(type = IdType.INPUT)
    private String xid;
    private String userId;
    private Integer freezeMoney;
    private Integer state;

    /**
     * 静态内部常量类，用于标识 TCC 操作状态
     */
    public static abstract class State {
        public final static int TRY = 0;
        public final static int CONFIRM = 1;
        public final static int CANCEL = 2;
    }

}
