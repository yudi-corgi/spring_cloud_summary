package com.springcloud.zookeeperclient.primitive;

import lombok.SneakyThrows;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author CDY
 * @date 2021/6/13
 * @description
 */
public class ZkConfig implements Watcher {

    private static final CountDownLatch COUNT_DOWN_LATCH = new CountDownLatch(1);

    @Override
    public void process(WatchedEvent event) {
        // 事件类型，连接的类型为 none
        Event.EventType type = event.getType();
        System.out.println(type);
        // 判断通知状态
        Event.KeeperState state = event.getState();
        if(state == Event.KeeperState.SyncConnected){
            System.out.println("正常连接");
        }else if (state == Event.KeeperState.Disconnected){
            // 当会话断开会出现，断开连接不代表不能重连，在会话超时时间内重连可以恢复正常，因为服务端在超时时间内会保存客户端的连接信息
            System.out.println("断开连接");
            // ...执行重连操作...
        }else if (state == Event.KeeperState.Expired){
            // 当会话超时重连会进入这里
            System.out.println("连接过期");
        }else if (state == Event.KeeperState.AuthFailed){
            // 在操作的时候权限不够进入
            System.out.println("授权失败");
        }
        COUNT_DOWN_LATCH.countDown();
    }

    @SneakyThrows
    public void testConnection(){
        ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new ZkConfig());
        COUNT_DOWN_LATCH.await();
        // 模拟授权失败，填写错误的 auth 认证用户，针对 KeeperState.AuthFailed
        zooKeeper.addAuthInfo("digest","admin:123456".getBytes());
        byte[] data = zooKeeper.getData("/node", false, null);
        System.out.println(new String(data));
        System.out.println("当前客户端会话ID："+zooKeeper.getSessionId());
        TimeUnit.SECONDS.sleep(5);
        close(zooKeeper);
    }

    public static void main(String[] args) {
        new ZkConfig().testConnection();
    }

    @SneakyThrows
    public static ZooKeeper connection(){
        ZooKeeper zooKeeper = new ZooKeeper("192.168.137.8:2181", 5000, event -> {
            if(event.getType() == Event.EventType.None && event.getState() == Event.KeeperState.SyncConnected){
                System.out.println("客户端连接成功!");
                COUNT_DOWN_LATCH.countDown();
            }
        });
        COUNT_DOWN_LATCH.await();
        System.out.println("当前客户端会话ID："+zooKeeper.getSessionId());
        return zooKeeper;
    }

    @SneakyThrows
    public static void close(ZooKeeper zooKeeper){
        if(zooKeeper != null){
            zooKeeper.close();
        }
    }



}
