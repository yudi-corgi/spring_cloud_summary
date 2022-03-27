package com.springcloud.zookeeperclient.curator;

import lombok.SneakyThrows;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author CDY
 * @date 2021/6/14
 * @description  查询节点数据、查询子节点、检查节点是否存在
 */
public class CuratorGetDataTest {

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
    public void getDataTest(){
        // 创建 Stat 对象，用于获取节点的元数据
        Stat stat = new Stat();
        byte[] bytes = cf.getData().storingStatIn(stat).forPath("/get");
        System.out.println(new String(bytes, StandardCharsets.UTF_8));
        System.out.println("数据版本号：" + stat.getVersion());
    }

    @Test
    @SneakyThrows
    public void asyncGetDataTest(){
        cf.getData().inBackground((client, event) -> {
            System.out.println("数据版本号：" + event.getStat().getVersion());
            System.out.println("数据：" + new String(event.getData(), StandardCharsets.UTF_8));
            System.out.println("上下文信息：" + event.getContext());
        }, "I'm Context").forPath("/get");
        TimeUnit.SECONDS.sleep(1);
        System.out.println("执行完毕！");
    }

    @Test
    @SneakyThrows
    public void getChildrenTest(){
        // 获取子节点
        List<String> children = cf.getChildren().forPath("/children");
        children.forEach(System.out::println);
    }

    @Test
    @SneakyThrows
    public void asyncGetChildrenTest(){
        cf.getChildren().inBackground((client, event) -> {
                    System.out.println("事件类型：" + event.getType());
                    System.out.println("上下文信息：" + event.getContext());
                    List<String> children = event.getChildren();
                    children.forEach(System.out::println);
                }, "context-获取子节点...").forPath("/children");
        TimeUnit.SECONDS.sleep(1);
        System.out.println("执行完毕！");
    }

    @Test
    @SneakyThrows
    public void checkExistsTest(){
        Stat stat = cf.checkExists().forPath("/exists");
        System.out.println("数据版本号：" + stat.getVersion());
    }

    @Test
    @SneakyThrows
    public void asyncCheckExistsTest(){
        cf.checkExists().inBackground((client, event) -> {
            System.out.println("事件类型：" + event.getType());
            System.out.println("上下文信息：" + event.getContext());
            System.out.println("节点元数据：" + event.getStat());
        }, "context-检查节点是否存在").forPath("/exists");
        TimeUnit.SECONDS.sleep(1);
        System.out.println("执行完毕！");
    }
}
