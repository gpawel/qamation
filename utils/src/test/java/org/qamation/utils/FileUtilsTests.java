package org.qamation.utils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Path;

/**
 * Created by Pavel on 2017-03-25.
 */
public class FileUtilsTests {
    String separator = File.pathSeparator;
    String tempFolder = System.getProperty("user.dir");
    String prefix = "tempFile";
    String suffix = ".abc";
    File f1;

    @Before
    public void setUp() {
        try {
            f1 = File.createTempFile(prefix,suffix,new File(tempFolder));
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @After
    public void tearDown() {
        f1.deleteOnExit();
    }

    @Test
    public void getFilePathFromFile() {
        String filePath = FileUtils.getPathToFile(f1.getAbsolutePath());
        Assert.assertEquals(tempFolder,filePath);
    }

    @Test
    public void getFileName() {
        String filename = FileUtils.getFileName(f1.getAbsolutePath());
        Assert.assertTrue(filename.startsWith(prefix));
    }

    @Test
    public void getFileExt() {
        String fileext = FileUtils.getFileNameExtention(f1.getAbsolutePath());
        Assert.assertEquals(suffix,fileext);
    }

    @Test
    public void copyFile() {
        Path r = FileUtils.copyFileToSameFolder(f1.getPath(),"copy_file.def");
        Assert.assertEquals(tempFolder,r.getParent().toString());
        Assert.assertEquals(tempFolder+FileUtils.FILE_SEPARATOR +"copy_file.def",r.toString());
    }

}
