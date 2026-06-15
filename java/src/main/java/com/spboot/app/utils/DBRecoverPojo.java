package com.spboot.app.utils;

import java.io.File;
import java.io.Serializable;

public class DBRecoverPojo implements Serializable {

    private String filename;
    private String lastTime;
    private String filepath;
    private Long filesize;

    public DBRecoverPojo(String filepath, String file, String lastTime) {
        setFilepath(filepath);
        setFilename(file);
        setLastTime(lastTime);
    }

    public DBRecoverPojo(String file) {
        File file1 = new File(file);
        setFile(file1);
    }

    public DBRecoverPojo(File file) {
        setFile(file);
    }

    private void setFile(File file) {
        setFilename(file.getName());
        setLastTime(Info.date("yyyy-MM-dd HH:mm:ss", file.lastModified() / 1000));
        setFilepath(file.getAbsolutePath());
        setFilesize(file.length());
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public Long getFilesize() {
        return filesize;
    }

    public void setFilesize(Long filesize) {
        this.filesize = filesize;
    }
}
