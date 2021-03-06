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
package com.sourcesense.stone.jcr.modeshape.server.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.Formatter;

import javax.jcr.Credentials;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;

import org.apache.sling.jcr.api.SlingRepository;
import org.modeshape.common.collection.Problem;
import org.modeshape.common.component.ClassLoaderFactory;
import org.modeshape.graph.ExecutionContext;
import org.modeshape.jcr.JcrConfiguration;
import org.modeshape.jcr.JcrEngine;
import org.modeshape.jcr.api.SecurityContext;
import org.modeshape.jcr.api.SecurityContextCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sourcesense.stone.jcr.base.AbstractSlingRepository;
import com.sourcesense.stone.jcr.modeshape.server.security.CustomSecurityContext;

/**
 * The <code>SlingServerRepository</code> TODO add policy="require"
 *
 * @scr.component label="%repository.name" description="%repository.description" name=
 *                "com.sourcesense.stone.jcr.modeshape.server.SlingServerRepository" configurationFactory="true" policy="require"
 * @scr.property name="service.vendor" value="JBoss"
 * @scr.property name="service.description" value="Factory for embedded Modeshape Repository Instances"
 */
public class SlingServerRepository extends AbstractSlingRepository implements Repository, SlingRepository {

    /**
     * The name of the configuration property defining the URL to the repository configuration file (value is "config").
     * <p>
     * If the configuration file is located in the local file system, the "file:" scheme must still be specified.
     * <p>
     * This parameter is mandatory for this activator to start the repository.
     *
     * @scr.property value=""
     */
    public static final String REPOSITORY_CONFIG_URL = "config";

    /**
     * @scr.property value=""
     */
    public static final String REPOSITORY_REGISTRATION_NAME = "name";

    private static final Repository NO_REPOSITORY = null;

    private JcrEngine engine;

    private final Logger log = LoggerFactory.getLogger(SlingServerRepository.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public Repository acquireRepository() {
        Repository repository = super.acquireRepository();
        try {
            return repository == NO_REPOSITORY ? findSuitableRepository() : repository;
        } catch (Exception e) {
            log.error("Could not initialize repository", e);
        }
        return NO_REPOSITORY;
    }

    /**
     * TODO fill me
     *
     * @return
     * @throws Exception
     */
    private Repository findSuitableRepository() throws Exception {

        String configFilePath = (String) getComponentContext().getProperties().get("config");

        if (log.isInfoEnabled()) {
            log.info("Reading configuration from {}", configFilePath);
        }

        URL configURL = new URL(configFilePath);

        ExecutionContext executionContext = new ExecutionContext() {
            @Override
            public ClassLoaderFactory getClassLoaderFactory() {
                return new BundleClassLoaderFactory(getComponentContext());
            }
        };

        JcrConfiguration configuration = new JcrConfiguration(executionContext);

        try {
            configuration.loadFrom(configURL);
            if (!configuration.getProblems().isEmpty()) {
                logProblems(configuration);
            } else {
                return startModeShapeRepository(configuration);
            }
        } catch (Exception e) {
            StringWriter writer = new StringWriter();
            e.printStackTrace(new PrintWriter(writer));
            if (log.isWarnEnabled()) {
                log.warn("Impossible to open configuration from URL '{}': {}\nNo javax.jcr.Repository implementation found",
                        configURL,
                        writer.getBuffer());
            }
        }

        return NO_REPOSITORY;
    }

    /**
     * TODO fill me
     *
     * @param configuration
     * @return
     * @throws RepositoryException
     */
    private Repository startModeShapeRepository( JcrConfiguration configuration ) throws RepositoryException {
        engine = configuration.build();
        engine.start();
        return engine.getRepository("test");
    }

    /**
     * Log the error messages in the given configuration.
     *
     * @param configuration
     */
    private void logProblems( JcrConfiguration configuration ) {
        if (!configuration.getProblems().isEmpty()) {
            Formatter fmt = new Formatter()
                .format("%s error occurred while looking for a suitable repository using configuration: %s",
                    (configuration.getProblems().size() > 1 ? "Some" : "One"),
                    configuration.getConfigurationDefinition().getPath());

            int index = 1;
            for (Problem problem : configuration.getProblems()) {
                fmt.format("    %s) %s%n", index++, problem.getMessageString());
            }

            log.error(fmt.toString());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void disposeRepository( Repository repository ) {
        super.disposeRepository(repository);
        engine.shutdown();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Credentials getAdministrativeCredentials( String adminUser ) {
        SecurityContext securityContext = new CustomSecurityContext();
        return new SecurityContextCredentials(securityContext);
    }

}
