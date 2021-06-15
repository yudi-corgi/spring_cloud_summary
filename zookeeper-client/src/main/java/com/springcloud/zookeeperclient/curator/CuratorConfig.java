package com.springcloud.zookeeperclient.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.retry.RetryOneTime;
import org.apache.zookeeper.CreateMode;

import java.nio.charset.StandardCharsets;

/**
 * @author CDY
 * @date 2021/6/14
 * @description  配置 Curator 连接
 */
public class CuratorConfig {

    public static CuratorFramework connection(){

        // 参数一：连接的 ZK 服务端，多个 IP：Port 逗号分隔
        // 参数二：会话超时时间
        // 参数三：连接超时时间
        // 参数四：重试策略
        // CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient("127.0.0.1:2181,127.0.0.2:2181", 5000, 5000, new RetryOneTime(1000));

        // Builder 方式构建
        CuratorFramework curatorFramework =  CuratorFrameworkFactory.builder()
                .connectString("localhost:2181")
                .sessionTimeoutMs(50000)
                .connectionTimeoutMs(5000)
                .retryPolicy(new RetryNTimes(3, 3000))
                .namespace("root")
                .build();
        // 调用该方法才可开始使用 curator
        curatorFramework.start();
        return curatorFramework;
    }

    public static void close(CuratorFramework curatorFramework){
        if (curatorFramework != null){
            curatorFramework.close();
        }
    }

    public static void main(String[] args) throws Exception {

        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient("127.0.0.1:2181", 5000, 5000, new RetryOneTime(1000));
        curatorFramework.start();
        System.out.println("asdasd");
        curatorFramework = curatorFramework.usingNamespace("asd");
        System.out.println(curatorFramework.getNamespace());
        curatorFramework.create().withMode(CreateMode.PERSISTENT).forPath("/aasd","asd".getBytes(StandardCharsets.UTF_8));
        curatorFramework.close();
    }

}
