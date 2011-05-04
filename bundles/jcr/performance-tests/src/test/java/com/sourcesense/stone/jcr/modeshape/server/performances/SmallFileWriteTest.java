/*
 *  Copyright 2011 Sourcesense
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
*/
package com.sourcesense.stone.jcr.modeshape.server.performances;

import java.util.Calendar;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

class SmallFileWriteTest extends AbstractTest {

    private static final int FILE_COUNT = 100;

    private static final int FILE_SIZE = 10;

    private Session session;

    private Node root;

    public void beforeSuite() throws RepositoryException {
        session = loginWriter();
    }

    public void beforeTest() throws RepositoryException {
        root = session.getRootNode().addNode("SmallFileWriteTest", "nt:folder");
        session.save();
    }

    @SuppressWarnings("deprecation")
    public void runTest() throws Exception {
        for (int i = 0; i < FILE_COUNT; i++) {
            Node file = root.addNode("file" + i, "nt:file");
            Node content = file.addNode("jcr:content", "nt:resource");
            content.setProperty("jcr:mimeType", "application/octet-stream");
            content.setProperty("jcr:lastModified", Calendar.getInstance());
            content.setProperty(
                    "jcr:data", new TestInputStream(FILE_SIZE * 1024));
        }
    }

    public void afterTest() throws RepositoryException {
        root.remove();
        session.save();
    }

}
