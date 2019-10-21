package com.mitrais.atm.utility;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;

public class FileUtil {

    public static String getResourcePath() {
        FileUtil fu = new FileUtil();
        return fu.resourcePath();
    }

    private String resourcePath() {
        String path = "src/main/resources/csv/";
        File file = new File(path);
        return file.getAbsolutePath();
    }
}
