package org.qamation.jmeter.apache.junit;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.functions.InvalidVariableException;
import org.apache.jmeter.util.JMeterUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Locale;
import java.util.MissingResourceException;

import static org.junit.Assert.fail;

/**
 * Created by Pavel on 2017-06-05.
 * This is copy of org.apache.jmeter.junit.JMeterTestCase;
 */
public class JMeterTestCase {
    private static final String filePrefix;


    /*
     * If not running under AllTests.java, make sure that the properties (and
     * log file) are set up correctly.
     *
     * N.B. In order for this to work correctly, the JUnit test must be started
     * in the bin directory, and all the JMeter jars (plus any others needed at
     * run-time) need to be on the classpath.
     *
     */
    static {
        if (JMeterUtils.getJMeterProperties() == null) {
            String file = "jmeter.properties";
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            URL url = classLoader.getSystemResource(file);
            file = url.getPath();
            File f = new File(file);
            if (!f.canRead()) {
                System.out.println("Can't find " + file + " - trying bin directory");
                file = "bin/" + file;// JMeterUtils assumes Unix-style separators
                filePrefix = "bin/";
            } else {
                filePrefix = "";
            }
            // Used to be done in initializeProperties
            String home=new File(System.getProperty("user.dir"),filePrefix).getParent();
            System.out.println("Setting JMeterHome: "+home);
            JMeterUtils.setJMeterHome(home);
            System.setProperty("jmeter.home", home); // needed for scripts
            JMeterUtils jmu = new JMeterUtils();
            try {
                jmu.initializeProperties(file);
            } catch (MissingResourceException e) {
                System.out.println("** Can't find resources - continuing anyway **");
            }
            System.out.println("JMeterVersion="+JMeterUtils.getJMeterVersion());
            logprop("java.version");
            logprop("java.vm.name");
            logprop("java.vendor");
            logprop("java.home");
            logprop("file.encoding");
            // Display actual encoding used (will differ if file.encoding is not recognised)
            System.out.println("default encoding="+Charset.defaultCharset());
            logprop("user.home");
            logprop("user.dir");
            logprop("user.language");
            logprop("user.region");
            logprop("user.country");
            logprop("user.variant");
            System.out.println("Locale="+Locale.getDefault().toString());
            logprop("java.class.version");
            logprop("java.awt.headless");
            logprop("os.name");
            logprop("os.version");
            logprop("os.arch");
            logprop("java.class.path");
        } else {
            filePrefix = "";
        }
    }

    private static void logprop(String prop) {
        System.out.println(prop + "=" + System.getProperty(prop));
    }

    // Helper method to find a file
    protected static File findTestFile(String file) {
        File f = new File(file);
        if (filePrefix.length() > 0 && !f.isAbsolute()) {
            f = new File(filePrefix, file);// Add the offset
        }
        return f;
    }

    // Helper method to find a test path
    protected static String findTestPath(String file) {
        File f = new File(file);
        if (filePrefix.length() > 0 && !f.isAbsolute()) {
            return filePrefix + file;// Add the offset
        }
        return file;
    }

    protected static final org.slf4j.Logger testLog = org.slf4j.LoggerFactory. getLogger(JMeterTestCase.class);

    protected void checkInvalidParameterCounts(AbstractFunction func, int minimum)
            throws Exception {
        Collection<CompoundVariable> parms = new LinkedList<>();
        for (int c = 0; c < minimum; c++) {
            try {
                func.setParameters(parms);
                fail("Should have generated InvalidVariableException for " + parms.size()
                        + " parameters");
            } catch (InvalidVariableException ignored) {
            }
            parms.add(new CompoundVariable());
        }
        func.setParameters(parms);
    }

    protected void checkInvalidParameterCounts(AbstractFunction func, int min,
                                               int max) throws Exception {
        Collection<CompoundVariable> parms = new LinkedList<>();
        for (int count = 0; count < min; count++) {
            try {
                func.setParameters(parms);
                fail("Should have generated InvalidVariableException for " + parms.size()
                        + " parameters");
            } catch (InvalidVariableException ignored) {
            }
            parms.add(new CompoundVariable());
        }
        for (int count = min; count <= max; count++) {
            func.setParameters(parms);
            parms.add(new CompoundVariable());
        }
        parms.add(new CompoundVariable());
        try {
            func.setParameters(parms);
            fail("Should have generated InvalidVariableException for " + parms.size()
                    + " parameters");
        } catch (InvalidVariableException ignored) {
        }
    }

    public static void assertPrimitiveEquals(boolean expected, boolean actual) {
        org.junit.Assert.assertEquals(Boolean.valueOf(expected), Boolean.valueOf(actual));
    }
}