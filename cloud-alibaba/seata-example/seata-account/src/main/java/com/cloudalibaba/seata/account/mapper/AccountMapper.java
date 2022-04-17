package com.cloudalibaba.seata.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloudalibaba.seata.account.domain.Account;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @author YUDI-Corgi
 * @description AccountMapper
 */
public interface AccountMapper extends BaseMapper<Account> {

    /**
     * 扣除金额
     * @param userId 用户ID
     * @param money  金额
     * @return 影响行数
     */
    @Update("update account set money = money - ${money} where user_id = #{userId}")
    int deduct(@Param("userId") String userId, @Param("money") int money);

    /**
     * 退款
     * @param userId 用户ID
     * @param money  金额
     * @return 影响行数
     */
    @Update("update account set money = money + ${money} where user_id = #{userId}")
    int refund(@Param("userId") String userId, @Param("money") int money);

}
