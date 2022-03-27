package com.springcloud.zookeeperclient.primitive;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author CDY
 * @date 2021/6/14
 * @description  ZK 分布式锁测试类
 */
public class ZkDistributedLockTest {

    /**
     * 模拟购物耗时业务
     * @throws InterruptedException
     */
    private static void sellMock() throws InterruptedException {
        System.out.println("抢购开始！");
        System.out.println("等待ing...");
        TimeUnit.SECONDS.sleep(5);
        System.out.println("抢购结束");
    }

    public static void sellMockWithLock() throws InterruptedException, IOException {
        ZkDistributedLock zdl = new ZkDistributedLock();
        // 获取锁
        zdl.acquireLock();
        // 获取成功会执行业务
        sellMock();
        // 最后释放锁
        zdl.releaseLock();
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        // 循环十次，开启多个客户端连接 ZK 服务端测试
        for (int i = 0; i < 10; i++) {
            ZkDistributedLockTest.sellMockWithLock();
        }
    }

}
