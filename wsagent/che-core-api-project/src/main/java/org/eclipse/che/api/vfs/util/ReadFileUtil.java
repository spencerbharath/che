package org.eclipse.che.api.vfs.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Vitalii Parfonov
 */

public class ReadFileUtil {


    public static Line getLine(File file, int offset) throws IOException {
        try (LineNumberReader r = new LineNumberReader(new FileReader(file))) {
            int count = 0;
            int read = 0;
            int startPosition = 0;
            while (read != -1 && count < offset) {
                read = r.read();
                final char[] chars = Character.toChars(read);
                if (System.lineSeparator().equals(new String(chars))) {
                    startPosition = count;
                }
                count++;
            }

            if (count == offset) {
                int lineNumber = r.getLineNumber();
                String s = r.readLine();
                int t = offset + s.length();
                int x = t - startPosition;
                r.close();

                RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
                byte [] chars = new byte[x];
                randomAccessFile.seek(startPosition);
                randomAccessFile.read(chars, 0, x);
                return new Line(lineNumber, new String(chars));
            } else {
                throw new IOException("File is not long enough");
            }
        }

    }

    public static class Line {
        private int lineNumber;
        private String lineContent;

        Line(int lineNumber, String lineContent) {
            this.lineNumber = lineNumber;
            this.lineContent = lineContent;
        }


        public String getLineContent() {
            return lineContent;
        }

        public int getLineNumber() {
            return lineNumber;
        }
    }
}
