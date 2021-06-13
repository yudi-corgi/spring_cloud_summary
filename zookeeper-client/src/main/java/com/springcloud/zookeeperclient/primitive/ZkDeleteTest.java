package com.springcloud.zookeeperclient.primitive;

import lombok.SneakyThrows;
import org.apache.zookeeper.ZooKeeper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author CDY
 * @date 2021/6/13
 * @description
 */
public class ZkDeleteTest {

    private ZooKeeper zooKeeper;

    @Before
    public void getConnection(){
        zooKeeper = ZkConfig.connection();
    }

    @After
    public void close(){
        ZkConfig.close(zooKeeper);
    }

    @Test
    @SneakyThrows
    public void syncDelete(){
        // 参数一：节点路径
        // 参数二：数据版本号，-1 表示删除不考虑版本号
        // 节点存在子节点会删除失败
        zooKeeper.delete("/node", -1);
    }

    @Test
    @SneakyThrows
    public void asyncDelete(){
        // 异步删除
        zooKeeper.delete("/node", -1, (rc, path, ctx) -> {
            System.out.println("状态：" + rc);
            System.out.println("路径：" + path);
            System.out.println("上下问信息：" + ctx);
        }, "I'm Context");
        System.out.println("异步删除方法执行到此！");
    }
}
