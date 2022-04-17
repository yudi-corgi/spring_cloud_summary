package com.cloudalibaba.seata.account.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloudalibaba.seata.account.domain.Account;

/**
 * @author YUDI-Corgi
 * @description Account 服务接口
 */
public interface AccountService extends IService<Account> {

    /**
     * 从用户账户中扣款
     * @param userId 用户ID
     * @param money  金额
     */
    void deduct(String userId, int money);

}
