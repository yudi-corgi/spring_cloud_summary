package com.springcloud.zookeeperclient.curator;

import lombok.SneakyThrows;
import org.apache.curator.framework.CuratorFramework;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @author CDY
 * @date 2021/6/14
 * @description  删除节点
 */
public class CuratorDeleteTest {

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
    public void deleteTest(){
        // deletingChildrenIfNeeded()：递归删除子节点
        cf.delete().deletingChildrenIfNeeded().withVersion(-1).forPath("/delete");
    }

    @Test
    @SneakyThrows
    public void asyncDeleteTest(){
        cf.delete().deletingChildrenIfNeeded().withVersion(-1)
                .inBackground((client, event) -> {
                    System.out.println("数据："+new String(event.getData(), StandardCharsets.UTF_8));
                    System.out.println("上下文信息："+event.getContext());
                },"I'm Context").forPath("/delete");
        TimeUnit.SECONDS.sleep(1);
        System.out.println("执行完毕！");
    }
}
