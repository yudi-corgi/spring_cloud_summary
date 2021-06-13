package com.springcloud.zookeeperclient.primitive;

import lombok.SneakyThrows;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

/**
 * @author CDY
 * @date 2021/6/13
 * @description
 */
public class ZkConfig {

    @SneakyThrows
    public static ZooKeeper connection(){
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ZooKeeper zooKeeper = new ZooKeeper("192.168.137.8:2181", 5000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                if(watchedEvent.getState() == Event.KeeperState.SyncConnected){
                    System.out.println("客户端已连接！");
                    countDownLatch.countDown();
                }
            }
        });
        countDownLatch.await();
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
