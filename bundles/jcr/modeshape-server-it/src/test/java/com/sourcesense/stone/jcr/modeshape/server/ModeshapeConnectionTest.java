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
import static com.sourcesense.stone.jcr.modeshape.server.PaxConfigurations.slingBasicConfiguration;
import static com.sourcesense.stone.jcr.modeshape.server.PaxConfigurations.stoneInMemoryConfiguration;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.CoreOptions.options;
import java.io.File;
import javax.jcr.Node;
import javax.jcr.Session;
import org.apache.sling.jcr.api.SlingRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modeshape.jcr.api.SecurityContextCredentials;
import org.ops4j.io.FileUtils;
import org.ops4j.pax.exam.Inject;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import com.sourcesense.stone.jcr.modeshape.server.impl.SlingServerRepository;
import com.sourcesense.stone.jcr.modeshape.server.security.CustomSecurityContext;
import com.sourcesense.stone.test.services.CallingComponentService;
import com.sourcesense.stone.test.services.SimpleService;

@RunWith( JUnit4TestRunner.class )
public class ModeshapeConnectionTest {

    @Inject
    BundleContext bundleContext;

    @Configuration
    public Option[] configuration() {
        FileUtils.delete(new File("/tmp/sling"));
        return options(debug(),
                       slingBasicConfiguration(),
                       stoneInMemoryConfiguration(),
                       mavenBundle(PaxConfigurations.STONE_GROUP,
                                   "com.sourcesense.stone.bundle.test",
                                   PaxConfigurations.STONE_VERSION));
    }

    @Test
    public void shouldTakeTheDefaultWorkspace() throws Exception {
        ServiceReference[] serviceReference = bundleContext.getAllServiceReferences(SimpleService.class.getName(),
                                                                                    "(component.name=com.sourcesense.stone.test.services.CallingComponentService)");
        assertNotNull(serviceReference);

        CallingComponentService ssr = (CallingComponentService)bundleContext.getService(serviceReference[0]);
        SlingRepository slingRepository = ssr.getComponent();
        assertNotNull(slingRepository);
        assertNotNull(slingRepository.getDefaultWorkspace());
    }

    @Test
    public void shouldGetValidSlingRepository() throws Exception {

        SlingRepository slingRepository = IntegrationTestUtil.getSlingRepositoryFromServiceList(bundleContext);
        assertNotNull(slingRepository);
        assertTrue(slingRepository instanceof SlingServerRepository);
    }

    @Test
    public void shouldSuccessfullyLoginToModeShapeRepository() throws Exception {
        SlingRepository slingRepository = IntegrationTestUtil.getSlingRepositoryFromServiceList(bundleContext);

        Session session = slingRepository.login(new SecurityContextCredentials(new CustomSecurityContext()));
        assertNotNull(session);
        assertNotNull(slingRepository);
        assertTrue(slingRepository instanceof SlingServerRepository);
    }

    @Test
    public void shouldWriteEtcMapNodesIfNotFound() throws Exception {
        SlingRepository slingRepository = IntegrationTestUtil.getSlingRepositoryFromServiceList(bundleContext);

        Session session = slingRepository.login(new SecurityContextCredentials(new CustomSecurityContext()));
        assertFalse(session.nodeExists("/etc/map"));

        Node root = session.getNode("/");
        Node etc = root.addNode("etc");
        etc.addNode("map");

        session.save();

        assertTrue(session.nodeExists("/etc/map"));
        session.logout();
    }

}
