package com.springcloud.zookeeperclient.primitive;

import lombok.SneakyThrows;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

/**
 * @author CDY
 * @date 2021/6/13
 * @description
 */
public class ZkUpdateTest {

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
    public void syncUpdate(){
        // 参数一：节点路径
        // 参数二：节点数据
        // 参数三：节点数据版本号，传递 -1 表示更新不考虑版本号
        Stat stat = zooKeeper.setData("/node", "newData".getBytes(StandardCharsets.UTF_8), -1);
        System.out.println("数据版本号："+stat.getVersion());
    }

    @Test
    @SneakyThrows
    public void asyncUpdate(){
        // 参数四：回调函数
        // 参数五：上下文信息，会传递给回调函数中的 Object ctx 参数
        // 异步设值
        zooKeeper.setData("/node", "newData".getBytes(StandardCharsets.UTF_8), -1,
                (rc, path, ctx, stat) -> {
                    System.out.println("状态(0成功)："+rc);
                    System.out.println("路径："+path);
                    System.out.println("数据版本："+stat.getVersion());
                    System.out.println("上下文信息："+ctx);
                }, "I'm Context");
        System.out.println("异步更新方法执行到此！");
    }
}
