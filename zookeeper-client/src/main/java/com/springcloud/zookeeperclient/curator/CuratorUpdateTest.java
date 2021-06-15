package com.springcloud.zookeeperclient.curator;

import lombok.SneakyThrows;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @author CDY
 * @date 2021/6/14
 * @description  更新节点
 */
public class CuratorUpdateTest {

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
    public void updateTest(){
        // -1 表示版本号不参与设值操作
        Stat stat = cf.setData().withVersion(-1).forPath("/set", "newDate".getBytes(StandardCharsets.UTF_8));
        System.out.println("数据版本号：" + stat.getVersion());
    }

    @Test
    @SneakyThrows
    public void asyncUpdateTest(){
        // 异步设值
        Stat stat = cf.setData().withVersion(-1)
                .inBackground((client, event) -> {
                    System.out.println("客户端连接当前使用的命名空间：" + client.getNamespace());
                    System.out.println("事件类型："+event.getType());
                    System.out.println("上下文信息："+event.getContext());
                },"I'm Context")
                .forPath("/set", "async set newDate".getBytes(StandardCharsets.UTF_8));
        System.out.println("执行到此！");
        TimeUnit.SECONDS.sleep(1);
        System.out.println("数据版本号：" + stat.getVersion());
    }

}
