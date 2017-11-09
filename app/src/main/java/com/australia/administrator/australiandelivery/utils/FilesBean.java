package com.australia.administrator.australiandelivery.utils;

import java.io.File;

/**
 * Created by gsd on 16-12-29.
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
