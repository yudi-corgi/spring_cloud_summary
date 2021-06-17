package com.springcloud.zookeeperclient.primitive;

import lombok.SneakyThrows;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author CDY
 * @date 2021/6/14
 * @description
 */
public class ZkWatcherTest {

    private ZooKeeper zooKeeper;

    @Before
    public void getConnection(){
        zooKeeper = ZkConfig.connection();
    }

    @After
    public void close(){
        ZkConfig.close(zooKeeper);
    }

    /**
     * 循环注册 watcher
     * @param path  节点路径
     * @param type  方法类型，1:exists() 2:getData() 3:getChildren()
     * @return
     */
    private Watcher registerWatcherLoop(String path, int type){
        return event -> {
            System.out.println("自定义 watcher ");
            System.out.println("路径："+event.getPath());
            System.out.println("事件类型："+event.getType());
            System.out.println("通知状态："+event.getState());
            try {
                // Watcher 为一次性，通知接收后再次注册本身，若是节点被删除，则不需要再注册
                if(event.getType() != Watcher.Event.EventType.NodeDeleted){
                    switch (type){
                        case 1: zooKeeper.exists(path, (Watcher) this); break;
                        case 2: zooKeeper.getData(path, (Watcher) this, null); break;
                        case 3: zooKeeper.getChildren(path, (Watcher) this); break;
                        default:
                            System.out.println("类型不匹配，不循环注册啦!"); break;
                    }
                }
            } catch (KeeperException | InterruptedException e) {
                e.printStackTrace();
            }
        };
    }

    /**
     * watcher 1
     */
    private final Watcher watcher1 = event -> {
        System.out.println("自定义 watcher1");
        System.out.println("监听器1 ：路径："+event.getPath());
        System.out.println("监听器1 ：事件类型："+event.getType());
        System.out.println("监听器1 ：通知状态："+event.getState());
    };

    /**
     * watcher 2
     */
    private final Watcher watcher2 = event -> {
        System.out.println("自定义 watcher2");
        System.out.println("监听器2 ：路径："+event.getPath());
        System.out.println("监听器2 ：事件类型："+event.getType());
        System.out.println("监听器2 ：通知状态："+event.getState());
    };

    /**
     * 睡眠一分钟，用于保持连接，以便执行节点操作，触发各监听器
     * @throws InterruptedException
     */
    private void sleep() throws InterruptedException {
        TimeUnit.SECONDS.sleep(60);
        System.out.println("完毕！");
    }

    @Test
    @SneakyThrows
    public void existsDefaultWatcher(){
        // 使用 zookeeper 注册连接时注册的监听器，监听 /exists 节点的增删改操作
        zooKeeper.exists("/exists",true);
        sleep();
    }

    @Test
    @SneakyThrows
    public void existsCustomizeWatcher(){
        zooKeeper.exists("/exists", registerWatcherLoop("/exists", 1));
        sleep();
    }

    @Test
    @SneakyThrows
    public void getDataDefaultWatcher(){
        // 使用 zookeeper 注册连接时注册的监听器，监听 /getData 节点的增删改操作
        Stat stat = new Stat();
        zooKeeper.getData("/getData",true, stat);
        sleep();
    }

    @Test
    @SneakyThrows
    public void getDataCustomizeWatcher(){
        Stat stat = new Stat();
        // 注册多个监听器
        zooKeeper.getData("/getData", watcher1, stat);
        zooKeeper.getData("/getData", watcher2, stat);
        sleep();
    }

    @Test
    @SneakyThrows
    public void getChildrenDefaultWatcher(){
        // 使用 zookeeper 注册连接时注册的监听器，监听 /getChildren 节点的删除及其子节点的增删操作
        Stat stat = new Stat();
        List<String> children = zooKeeper.getChildren("/getChildren", true, stat);
        children.forEach(System.out::println);
        sleep();
    }

    @Test
    @SneakyThrows
    public void getChildrenCustomizeWatcher(){
        Stat stat = new Stat();
        // 注册多个监听器
        List<String> children = zooKeeper.getChildren("/getChildren", registerWatcherLoop("/getChildren", 3), stat);
        children.forEach(System.out::println);
        sleep();
    }

}
