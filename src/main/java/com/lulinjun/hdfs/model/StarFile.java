package com.lulinjun.hdfs.model;

public class StarFile {
    private String uid;
    private String path;

    public StarFile() {
    }

    public StarFile(String uid, String path) {
        this.uid = uid;
        this.path = path;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
