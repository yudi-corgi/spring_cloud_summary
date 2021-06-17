package com.springcloud.zookeeperclient.primitive;

import lombok.SneakyThrows;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

/**
 * @author CDY
 * @date 2021/6/14
 * @description  分布式唯一 ID 生成，创建临时有序节点，有序节点生成后的序号截取出来后即可作为全局唯一 ID，因为每创建顺序节点，其序号是全局递增的
 */
public class ZkGloballyUniqueIdGenerator {

    @SneakyThrows
    public static void main(String[] args) {
        ZooKeeper zooKeeper = ZkConfig.connection();
        for (int i = 0; i < 100; i++) {
            String path = zooKeeper.create("/uniqueId_", new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            // 有序节点的格式为节点 path + 0000000001 ，从 1 开始，这里是直接根据 _ 下标截取，实际开发根据自身业务做判断截取序号即可
            System.out.println(path.substring(path.indexOf("_") + 1));
        }
    }

}


