package com.springcloud.zookeeperclient.primitive;

import lombok.SneakyThrows;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author CDY
 * @date 2021/6/13
 * @description
 */
public class ZkCreatorTest {

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
    public void createOne(){
        // 参数一：节点路径
        // 参数二：节点数据
        // 参数三：节点 ACL 权限
        // 参数四：节点类型
        String path = zooKeeper.create("/node1", "node1".getBytes(StandardCharsets.UTF_8), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        // 所有人可访问，但权限为只读  world:anyone:r
        // String path = zooKeeper.create("/node1", "node1".getBytes(StandardCharsets.UTF_8), ZooDefs.Ids.READ_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(path);
    }

    @Test
    @SneakyThrows
    public void createAuth(){
        zooKeeper.addAuthInfo("digest","admin:123".getBytes(StandardCharsets.UTF_8));
        zooKeeper.addAuthInfo("digest","super:123".getBytes(StandardCharsets.UTF_8));
        // 认证用户可访问  auth:user:cdrwa，user 会自动替换为 zookeeper 中添加的认证用户
        String path = zooKeeper.create("/auth", "auth".getBytes(StandardCharsets.UTF_8), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("添加认证用户访问节点：" + path);
    }

    @Test
    @SneakyThrows
    public void customizeAclCreate(){
        zooKeeper.addAuthInfo("digest","customize:123".getBytes(StandardCharsets.UTF_8));
        List<ACL> aclList = new ArrayList<>();
        // 指定 scheme(授权模式)和授权对象(id)
        Id id = new Id("auth","customize");
        // 权限设置
        aclList.add(new ACL(ZooDefs.Perms.READ,id));
        aclList.add(new ACL(ZooDefs.Perms.WRITE,id));
        String path = zooKeeper.create("/customize", "customize ACL".getBytes(StandardCharsets.UTF_8), aclList, CreateMode.PERSISTENT);
        System.out.println("自定义权限节点："+path);
    }

    @Test
    @SneakyThrows
    public void createDigest(){
        List<ACL> aclList = new ArrayList<>();
        Id id = new Id("digest","admin:pf4KJ0yh0JhZLDAC+1vmC8s2bVw=");
        aclList.add(new ACL(ZooDefs.Perms.ALL,id));
        // 认证用户可访问  digest:user:<密文>:cdrwa
        String path = zooKeeper.create("/digest", "digest".getBytes(StandardCharsets.UTF_8), aclList, CreateMode.PERSISTENT_SEQUENTIAL);
        System.out.println("添加认证用户访问节点：" + path);
    }

    @Test
    @SneakyThrows
    public void asyncCreate(){
        // 参数五：回调函数
        // 参数六：上下文信息，会传递给回调函数中的 Object ctx 参数
        // 异步创建节点
        zooKeeper.create("/async", "async".getBytes(StandardCharsets.UTF_8), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL,
                (rc, path, ctx, name) -> {
            System.out.println("状态(0成功)："+rc);
            System.out.println("路径："+path);
            System.out.println("路径："+name);
            System.out.println("上下文信息："+ctx);
        }, "I's Context，回调方法中的 ctx 参数接收");
    }
}
