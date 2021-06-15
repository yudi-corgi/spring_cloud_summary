package com.springcloud.zookeeperclient.curator;

import lombok.SneakyThrows;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author CDY
 * @date 2021/6/14
 * @description  创建节点
 */
public class CuratorCreatorTest {

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
    public void createTest(){
        // creatingParentsIfNeeded：递归创建节点
        cf.create().creatingParentsIfNeeded()
                // 指定节点类型
                .withMode(CreateMode.PERSISTENT)
                // 节点 ACL 权限
                .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                // 节点路径，节点数据
                .forPath("/create/r2","create".getBytes(StandardCharsets.UTF_8));
    }

    @Test
    @SneakyThrows
    public void createCustomizeTest(){
        List<ACL> aclList = new ArrayList<>();
        Id id = new Id("auth","admin:123");
        aclList.add(new ACL(ZooDefs.Perms.ALL,id));
        cf.create().withMode(CreateMode.PERSISTENT).withACL(aclList)
                .forPath("/createCustomize","createCustomize".getBytes(StandardCharsets.UTF_8));
    }

    @Test
    @SneakyThrows
    public void asyncCreateTest(){
        // 异步构建
        cf.create().withMode(CreateMode.PERSISTENT)
                .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                // 回调函数
                .inBackground((client, event) -> {
                    System.out.println("客户端连接当前使用的命名空间：" + client.getNamespace());
                    System.out.println("路径："+event.getPath());
                    System.out.println("上下文信息："+event.getContext());
                },"I'm Context")
                .forPath("/asyncCreate1","asyncCreate".getBytes(StandardCharsets.UTF_8));
        TimeUnit.SECONDS.sleep(3);
        System.out.println("执行到此!");
    }

}
