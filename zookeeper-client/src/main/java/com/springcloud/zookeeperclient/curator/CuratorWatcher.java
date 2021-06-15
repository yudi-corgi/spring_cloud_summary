package com.springcloud.zookeeperclient.curator;

import lombok.SneakyThrows;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author CDY
 * @date 2021/6/14
 * @description
 */
public class CuratorWatcher {

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
        // TODO 对象已弃用，找替代 CuratorCache，continue...
        CuratorCache curatorCache = CuratorCache.build(cf, "/watcher", CuratorCache.Options.SINGLE_NODE_CACHE);
    }

    @Test
    @SneakyThrows
    public void pathChildrenCacheTest(){
        // TODO 对象已弃用，找替代 CuratorCache，continue...
    }

    @Test
    @SneakyThrows
    public void treeCacheTest(){
        // TODO 对象已弃用，找替代 CuratorCache，continue...
    }
}
