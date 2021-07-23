package com.example.practise.utils;

import java.io.File;

public class FileUtil {
    public static String getDirs(String path)
    {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return path;
    }
}
