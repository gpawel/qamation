package org.qamation.utils;

import java.io.File;
import java.nio.file.*;
import static java.nio.file.StandardCopyOption.*;

/**
 * Created by Pavel on 2017-03-25.
 */
public class FileUtils {
    public final static String FILE_SEPARATOR = System.getProperty("file.separator");

    public static Path copyFileToSameFolder(String sourceFilePath, String newfileName) {
        File f = new File(sourceFilePath);
        if (f.isDirectory()) throw new RuntimeException("Given path should lead to a file.");
        String fileName = getFileName(f.getAbsolutePath());
        String fileExt = getFileNameExtention(fileName);
        String filePath = getPathToFile(f.getAbsolutePath());
        try {
            return  Files.copy(f.toPath(), Paths.get(filePath,newfileName), StandardCopyOption.REPLACE_EXISTING);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }

    public static String getFileName(String absolutePath) {
        Path p = Paths.get(absolutePath);
        return p.getFileName().toString();
    }

    public static String getPathToFile(String absolutePath) {
        Path p = Paths.get(absolutePath);
        return p.getParent().toString();
    }

    public static String getFileNameExtention(String fileName) {
        String ext = fileName.substring(fileName.lastIndexOf("."));
        return ext;
    }

}
