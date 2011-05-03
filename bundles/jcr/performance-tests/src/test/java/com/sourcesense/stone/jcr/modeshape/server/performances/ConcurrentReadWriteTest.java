/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sourcesense.stone.jcr.modeshape.server.performances;

import java.util.Random;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

/**
 * A {@link ConcurrentReadTest} with a single writer thread that continuously
 * updates the nodes being accessed by the readers.
 */
class ConcurrentReadWriteTest extends ConcurrentReadTest {

    public void beforeSuite() throws Exception {
        super.beforeSuite();

        addBackgroundJob(new Writer());
    }

    private class Writer implements Runnable {

        private final Session session = loginWriter();

        private final Random random = new Random();

        private long count = 0;

        public void run() {
            try {
                int i = random.nextInt(NODE_COUNT);
                int j = random.nextInt(NODE_COUNT);
                Node node = session.getRootNode().getNode(
                        "testroot/node" + i + "/node" + j);
                node.setProperty("count", count++);
                session.save();
            } catch (RepositoryException e) {
                throw new RuntimeException(e);
            }
        }

    }

}
