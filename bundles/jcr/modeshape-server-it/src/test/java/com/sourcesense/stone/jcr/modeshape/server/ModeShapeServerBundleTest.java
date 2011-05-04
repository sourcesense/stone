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

import static com.sourcesense.stone.jcr.modeshape.server.IntegrationTestUtil.getBundle;
import static com.sourcesense.stone.jcr.modeshape.server.IntegrationTestUtil.symbolicNamesListFor;
import static com.sourcesense.stone.jcr.modeshape.server.PaxConfigurations.slingBasicConfiguration;
import static com.sourcesense.stone.jcr.modeshape.server.PaxConfigurations.stoneInMemoryConfiguration;
import static com.sourcesense.stone.jcr.modeshape.server.PaxConfigurations.debug;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.ops4j.pax.exam.CoreOptions.options;
import java.util.Dictionary;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Inject;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

@RunWith( JUnit4TestRunner.class )
public class ModeShapeServerBundleTest {

    @Inject
    BundleContext bundleContext;

    @Configuration
    public Option[] configuration() {
        return options(debug(), slingBasicConfiguration(), stoneInMemoryConfiguration());
    }

    @Test
    public void shouldHaveNotNullBundleContext() throws Exception {
        assertNotNull(bundleContext);
    }

    @Test
    public void shouldHaveModeshapeServerBundleLoaded() {
        Bundle[] bundles = bundleContext.getBundles();
        assertTrue(bundles.length != 0);

        assertTrue(symbolicNamesListFor(bundles).contains("com.sourcesense.stone.jcr.modeshape.server"));
    }

    @Test
    public void shouldHaveModeshapeServerActive() throws Exception {
        Bundle modeshapeServerBundle = getBundle("com.sourcesense.stone.jcr.modeshape.server", bundleContext.getBundles());

        assertEquals(Bundle.ACTIVE, modeshapeServerBundle.getState());
    }

    @Test
    public void shouldHaveActivatorRegistered() throws Exception {

        Bundle modeshapeServerBundle = getBundle("com.sourcesense.stone.jcr.modeshape.server", bundleContext.getBundles());

        @SuppressWarnings( "unchecked" )
        Dictionary<String, String> manifestContent = modeshapeServerBundle.getHeaders();

        String activatorClassName = manifestContent.get("Bundle-Activator");

        assertEquals("com.sourcesense.stone.jcr.modeshape.server.impl.Activator", activatorClassName);
    }
}
