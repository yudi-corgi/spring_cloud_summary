package com.cloudalibaba.seata.account.service;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 * @author YUDI-Corgi
 * @description AccountService 实现类
 */
@LocalTCC
public interface AccountTccService {

    /**
     * 扣减金额，即 TCC 事务中的 Try 方法
     * @param userId 用户ID
     * @param money 金额
     */
    @TwoPhaseBusinessAction(name = "deduct", commitMethod = "confirm", rollbackMethod = "cancel")
    void deduct(@BusinessActionContextParameter(paramName = "userId") String userId,
                @BusinessActionContextParameter(paramName = "money")int money);

    /**
     * 二阶段确认提交接口
     * @param ctx 业务操作上下问
     * @return 操作是否成功，true-成功，false-失败
     */
    boolean confirm(BusinessActionContext ctx);

    /**
     * 二阶段回滚接口
     * @param ctx 业务操作上下问
     * @return 操作是否成功，true-成功，false-失败
     */
    boolean cancel(BusinessActionContext ctx);
}
