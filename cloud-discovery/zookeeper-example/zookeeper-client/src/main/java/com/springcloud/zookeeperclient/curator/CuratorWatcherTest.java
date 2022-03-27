package com.springcloud.zookeeperclient.curator;

import lombok.SneakyThrows;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.apache.curator.framework.recipes.cache.CuratorCacheListener;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @author CDY
 * @date 2021/6/14
 * @description
 */
public class CuratorWatcherTest {

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
    public void nodeCacheTest(){
        // CuratorCache 用于构建监听器和响应事件，缓存节点数据
        CuratorCache curatorCache = CuratorCache.builder(cf, "/node").build();
        // 监听节点的增删改事件
        CuratorCacheListener ccl = CuratorCacheListener.builder().forNodeCache(() -> {
            System.out.println("节点已被更新，进行业务处理...");
        }).build();
        curatorCache.listenable().addListener(ccl);
        curatorCache.start();
        curatorCache.get("/node").ifPresent(childData -> System.out.println("节点数据：" + new String(childData.getData(), StandardCharsets.UTF_8)));
        TimeUnit.SECONDS.sleep(100);
    }

    @Test
    @SneakyThrows
    public void pathChildrenCacheTest(){
        CuratorCache curatorCache = CuratorCache.builder(cf, "/child").build();
        // 监听节点及子孙节点增删改事件，forPathChildrenCache() 第一个参数路径设置无效
        // 以 CuratorCache.builder() 指定的路径为准
        CuratorCacheListener ccl = CuratorCacheListener.builder()
                .forPathChildrenCache("/node", cf, (client, event) -> {
                    System.out.println("事件类型："+event.getType());
                    System.out.println("数据："+event.getData());
                }).build();
        curatorCache.listenable().addListener(ccl);
        curatorCache.start();
        TimeUnit.SECONDS.sleep(1000);
    }

    @Test
    @SneakyThrows
    public void treeCacheTest(){
        CuratorCache curatorCache = CuratorCache.builder(cf, "/tree").build();
        // 监听节点及其子节点的增删改事件
        CuratorCacheListener ccl = CuratorCacheListener.builder()
                .forTreeCache( cf, (client, event) -> {
                    System.out.println("事件类型："+event.getType());
                    System.out.println("数据："+event.getData());
                }).build();
        curatorCache.listenable().addListener(ccl);
        curatorCache.start();
        TimeUnit.SECONDS.sleep(1000);
    }

    @Test
    @SneakyThrows
    public void changeOrCreateTest(){
        CuratorCache curatorCache = CuratorCache.builder(cf, "/node").build();
        // 监听节点及子孙节点创建事件
        CuratorCacheListener create = CuratorCacheListener.builder().forCreates(childData -> {
            System.out.println("新创建的节点：" + childData.getPath());
        }).build();
        // 监听节点及子孙节点修改事件，只能针对客户端修改API有效果
        // sh / cmd 命令行增改都无法触发（原因应当是事件类型不匹配，未据此重新测试）
        CuratorCacheListener changes = CuratorCacheListener.builder().forChanges( (oldNode, childData) -> {
            System.out.println("节点旧数据：" + new String(oldNode.getData(), StandardCharsets.UTF_8));
            System.out.println("节点新数据：" + new String(childData.getData(), StandardCharsets.UTF_8));
        }).build();
        // 监听节点及子孙节点删除事件
        CuratorCacheListener delete = CuratorCacheListener.builder().forDeletes(childData -> {
            System.out.println("删除的节点：" + childData.getPath());
        }).build();
        // 监听节点及子孙节点增改事件，只能针对客户端修改API后响应 changes 事件
        // sh / cmd 命令行增改都无法触发（原因应当是事件类型不匹配，未据此重新测试）
        CuratorCacheListener ccl = CuratorCacheListener.builder()
                .forCreatesAndChanges((oldNode, node) -> {
                    System.out.println("节点旧数据："+new String(oldNode.getData(), StandardCharsets.UTF_8));
                    System.out.println("节点新(或新创建的)数据："+new String(node.getData(), StandardCharsets.UTF_8));
                }).build();
        // curatorCache.listenable().addListener(create);
        curatorCache.listenable().addListener(changes);
        // curatorCache.listenable().addListener(delete);
        // curatorCache.listenable().addListener(ccl);
        curatorCache.start();
        TimeUnit.SECONDS.sleep(2);
        cf.setData().forPath("/node","asd".getBytes(StandardCharsets.UTF_8));
        TimeUnit.SECONDS.sleep(100);
    }

}
