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

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

/**
 * Test for measuring the performance of {@value #ITERATIONS} iterations of
 * transiently adding and removing a child node to a node that already has
 * {@value #CHILD_COUNT} existing child nodes.
 */
class TransientManyChildNodesTest extends AbstractTest {

    private static final int CHILD_COUNT = 10 * 1000;

    private static final int ITERATIONS = 1000;

    private Session session;

    private Node node;

    public void beforeSuite() throws RepositoryException {
        session = getRepository().login(getCredentials());
        node = session.getRootNode().addNode("testnode", "nt:unstructured");
        for (int i = 0; i < CHILD_COUNT; i++) {
            node.addNode("node" + i, "nt:unstructured");
        }
    }

    public void beforeTest() throws RepositoryException {
    }

    public void runTest() throws Exception {
        for (int i = 0; i < ITERATIONS; i++) {
            node.addNode("onemore", "nt:unstructured").remove();
        }
    }

    public void afterTest() throws RepositoryException {
    }

    public void afterSuite() throws RepositoryException {
        session.getRootNode().getNode("testnode").remove();
        session.save();
        session.logout();
    }

}
