package com.snicesoft.freefir.utils;

import java.io.File;

/**
 * Created by zhe on 2016/10/5.
 */
public class FileUtil {
    public static void mkdirs(String destPath) {
        new File(destPath).mkdirs();
    }
}
