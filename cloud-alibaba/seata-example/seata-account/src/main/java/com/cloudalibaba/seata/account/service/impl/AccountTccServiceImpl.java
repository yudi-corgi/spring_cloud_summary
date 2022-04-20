package com.cloudalibaba.seata.account.service.impl;

import com.cloudalibaba.seata.account.domain.AccountFreeze;
import com.cloudalibaba.seata.account.mapper.AccountFreezeMapper;
import com.cloudalibaba.seata.account.mapper.AccountMapper;
import com.cloudalibaba.seata.account.service.AccountTccService;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author YUDI-Corgi
 * @description TCC 接口实现，模拟本地分支事务扣减金额操作（仅在 Account 实现示例，Storage 模块则无）
 */
@Slf4j
@Service
public class AccountTccServiceImpl implements AccountTccService {

    @Resource
    private AccountMapper accountMapper;
    @Resource
    private AccountFreezeMapper freezeMapper;

    @Override
    public void deduct(String userId, int money) {
        // 获取全局事务 ID，RootContext 是根上下文，可获取与事务有关的信息
        String xid = RootContext.getXID();

        // 悬挂问题判断
        AccountFreeze oldFreeze = freezeMapper.selectById(xid);
        if (oldFreeze != null) {
            // 不为空说明有执行过 cancel 回滚，直接返回
            // 因为只有回滚才会插入一条记录，正确提交是删除冻结记录
            return;
        }

        // 1. 扣减金额
        // 不判断金额为负数情况是因为表字段设计为 unsigned，若为负数则会抛出异常回滚事务
        accountMapper.deduct(userId, money);

        // 2. 记录冻结金额，事务状态
        AccountFreeze freeze = new AccountFreeze();
        freeze.setXid(xid);
        freeze.setUserId(userId);
        freeze.setFreezeMoney(money);
        freeze.setState(AccountFreeze.State.TRY);
        freezeMapper.insert(freeze);
    }

    @Override
    public boolean confirm(BusinessActionContext ctx) {
        String xid = ctx.getXid();
        // 删除冻结记录
        return freezeMapper.deleteById(xid) == 1;
    }

    @Override
    public boolean cancel(BusinessActionContext ctx) {
        // 查询冻结记录
        String xid = ctx.getXid();
        AccountFreeze freeze = freezeMapper.selectById(xid);

        // 空回滚判断，为空插入一条记录，是为了防止触发业务悬挂
        // 因为什么都不做就返回，当 Try 比 Cancel 晚执行，查不到记录会认为事务还没有进行二阶段操作
        // 从而执行 Try 操作，就会出现业务悬挂
        if (Objects.isNull(freeze)) {
            AccountFreeze emptyFreeze = new AccountFreeze();
            emptyFreeze.setXid(xid);
            emptyFreeze.setUserId(ctx.getActionContext("userId").toString());
            emptyFreeze.setFreezeMoney(0);
            emptyFreeze.setState(AccountFreeze.State.TRY);
            freezeMapper.insert(emptyFreeze);
            return true;
        }

        // 幂等判断
        if (freeze.getState() == AccountFreeze.State.CANCEL) {
            // 状态为 CANCEL 说明已回滚处理过
            return true;
        }

        // 1. 恢复可用余额
        accountMapper.refund(freeze.getUserId(), freeze.getFreezeMoney());

        // 2. 将冻结金额清零，状态改为 CANCEL
        freeze.setFreezeMoney(0);
        freeze.setState(AccountFreeze.State.CANCEL);
        return freezeMapper.updateById(freeze) == 1;
    }
}
