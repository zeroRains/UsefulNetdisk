package com.lulinjun.hdfs.utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;

public class TestConnectHDFS {
    public static void main(String[] args) throws IOException {
//        配置HDFS使用的账户
        System.setProperty("HADOOP_USER_NAME","hadoop");
//        创建连接配置文件
        Configuration conf = new Configuration();
//        设置对应的FsImage对应的端口
        conf.set("fs.defaultFS","hdfs://192.168.16.129:9000");
//        创建文件系统
        FileSystem client = FileSystem.get(conf);
//        创建爱你文件夹
        client.mkdirs(new Path("lulinjun_work1"));
//        关闭文件系统
        client.close();
    }
}
