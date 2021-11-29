package com.lulinjun.hdfs.utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;

import java.io.*;
import java.net.URI;

public class HDFSTool {
    private String hdfsPath = "hdfs://192.168.16.129:9000/user/hadoop/";

    public static void main(String[] args) throws IOException, InterruptedException {

        HDFSTool hdfsTool = new HDFSTool();
        hdfsTool.copyDir( "/star_root/work_test1/","/lulinjun_work1/");
//        hdfsTool.copyFile("D:\\gitLearning\\lulinjun-hdfs\\src\\main\\java\\com\\lulinjun\\hdfs\\test.txt");
//        hdfsTool.createFile("lulinjun_test233.txt");
////        Thread.sleep(3000);
//        hdfsTool.deleteFile(hdfsTool.hdfsPath + "test233.txt");
////        Thread.sleep(3000);
//        hdfsTool.createDir("lulinjun_create");
//        hdfsTool.createFile("lulinjun_test.txt");
//        hdfsTool.getDirectoryFromHdfs();
//        hdfsTool.readFile("test.txt");

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

    //    上传文件
    public void copyFile(String local) throws IOException {
        //        配置HDFS使用的账户
        System.setProperty("HADOOP_USER_NAME", "hadoop");
//        创建连接配置文件
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);

        fs.copyFromLocalFile(new Path(local), new Path(hdfsPath));
        System.out.println("将文件从" + local + "复制到" + hdfsPath);
        fs.close();
    }

    public void deleteFile(String delPath) {
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
    public void createDir(String path) {
        //        配置HDFS使用的账户
        System.setProperty("HADOOP_USER_NAME", "hadoop");
//        创建连接配置文件
        Configuration conf = new Configuration();
        try {
            String index = hdfsPath + path;
            FileSystem fs = FileSystem.get(URI.create(index), conf);

            if (!fs.exists(new Path(index))) {
                fs.mkdirs(new Path(index));
                System.out.println("创建成功" + path + "成功");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        conf.set("fs.default.name", strpath);
        FileSystem fs = FileSystem.get(conf);
        return fs.open(new Path(strpath));
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
    public FileStatus[] getDirectoryFromHdfs() {
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
                    System.out.printf("name: %s, folder: %s, size: %d\n", f.getPath().getName(), f.isDir(), f.getLen());

                }
            fs.close();
        } catch (Exception ex) {

        }
        return list;
    }

    /**
     * 遍历HDFS上的文件和目录
     */
    public FileStatus[] getDirectoryFromHdfs(String path) throws FileNotFoundException, IOException {

        //指定当前的Hadoop的用户
        System.setProperty("HADOOP_USER_NAME", "hadoop");
        FileStatus[] list = null;
        try {
            Configuration conf = new Configuration();
            String dst = hdfsPath;
            if (path.length() > 0) {
                dst = path;
            }
            FileSystem fs = FileSystem.get(URI.create(dst), conf);
            list = fs.listStatus(new Path(dst));
            if (list != null)
                for (FileStatus f : list) {
                    System.out.printf("name: %s, folder: %s, size: %d\n", f.getPath().getName(), f.isDir(), f.getLen());
                }
            fs.close();

        } catch (Exception ex) {

        }
        return list;
    }

}
