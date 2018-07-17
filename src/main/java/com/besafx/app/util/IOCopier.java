package com.besafx.app.util;

import java.io.*;
import java.util.List;

public class IOCopier {

    public static void joinFiles(File destination, List<File> sources) throws IOException {
        OutputStream output = null;
        try {
            output = createAppendableStream(destination);
            for (File source : sources) {
                appendFile(output, source);
            }
        } finally {
            com.besafx.app.util.IOUtils.closeQuietly(output);
        }
    }

    private static BufferedOutputStream createAppendableStream(File destination)
            throws FileNotFoundException {
        return new BufferedOutputStream(new FileOutputStream(destination, true));
    }

    private static void appendFile(OutputStream output, File source)
            throws IOException {
        InputStream input = null;
        try {
            input = new BufferedInputStream(new FileInputStream(source));
            com.besafx.app.util.IOUtils.copy(input, output);
        } finally {
            com.besafx.app.util.IOUtils.closeQuietly(input);
        }
    }

}
