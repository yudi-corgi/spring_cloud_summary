package com.springcloud.zookeeperclient.curator;

import lombok.SneakyThrows;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.*;
import org.apache.curator.framework.recipes.shared.SharedCount;
import org.apache.curator.framework.recipes.shared.SharedCountListener;
import org.apache.curator.framework.recipes.shared.SharedCountReader;
import org.apache.curator.framework.state.ConnectionState;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author CDY
 * @date 2021/6/15
 * @description  分布式锁
 */
public class CuratorDistributedLockTest {

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
    public void mutexLockTest(){
        // 可重入互斥锁，并且是公平锁，客户端会按请求顺序获取互斥锁（由 ZK 排序）
        InterProcessLock mutexLock = new InterProcessMutex(cf, "/root");
        System.out.println("尝试获取锁!");
        // 参数表示阻塞时长，超过则不再阻塞
        mutexLock.acquire(10, TimeUnit.SECONDS);
        System.out.println("获取锁成功！");
        for (int i = 0; i < 5; i++) {
            mutexLock.acquire();
            System.out.println("锁重入：" + (i + 1) + "次");
            TimeUnit.SECONDS.sleep(3);
        }
        for (int i = 0; i < 5; i++) {
            mutexLock.release();
            System.out.println("锁释放：" + (i + 1) + "次");
            TimeUnit.SECONDS.sleep(3);
        }
        System.out.println("最后一次尝试释放锁！");
        mutexLock.release();
        // 可以模拟释放锁后睡眠一会，再开启一个实例看看锁是否能获取
        TimeUnit.SECONDS.sleep(20);
        System.out.println("释放锁成功！");
    }

    @Test
    @SneakyThrows
    public void semaphoreMutexLockTest(){
        // 不可重入互斥锁
        InterProcessLock semaphoreMutexLock = new InterProcessSemaphoreMutex(cf, "/root");
        forEachNumber(semaphoreMutexLock);
    }

    @Test
    @SneakyThrows
    public void readLockTest(){
        // 读写锁
        // 1. 可重入，写锁可降级为读锁，然后释放写锁，但读锁无法升级为写锁，读锁存在时无法获取写锁
        // 2. 写锁未被占有时，可以有多个读锁，一旦被占有，仅持有写锁的线程能获取读锁
        // 获取读锁
        InterProcessLock readLock = new InterProcessReadWriteLock(cf, "/root").readLock();
        forEachNumber(readLock);

    }

    @Test
    @SneakyThrows
    public void writeLockTest(){
        // 获取写锁
        InterProcessLock writeLock = new InterProcessReadWriteLock(cf, "/root").writeLock();
    }

    @Test
    @SneakyThrows
    public void writeToReadLockTest(){
        InterProcessReadWriteLock readWriteLock = new InterProcessReadWriteLock(cf, "/root");
        // 获取写锁
        InterProcessLock writeLock = readWriteLock.writeLock();
        System.out.println("尝试获取锁!");
        writeLock.acquire();
        System.out.println("获取锁成功！");
        for (int i = 0; i < 3; i++) {
            System.out.println("开始计数：" + i);
            TimeUnit.SECONDS.sleep(3);
        }
        // 锁重入获取读锁
        InterProcessLock readLock = readWriteLock.readLock();
        System.out.println("获取读锁并释放写锁！");
        readLock.acquire();
        writeLock.release();
        System.out.println("获取读锁并释放写锁成功！");
        System.out.println("锁降级完毕！");
        for (int i = 0; i < 3; i++) {
            System.out.println("开始计数：" + i);
            TimeUnit.SECONDS.sleep(3);
        }
        System.out.println("尝试释放读锁！");
        readLock.release();
        System.out.println("释放读锁成功！");
    }

    @Test
    @SneakyThrows
    public void multiLockTest(){
        List<String> paths = new ArrayList<>();
        paths.add("/root1");
        paths.add("/root2");
        // 多锁，即同时获取传入的节点上的锁，是将多个锁作为单个实体管理的容器
        // 因为是将多个节点的锁合成一个，因此若是单独获取某个节点上的锁还是可以获取的
        // 如 new InterProcessSemaphoreMutex(cf, "/root1").acquire() ==> true
        InterProcessLock multiLock = new InterProcessMultiLock(cf, paths);
        forEachNumber(multiLock);
    }

    @SneakyThrows
    private void forEachNumber(InterProcessLock lock){
        System.out.println("尝试获取锁!");
        lock.acquire();
        System.out.println("获取锁成功！");
        for (int i = 0; i < 10; i++) {
            System.out.println("开始计数：" + i);
            TimeUnit.SECONDS.sleep(3);
        }
        System.out.println("尝试释放锁！");
        lock.release();
        System.out.println("释放锁成功！");
    }

    @Test
    @SneakyThrows
    public void semaphoreV2LockTest(){
        /*
         * 基于信号量获取锁，比如：设置租约数为 10
         *      1. 则最多可以有10个线程各获取一个租约表示获取到节点的锁
         *      2. 或者 A 线程持有租约数 5（重复租约表示锁重入），B 线程租约数也为 5，不同线程的租约数要确保相同
         * 两种构造方式：
         *      1. 参数三表示租约数
         *      2. 参数三表示租约数的共享管理类，可监听租约数的变化，该类的参数三表示节点若未创建则指定其初始租约数
         */
        // InterProcessSemaphoreV2 v2Lock1 = new InterProcessSemaphoreV2(cf, "/root", 3);
        SharedCount sharedCount = new SharedCount(cf, "/root", 2);
        InterProcessSemaphoreV2 v2Lock1 = new InterProcessSemaphoreV2(cf, "/root", sharedCount);
        sharedCount.addListener(new SharedCountListener() {
            @Override
            public void countHasChanged(SharedCountReader sharedCount, int newCount) throws Exception {
                System.out.println("租约数变更，目前为：" + newCount);
            }

            @Override
            public void stateChanged(CuratorFramework client, ConnectionState newState) {
                System.out.println("状态变更，目前状态" + newState);
            }
        });
        sharedCount.start();
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CountDownLatch cdl = new CountDownLatch(2);

        Runnable r1 = () -> {
            Lease lease = null;
            try {
                // 获取多个租约
                // List<Lease> leases = v2Lock1.acquire(2);
                TimeUnit.SECONDS.sleep(1);
                lease = v2Lock1.acquire();
                System.out.println("获取租约1！");
                TimeUnit.SECONDS.sleep(3);
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if(lease != null){
                    v2Lock1.returnLease(lease);
                }
                System.out.println("释放租约1");
                cdl.countDown();
            }
        };

        Runnable r2 = () -> {
            Lease lease = null;
            try {
                lease = v2Lock1.acquire();
                System.out.println("获取租约2！");
                // sharedCount.setCount(1);
                TimeUnit.SECONDS.sleep(3);
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if(lease != null){
                    v2Lock1.returnLease(lease);
                }
                System.out.println("释放租约1");
                cdl.countDown();
            }
        };

        executorService.submit(r1);
        executorService.submit(r2);
        cdl.await();
        // 计数器要关闭，但只是移除了监听器，节点与数据依旧保留
        sharedCount.close();
        System.out.println("结束执行");
    }



}
