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

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

/**
 * Test for measuring the performance of creating a node with
 * {@value #CHILD_COUNT} child nodes.
 */
class CreateManyChildNodesTest extends AbstractTest {

    private static final int CHILD_COUNT = 10 * 1000;

    private Session session;

    public void beforeSuite() throws RepositoryException {
        session = loginWriter();
    }

    public void beforeTest() throws RepositoryException {
    }

    public void runTest() throws Exception {
        Node node = session.getRootNode().addNode("testnode", "nt:unstructured");
        for (int i = 0; i < CHILD_COUNT; i++) {
            node.addNode("node" + i, "nt:unstructured");
        }
        session.save();
    }

    public void afterTest() throws RepositoryException {
        session.getRootNode().getNode("testnode").remove();
        session.save();
    }

}
