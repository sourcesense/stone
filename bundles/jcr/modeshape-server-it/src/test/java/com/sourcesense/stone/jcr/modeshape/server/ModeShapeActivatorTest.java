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

import static com.sourcesense.stone.jcr.modeshape.server.IntegrationTestUtil.countModeShapeRepositoryConfigurations;
import static com.sourcesense.stone.jcr.modeshape.server.IntegrationTestUtil.removeModeShapeRepositoryConfigurations;
import static com.sourcesense.stone.jcr.modeshape.server.PaxConfigurations.slingBasicConfiguration;
import static com.sourcesense.stone.jcr.modeshape.server.PaxConfigurations.stoneInMemoryConfiguration;
import static com.sourcesense.stone.jcr.modeshape.server.PaxConfigurations.debug;
import static org.junit.Assert.assertEquals;
import static org.ops4j.pax.exam.CoreOptions.options;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Inject;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.osgi.framework.BundleContext;
import com.sourcesense.stone.jcr.modeshape.server.impl.Activator;

@RunWith( JUnit4TestRunner.class )
public class ModeShapeActivatorTest {

    @Inject
    BundleContext bundleContext;

    @Configuration
    public Option[] configuration() {
        return options(debug(), slingBasicConfiguration(), stoneInMemoryConfiguration());
    }

    @Test
    public void shouldNotCreateANewConfigurationBecauseOneIsAlreadyStored() throws Exception {
        int previousNumberOfModeShapeRepositoryConfigurations = countModeShapeRepositoryConfigurations(bundleContext);

        Activator activator = new Activator();
        activator.start(bundleContext);

        int currentNumberOfModeShapeRepositoryConfigurations = countModeShapeRepositoryConfigurations(bundleContext);
        assertEquals(previousNumberOfModeShapeRepositoryConfigurations, currentNumberOfModeShapeRepositoryConfigurations);
    }

    @Test
    public void shouldCreateANewConfigurationWhenNoConfigurationIsFound() throws Exception {
        removeModeShapeRepositoryConfigurations(bundleContext);
        assertEquals(0, countModeShapeRepositoryConfigurations(bundleContext));

        Activator activator = new Activator();
        activator.start(bundleContext);

        int currentNumberOfModeShapeRepositoryConfigurations = countModeShapeRepositoryConfigurations(bundleContext);
        assertEquals(1, currentNumberOfModeShapeRepositoryConfigurations);
    }

}
