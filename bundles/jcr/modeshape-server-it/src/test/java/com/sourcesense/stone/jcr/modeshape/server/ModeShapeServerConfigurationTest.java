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
import static org.junit.Assert.assertNotNull;
import static org.ops4j.pax.exam.CoreOptions.options;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Inject;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.ConfigurationAdmin;

@RunWith( JUnit4TestRunner.class )
public class ModeShapeServerConfigurationTest {

    @Inject
    BundleContext bundleContext;

    @Configuration
    public Option[] configuration() {
        return options(debug(), slingBasicConfiguration(), stoneInMemoryConfiguration());
    }

    @Test
    public void shouldHaveConfigAdminServiceReference() throws Exception {
        ServiceReference sr = getConfigurationAdminServiceReferenceFrom(bundleContext);

        assertNotNull(sr);
    }

    @Test
    public void shouldHaveConfigAdminServiceRegistered() throws Exception {

        ServiceReference sr = getConfigurationAdminServiceReferenceFrom(bundleContext);
        ConfigurationAdmin ca = (ConfigurationAdmin)bundleContext.getService(sr);

        assertNotNull(ca);
    }

    private ServiceReference getConfigurationAdminServiceReferenceFrom( BundleContext bundleContext ) {
        final String CONFIG_ADMIN_NAME = ConfigurationAdmin.class.getName();
        ServiceReference sr = bundleContext.getServiceReference(CONFIG_ADMIN_NAME);
        return sr;
    }
}
