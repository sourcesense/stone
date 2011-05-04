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
package com.sourcesense.stone.jcr.modeshape.server;

import static com.sourcesense.stone.jcr.modeshape.server.PaxConfigurations.debug;
import static com.sourcesense.stone.jcr.modeshape.server.PaxConfigurations.modeshapeWeb;
import static com.sourcesense.stone.jcr.modeshape.server.PaxConfigurations.slingFullConfiguration;
import static com.sourcesense.stone.jcr.modeshape.server.PaxConfigurations.stoneInMemoryConfiguration;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.CoreOptions.options;
import java.io.File;
import javax.jcr.Node;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.servlet.http.HttpServletRequest;
import org.apache.sling.jcr.api.SlingRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modeshape.jcr.api.SecurityContextCredentials;
import org.modeshape.web.jcr.ServletSecurityContext;
import org.ops4j.io.FileUtils;
import org.ops4j.pax.exam.Inject;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.osgi.framework.BundleContext;

@RunWith( JUnit4TestRunner.class )
public class SecurityTest {

    @Inject
    BundleContext bundleContext;

    @Configuration
    public Option[] configuration() {
        FileUtils.delete(new File("/tmp/sling"));
        return options(debug(),
                       slingFullConfiguration(),
                       stoneInMemoryConfiguration(),
                       modeshapeWeb(),
                       mavenBundle(PaxConfigurations.STONE_GROUP,
                                   "com.sourcesense.stone.bundle.test",
                                   PaxConfigurations.STONE_VERSION));
    }

    HttpServletRequest request = new HttpServletRequestStub();

    @Test
    public void shouldSaveAndReadANodeInTwoDifferentAuthenticatedSessions() throws Exception {
        SlingRepository slingRepository = IntegrationTestUtil.getSlingRepositoryFromServiceList(bundleContext);

        SecurityContextCredentials credentialsFromHTTPServletRequest = new SecurityContextCredentials(
                                                                                                      new ServletSecurityContext(
                                                                                                                                 request));
        Session session = slingRepository.login(credentialsFromHTTPServletRequest);
        assertNotNull(session);

        session.getRootNode().addNode("prova");
        session.save();
        session.logout();

        Session anotherSession = slingRepository.login(credentialsFromHTTPServletRequest);
        Node prova = anotherSession.getRootNode().getNode("prova");
        assertNotNull(prova);
    }

    @Test
    public void loginFromSling() throws Exception {
        SlingRepository slingRepository = IntegrationTestUtil.getSlingRepositoryFromServiceList(bundleContext);

        try {
            javax.security.auth.login.Configuration configuration = javax.security.auth.login.Configuration.getConfiguration();
            assertNotNull(configuration);
        } catch (SecurityException se) {
            fail();
        }

        Session session = slingRepository.login(new SimpleCredentials("admin", "admin".toCharArray()));
        assertNotNull(session);
    }
}
