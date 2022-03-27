package com.springcloud.zookeeperclient.curator;

import lombok.SneakyThrows;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.transaction.CuratorOp;
import org.apache.curator.framework.api.transaction.TransactionOp;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author CDY
 * @date 2021/6/15
 * @description  事务操作
 */
public class CuratorTransactionTest {

    private CuratorFramework cf;

    @Before
    public void getCuratorFramework(){
        cf = CuratorConfig.connection();
    }

    @After
    public void close(){
        CuratorConfig.close(cf);
    }

    @Test
    @SneakyThrows
    public void transactionTest(){
        // 构建事务构造器
        TransactionOp transactionOp = cf.transactionOp();
        // 创建事务操作，TransactionOp 可重用，无需多次构建
        CuratorOp op1 = transactionOp.create().forPath("/transaction", "open transaction".getBytes(StandardCharsets.UTF_8));
        CuratorOp op2 = transactionOp.setData().forPath("/demon", "set success".getBytes(StandardCharsets.UTF_8));
        List<CuratorOp> opList = new ArrayList<>();
        opList.add(op1);
        opList.add(op2);
        // 将多个操作作为单个事务提交，参数为 String... CuratorOp
        cf.transaction().forOperations(opList);
    }

}
