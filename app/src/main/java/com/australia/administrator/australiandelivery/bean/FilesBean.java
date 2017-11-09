package com.australia.administrator.australiandelivery.bean;

import java.io.File;

/**
 * Created by Administrator on 2017/6/13.
 */

public class FilesBean {
    private String key;
    private String name;
    private File file;

    public FilesBean(String key, String name, File file) {
        this.key = key;
        this.name = name;
        this.file = file;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public File getFile() {
        return file;
    }
}
