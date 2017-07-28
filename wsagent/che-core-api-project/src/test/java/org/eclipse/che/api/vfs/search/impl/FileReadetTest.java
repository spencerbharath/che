/*******************************************************************************
 * Copyright (c) 2012-2017 Codenvy, S.A.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Codenvy, S.A. - initial API and implementation
 *******************************************************************************/
package org.eclipse.che.api.vfs.search.impl;

import com.google.common.base.Optional;

import org.apache.commons.io.FileUtils;
import org.eclipse.che.api.vfs.ArchiverFactory;
import org.eclipse.che.api.vfs.VirtualFile;
import org.eclipse.che.api.vfs.VirtualFileFilter;
import org.eclipse.che.api.vfs.VirtualFileSystem;
import org.eclipse.che.api.vfs.impl.memory.MemoryVirtualFileSystem;
import org.eclipse.che.api.vfs.search.QueryExpression;
import org.eclipse.che.api.vfs.search.SearchResult;
import org.eclipse.che.commons.lang.IoUtil;
import org.eclipse.che.commons.lang.NameGenerator;
import org.mockito.ArgumentMatcher;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

@SuppressWarnings("Duplicates")
public class FileReadetTest {
    private static final String[] TEST_CONTENT = {
            "Apollo set several major human spaceflight milestones",
            "Maybe you should think twice",
            "To be or not to be beeeee lambergeeene",
            "In early 1961, direct ascent was generally the mission mode in favor at NASA",
            "Time to think"
    };

    String TEST_CONTENT2 = "Apollo set several major human spaceflight milestones,\n" +
                           "Maybe you should think twice,\n" +
                           "To be or not to be beeeee lambergeeene,\n" +
                           "In early 1961, direct ascent was generally the mission mode in favor at NASA,\n" +
                           "Time to think";

    private File                                         indexDirectory;
    private VirtualFileFilter                            filter;
    private FSLuceneSearcher                             searcher;
    private AbstractLuceneSearcherProvider.CloseCallback closeCallback;

    @BeforeMethod
    public void setUp() throws Exception {
        File targetDir = new File(Thread.currentThread().getContextClassLoader().getResource(".").getPath()).getParentFile();
        indexDirectory = new File(targetDir, NameGenerator.generate("index-", 4));
        assertTrue(indexDirectory.mkdir());

        filter = mock(VirtualFileFilter.class);
        when(filter.accept(any(VirtualFile.class))).thenReturn(false);

        closeCallback = mock(AbstractLuceneSearcherProvider.CloseCallback.class);
        searcher = new FSLuceneSearcher(indexDirectory, filter, closeCallback);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        searcher.close();
        IoUtil.deleteRecursive(indexDirectory);
    }


    @Test
    public void searchesByFullTextAndFileName2() throws Exception {
        final Path tempFile = Files.createTempFile("my", "_test");
        Files.write(tempFile, TEST_CONTENT2.getBytes());
        int offset = 56;

        try (LineNumberReader r = new LineNumberReader(new FileReader(tempFile.toFile()))) {
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
                final String s = r.readLine();
                int t = offset + s.length();
                int x = t - startPosition;

                System.out.println("current line : " + s);
                System.out.println("current line length: " + s.length());
                System.out.println("start position: " + startPosition);
                System.out.println("end pos: " + t);
                System.out.println("read length: " + x);
                r.close();

                RandomAccessFile randomAccessFile = new RandomAccessFile(tempFile.toFile(), "r");
                byte [] chars = new byte[x];
                System.out.println("read length 2: " + chars.length);
                randomAccessFile.seek(startPosition);
                randomAccessFile.read(chars, 0, x);
                System.out.println(new String(chars));
            } else {
                System.out.println("File is not long enough");
            }
        }






    }



    private VirtualFileSystem virtualFileSystem() throws Exception {
        return new MemoryVirtualFileSystem(mock(ArchiverFactory.class), null);
    }

    private static VirtualFile withName(String name) {
        return argThat(new ArgumentMatcher<VirtualFile>() {
            @Override
            public boolean matches(Object argument) {
                return name.equals(((VirtualFile)argument).getName());
            }
        });
    }
}
