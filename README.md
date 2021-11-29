# 欢迎使用好用网盘

> 本项目用于大数据实训课程大作业
>
> 由桂林电子科技大学，1900300819卢林军编写
>

# Important!!!

```
版权所有（c）2021 ZeroRains，pommespeter

反课设抄袭许可证-version 0.6

1、如果你想使用本项目的代码来交课设，
那么请尽可能在完善本项目的基础上进行使用，
因为本项目其实还有部分模块写得比较懒需要继续完善。(BUG还很多）
因为本项目代码比较乱，复用性太差，可读性不强，稳定性太差，所以参考价值不大。

2、一行不改甚至只改个100-200行代码就加上学号和名字说是你做的，
这种情况是是不被允许的！

3、上文所指的课设，包括诸如毕设，课程设计，课程结束考核等，
诸如此类情况而违反上述条例使用该程序的代码，都是不被允许的。
```

# 项目说明

## 启动方式

在idea中使用maven配置所有的依赖项，然后直接运行src/main/java/com.lulinjun.hdfs/StartApp.java即可运行

运行后点击[http://127.0.0.1:8084/login](http://127.0.0.1:8084/login)进入登录页

## 项目介绍

本项目使用Springboot+Thymeleaf+Layui完成前后端的搭建

使用本地数据库mysql，对应的建表信息查看usertable.sql文件

存储方式使用hadoop的HDFS平台进行文件存储，需要自己配置好对应的虚拟机环境或docker

**主页展示**

![image-20211129172233653](https://gitee.com/zerorains/drawing-bed/raw/master/image-20211129172233653.png)

**修改信息页**

![image-20211129172253279](https://gitee.com/zerorains/drawing-bed/raw/master/image-20211129172253279.png)

**文件收藏页**

![image-20211129172324321](https://gitee.com/zerorains/drawing-bed/raw/master/image-20211129172324321.png)

 **文件夹分享**

![image-20211129172418400](https://gitee.com/zerorains/drawing-bed/raw/master/image-20211129172418400.png)

**分享页面**

![image-20211129172454220](https://gitee.com/zerorains/drawing-bed/raw/master/image-20211129172454220.png)

**注册登录**

![image-20211129172511959](https://gitee.com/zerorains/drawing-bed/raw/master/image-20211129172511959.png)

![image-20211129172522635](https://gitee.com/zerorains/drawing-bed/raw/master/image-20211129172522635.png)
