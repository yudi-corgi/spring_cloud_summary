package com.springcloud.zookeeperclient.primitive;

import lombok.SneakyThrows;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author CDY
 * @date 2021/6/13
 * @description
 */
public class ZkGetTest {

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
    public void syncGet(){
        // 参数一：节点路径
        // 参数二：是否使用节点上注册的监听器
        // 参数三：Stat 节点元数据对象，传入后会把节点元数据信息赋值到改对象
        Stat stat = new Stat();
        byte[] data = zooKeeper.getData("/node", false, stat);
        System.out.println("节点数据:" + new String(data, StandardCharsets.UTF_8));
        System.out.println("数据版本号：" + stat.getVersion());
    }

    @Test
    @SneakyThrows
    public void asyncGet(){
        // 参数三：回调函数
        // 参数四：上下文信息，会传递给回调函数中的 Object ctx 参数
        zooKeeper.getData("/node", false, (rc, path, ctx, data1, stat1) -> {
            System.out.println("状态：" + rc);
            System.out.println("路径：" + path);
            System.out.println("上下文信息：" + ctx);
            System.out.println("数据：" + new String(data1,StandardCharsets.UTF_8));
            System.out.println("数据版本号：" + stat1.getVersion());
        }, "I'm Context");
        System.out.println("异步获取方法执行到此！");
    }

    @Test
    @SneakyThrows
    public void syncGetChildren(){
        // 参数一：节点路径
        // 参数二：是否使用节点上注册的监听器
        // 参数三：Stat 节点元数据对象，传入后会把节点元数据信息赋值到改对象
        Stat stat = new Stat();
        List<String> data = zooKeeper.getChildren("/node", false, stat);
        data.forEach(System.out::println);
        System.out.println("数据版本号：" + stat.getVersion());
    }

    @Test
    @SneakyThrows
    public void asyncGetChildren(){
        // 参数三：回调函数
        // 参数四：上下文信息，会传递给回调函数中的 Object ctx 参数
        Stat stat = new Stat();
        zooKeeper.getChildren("/node", false, (rc, path, ctx, children, stat1) -> {
            System.out.println("状态：" + rc);
            System.out.println("路径：" + path);
            System.out.println("上下文信息：" + ctx);
            System.out.println("数据版本号：" + stat1.getVersion());
            children.forEach(System.out::println);
        }, "I'm Context");
        System.out.println("异步获取子节点方法执行到此！");
    }

    @Test
    @SneakyThrows
    public void syncExists(){
        Stat exists = zooKeeper.exists("/node", false);
        System.out.println(exists == null ? "节点不存在" : "数据版本号：" + exists.getVersion());

    }

    @Test
    @SneakyThrows
    public void asyncExists(){
        // 参数三：回调函数
        // 参数四：上下文信息，会传递给回调函数中的 Object ctx 参数
        zooKeeper.exists("/node", false, (rc, path, ctx, stat) -> {
            System.out.println("状态：" + rc);
            System.out.println("路径：" + path);
            System.out.println("上下文信息：" + ctx);
            System.out.println(stat == null ? "节点不存在" : "数据版本号：" + stat.getVersion());
        }, "I'm Context");
        System.out.println("异步获取子节点方法执行到此！");
    }

    @Test
    public void ssasd() throws KeeperException, InterruptedException {
        List<String> children = zooKeeper.getChildren("/locks", false);
        System.out.println(children.get(0));
    }
}
