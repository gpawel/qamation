package org.qamation.utils;

/**
 * Created by pavel.gouchtchine on 05/15/2017.
 */
import java.io.*;
import java.util.ArrayList;
import java.util.function.Consumer;
import org.qamation.utils.RegExpUtils;

/**
 * Created by Pavel.Gouchtchine on 08/22/2016.
 *
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
public class ReadTextFile {

    private String inputFilePath;
    private File inputFile;
    private String[] regExpFilters;
    private boolean shouldFilter;

    public ReadTextFile(String inputPath) {
        this.inputFilePath = inputPath;
        this.inputFile = new File(inputFilePath);
        this.regExpFilters = new String[0];
        this.shouldFilter = false;
    }

    public ReadTextFile(String inputPath, String[] regExps) {
        this.inputFilePath = inputPath;
        this.inputFile = new File(inputFilePath);
        this.regExpFilters = regExps;
        this.shouldFilter = true;
    }


    public void getLines(Consumer<String> consumer) {
        getContent(inputFile,consumer);
    }


    private void getContent(File file, Consumer<String> consumer) {
        if (file.isDirectory()) processDirectory(file, consumer);
        else processFile(file, consumer);
    }

    private void processDirectory(File file, Consumer<String> consumer){
        File[] fileNames = file.listFiles();
        for (File f : fileNames) {
            getContent(f, consumer);
        }
    }

    private void processFile(File file, Consumer<String> consumer) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String filePath = file.getPath();
            String fileLine;
            while ((fileLine = br.readLine()) != null) {
                if (shouldIncludeLine(fileLine)) {
                    String line = filePath + "\t" + fileLine;
                    consumer.accept(line);
                }
                else continue;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean shouldIncludeLine(String fileLine) {
        if (shouldFilter) {
            for (String regExp : regExpFilters) {
                RegExpUtils ru = new RegExpUtils(fileLine,regExp);
                if (ru.isInputMatches()) return true;
                return false;
            }
        }
        return true;
    }


}
