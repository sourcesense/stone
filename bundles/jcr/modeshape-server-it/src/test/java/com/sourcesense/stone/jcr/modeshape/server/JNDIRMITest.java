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
package com.sourcesense.stone.jcr.modeshape.server;
import static com.sourcesense.stone.jcr.modeshape.server.PaxConfigurations.debug;
import static com.sourcesense.stone.jcr.modeshape.server.PaxConfigurations.slingBasicConfiguration;
import static com.sourcesense.stone.jcr.modeshape.server.PaxConfigurations.stoneInMemoryConfiguration;
import static org.junit.Assert.assertNotNull;
import static org.ops4j.pax.exam.CoreOptions.options;
import java.io.File;
import javax.jcr.Repository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.io.FileUtils;
import org.ops4j.pax.exam.Inject;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.osgi.framework.BundleContext;
import com.sourcesense.stone.jcr.base.util.RepositoryAccessor;

@RunWith(JUnit4TestRunner.class)
public class JNDIRMITest {

    @Inject
    BundleContext bundleContext;

    @Configuration
    public Option[] configuration() {
        FileUtils.delete(new File("/tmp/sling"));
        return options(debug(), slingBasicConfiguration(), stoneInMemoryConfiguration());
    }

	@Test
	public void connectionRetrieved() throws Exception {

		RepositoryAccessor  repositoryAccessor = new RepositoryAccessor();
		Repository repository = repositoryAccessor.getRepositoryFromURL("jndi://modeshape:java.naming.factory.initial=org.modeshape.common.naming.SingletonInitialContextFactory,java.naming.provider.url=localhost");
		assertNotNull(repository);
	}
}
