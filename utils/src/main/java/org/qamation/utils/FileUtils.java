package org.qamation.utils;


import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.nio.file.*;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by Pavel on 2017-03-25.
 */
public class FileUtils {
    public final static String FILE_SEPARATOR = System.getProperty("file.separator");

    public static Path copyFileToSameFolder(String sourceFilePath, String newfileName) {
        File f = new File(sourceFilePath);
        if (f.isDirectory()) throw new RuntimeException("Given path: "+sourceFilePath+" should lead to a file.");
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

    public static String createTempFile(String origFileName) {
        String prefix = generateFileNamePrefix();
        return createTempFile(origFileName,prefix);
    }

    public static String createTempFile(String origFileName, String tempFileNamePrefix) {
        String suffix = FileUtils.getFileNameExtention(origFileName);
        String tempFileName = tempFileNamePrefix+suffix;
        Path p = FileUtils.copyFileToSameFolder(origFileName,tempFileName);
        return p.toString();
    }



    public static String[] listFilesInFolder(String root) {
        File file = new File(root);
        root = file.getPath(); // to convert c:/tmp/mq into c:\tmp\mq if needed.
        ArrayList<String> list = new ArrayList<String>();
        if (file.exists()) {
                processFile(root, file, list);
        }
        else {
            throw new RuntimeException("There is no file of dirrectory at: "+root);
        }
        return list.toArray(new String[]{});
    }

    public static Properties loadPropertiesFile(String path) {
        try {
            Reader reader = new FileReader(path);
            Properties props = new Properties();
            props.load(reader);
            System.setProperties(props);
            reader.close();
            return props;

        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Unable to load properties file: "+path,ex);
        }
    }

    private static void processFile(String startPath, File file, ArrayList<String> list) {
        if (file.isDirectory()) {
            for (String fileName : file.list()) {
                String path = startPath + FILE_SEPARATOR + fileName;
                processFile(path, new File(path), list);
            }
        }
        else {
            list.add(file.getPath());
        }


    }


    private static String generateFileNamePrefix() {
        SecureRandom sr = new SecureRandom();
        long l = sr.nextLong();
        return String.valueOf(l);
    }




}
