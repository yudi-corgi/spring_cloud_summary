package com.springcloud.zookeeperclient.primitive;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author CDY
 * @date 2021/6/14
 * @description ZK 分布式锁构建
 */
public class ZkDistributedLock {

    CountDownLatch countDownLatch = new CountDownLatch(1);
    private final String LOCK_ROOT_PATH = "/locks";
    private final String LOCK_NODE_NAME = LOCK_ROOT_PATH + "/lock_";
    private String lockPath;
    @Getter
    @Setter
    private ZooKeeper zooKeeper;

    public ZkDistributedLock() throws IOException, InterruptedException {
        this.zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, event -> {
            if(event.getType() == Watcher.Event.EventType.None && event.getState() == Watcher.Event.KeeperState.SyncConnected){
                System.out.println("客户端连接成功!");
                countDownLatch.countDown();
            }
        });
        countDownLatch.await();
    }

    /**
     * 获取锁
     */
    public void acquireLock(){
        createLock();
        attemptLock();
    }

    private final Object lock = new Object();
    /**
     * 监听器，监听前一个节点的删除事件，若删除则唤醒其它客户端线程
     */
    private final Watcher watcher = event -> {
        // 判断事件是否为节点删除
        if(event.getType() == Watcher.Event.EventType.NodeDeleted){
            synchronized (lock){
                // xxx: 这里使用 notifyAll 性能不太好，应该使用 LockSupport#unpark(t) 来唤醒指定的线程
                // 避免所有线程被唤醒后又重新走了一遍创建、获取锁的流程；
                // 而使用 LockSupport 的缺点就是需要保存所有节点对应的线程，以便能够指定线程唤醒
                lock.notifyAll();
            }
        }
    };

    /**
     * 创建锁
     */
    @SneakyThrows
    public void createLock(){
        // 判断父节点是否存在，不存在则创建
        Stat stat = zooKeeper.exists(LOCK_ROOT_PATH, false);
        if(stat == null){
            zooKeeper.create(LOCK_ROOT_PATH, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        // 创建子节点 /lock_ ，注意是临时顺序节点
        lockPath = zooKeeper.create(LOCK_NODE_NAME, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("创建成功，名称为：" + lockPath);
    }

    /**
     * 尝试获取锁
     */
    @SneakyThrows
    public void attemptLock(){
        // 获取所有子节点
        List<String> children = zooKeeper.getChildren(LOCK_ROOT_PATH, false);
        Collections.sort(children);
        // 获取当前客户端创建的节点所在下标
        int index = children.indexOf(lockPath.substring(LOCK_ROOT_PATH.length()+1));
        if(index == 0){
            // 在第一位即表示获取到锁
            System.out.println("获取锁成功，执行业务逻辑！");
        }else{
            // 不是第一位，则获取前一个子节点路径，并注册监听器
            String path = children.get(index - 1);
            Stat stat = zooKeeper.exists(LOCK_ROOT_PATH + "/" + path, watcher);
            if(stat != null){
                // 若不为空，则前一个子节点可能在等待锁也可能是获取到锁但还在处理业务逻辑中，因此让当前客户端线程等待
                synchronized (lock) {
                    lock.wait();
                }
            }
            // 1. 若为空，说明在判断节点是否存在时，前一个子节点已获取到锁并执行完其业务逻辑删除掉了节点，则当前节点重新尝试获取锁
            // 2. 或者不为空时线程阻塞，当被唤醒后则继续尝试获取锁
            attemptLock();
        }
    }

    /**
     * 释放锁
     */
    @SneakyThrows
    public void releaseLock(){
        zooKeeper.delete(lockPath, -1);
        ZkConfig.close(zooKeeper);
        System.out.println(lockPath + " 释放锁完毕，关闭客户端连接！");
    }
}
