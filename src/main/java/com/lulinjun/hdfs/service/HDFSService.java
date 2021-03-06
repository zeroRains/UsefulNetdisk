package com.lulinjun.hdfs.service;

import com.lulinjun.hdfs.model.HDFSFile;
import com.lulinjun.hdfs.utils.HDFSTool;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.yarn.api.records.LocalResourceVisibility;
import org.apache.tomcat.util.http.fileupload.impl.FileUploadIOException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
public class HDFSService {
    private String hdfsPath = "hdfs://192.168.16.129:9000/user/hadoop/";

    //    上传文件
    public void copyFile(String user, String local) throws IOException {
        //        配置HDFS使用的账户
        System.setProperty("HADOOP_USER_NAME", "hadoop");
//        创建连接配置文件
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(hdfsPath + user + "/"), conf);

        fs.copyFromLocalFile(new Path(local), new Path(hdfsPath + user + "/"));
        System.out.println("将文件从" + local + "复制到" + hdfsPath + user + "/");
        fs.close();
    }

    public void copyDir(String src, String dst) {
        System.setProperty("HADOOP_USER_NAME", "hadoop");
//        创建连接配置文件
        Configuration conf = new Configuration();
        try {
            Path srcPath = new Path(hdfsPath + src);
            Path dstPath = new Path(hdfsPath + dst, srcPath.getName());
            FileSystem sf = FileSystem.get(URI.create(hdfsPath + src), conf);
            FileSystem df = FileSystem.get(URI.create(hdfsPath + dst), conf);
            FileUtil.copy(sf, srcPath, df, dstPath, false, conf);
            System.out.println("复制成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void deleteFile(String delPath) {
        delPath = hdfsPath + delPath;
        //        配置HDFS使用的账户
        System.setProperty("HADOOP_USER_NAME", "hadoop");
//        创建连接配置文件
        Configuration conf = new Configuration();
        try {
            FileSystem fs = FileSystem.get(URI.create(delPath), conf);
            fs.deleteOnExit(new Path(delPath));
            fs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //    创建文件夹
    public void createDir(String path) throws IOException {
        //        配置HDFS使用的账户
        System.setProperty("HADOOP_USER_NAME", "hadoop");
//        创建连接配置文件
        Configuration conf = new Configuration();

        String index = hdfsPath + path;
        FileSystem fs = FileSystem.get(URI.create(index), conf);
        fs.mkdirs(new Path(index));
        System.out.println("创建成功" + path + "成功");

    }

    //    创建新文件
    public void createFile(String filePath) {
        System.setProperty("HADOOP_USER_NAME", "hadoop");
//        创建连接配置文件
        Configuration conf = new Configuration();
        try {
            String index = hdfsPath + filePath;
            FileSystem fs = FileSystem.get(URI.create(index), conf);
            Path path = new Path(index);
            if (!fs.exists(path)) {
                fs.create(path);
                System.out.println("创建" + filePath + "成功");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * 获取文件输入流
     *
     * @param strpath 文件的hdfs绝对路径
     * @return
     * @throws IOException
     */
    public InputStream getFileInputStreamForPath(String strpath) throws IOException {
        //指定当前的Hadoop的用户
        System.setProperty("HADOOP_USER_NAME", "hadoop");
        Configuration conf = new Configuration();
        conf.set("fs.default.name", hdfsPath + strpath);
        FileSystem fs = FileSystem.get(conf);
        return fs.open(new Path(hdfsPath + strpath));
    }

    public boolean moveFile(String path, String newPath) throws IOException {
        boolean result = false;
        path = hdfsPath + path;
        newPath = hdfsPath + newPath;
        System.setProperty("HADOOP_USER_NAME", "hadoop");
        Configuration conf = new Configuration();
        conf.set("fs.default.name", hdfsPath);
        FileSystem fs = FileSystem.get(conf);
        if (fs.exists(new Path(path)) && !fs.exists(new Path(newPath)))
            result = fs.rename(new Path(path), new Path(newPath));
        else System.out.println("文件：" + newPath + "或" + path + "被占用了");
        return result;
    }


    //    查看文件
    public String readFile(String path) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(getFileInputStreamForPath(hdfsPath + path)));
        System.out.println(hdfsPath + path + "的内容为：");
        String str2, result = "";
        while ((str2 = br.readLine()) != null) {
            result += str2 + "\n";
        }
        System.out.println(result);
        return result;
    }


    /**
     * 遍历HDFS上的文件和目录
     */
    public List<HDFSFile> getDirectoryFromHdfs() {
        List<HDFSFile> res = new ArrayList<>();
        //指定当前的Hadoop的用户
        System.setProperty("HADOOP_USER_NAME", "hadoop");
        String dst = hdfsPath;
        Configuration conf = new Configuration();
        FileStatus[] list = null;
        try {
            FileSystem fs = FileSystem.get(URI.create(dst), conf);
            list = fs.listStatus(new Path(dst));
            if (list != null)
                for (FileStatus f : list) {
//                    System.out.printf("name: %s, folder: %s, size: %d\n", f.getPath().getName(), f.isDir(), f.getLen());
                    res.add(new HDFSFile(f.getPath().getName(), f.isDir() ? "文件夹" : "文件", f.getLen()));
                }
            fs.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return res;
    }

    /**
     * 遍历HDFS上的文件和目录
     */
    public List<HDFSFile> getDirectoryFromHdfs(String path) throws FileNotFoundException, IOException {
        List<HDFSFile> res = new ArrayList<>();
        //指定当前的Hadoop的用户
        System.setProperty("HADOOP_USER_NAME", "hadoop");
        String dst = hdfsPath + path;
        Configuration conf = new Configuration();
        FileStatus[] list = null;
        try {
            FileSystem fs = FileSystem.get(URI.create(dst), conf);
            list = fs.listStatus(new Path(dst));
            if (list != null)
                for (FileStatus f : list) {
//                    System.out.printf("name: %s, folder: %s, size: %d\n", f.getPath().getName(), f.isDir(), f.getLen());
                    res.add(new HDFSFile(f.getPath().getName(), f.isDir() ? "文件夹" : "文件", f.getLen()));
                }
            fs.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return res;
    }
}
