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
package com.sourcesense.stone.jcr.modeshape.server.impl;

import java.io.File;
import java.util.Hashtable;

import com.sourcesense.stone.jcr.base.AbstractSlingRepository;
import com.sourcesense.stone.jcr.base.util.RepositoryAccessor;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO fill me
 *
 * @version $Id$
 */
public class ActivatorHelper {

    public static final String SLING_CONTEXT = "sling.context";
    public static final String SLING_CONTEXT_DEFAULT = "sling.context.default";
    public static final String SERVER_REPOSITORY_FACTORY_PID = "com.sourcesense.stone.jcr.modeshape.server.SlingServerRepository";

    private final Logger log = LoggerFactory.getLogger(ActivatorHelper.class);

    private ConfigurationUtils configurationUtils;
    private BundleContext bundleContext;

    public ActivatorHelper( BundleContext bundleContext ) {
        this.bundleContext = bundleContext;
    }

    /**
     *
     *
     * @param configurationAdminServiceReference
     */
    void verifyConfiguration( ServiceReference configurationAdminServiceReference ) {
        ConfigurationAdmin ca = (ConfigurationAdmin)bundleContext.getService(configurationAdminServiceReference);
        if (ca == null) {
            log.error("verifyConfiguration: Failed to get Configuration Admin Service from Service Reference");
            return;
        }

        try {
            createNewConfigurationIfDoesNotExist(ca);
        } catch (Throwable t) {
            log.error("verifyConfiguration: Cannot check or define configuration", t);
        } finally {
            bundleContext.ungetService(configurationAdminServiceReference);
        }
    }

    /**
     *
     *
     * @param configurationAdmin
     * @throws Exception
     */
    private void createNewConfigurationIfDoesNotExist( ConfigurationAdmin configurationAdmin ) throws Exception {
        Configuration[] cfgs = configurationAdmin.listConfigurations(
                String.format("(%s=%s)", ConfigurationAdmin.SERVICE_FACTORYPID, SERVER_REPOSITORY_FACTORY_PID));
        if (cfgs != null && cfgs.length > 0) {
            if (log.isInfoEnabled()) {
                log.info("verifyConfiguration: {} Configurations available for {}, nothing to do", new Object[] {
                        Integer.valueOf(cfgs.length),
                        SERVER_REPOSITORY_FACTORY_PID
                });
            }
        } else {
            createNewConfiguration(configurationAdmin);
        }
    }

    /**
     *
     *
     * @param configurationAdmin
     * @throws Exception
     */
    private void createNewConfiguration( ConfigurationAdmin configurationAdmin ) throws Exception {
        Hashtable<String, String> defaultConfig = new Hashtable<String, String>();

        final String overrideUrl = bundleContext.getProperty(RepositoryAccessor.REPOSITORY_URL_OVERRIDE_PROPERTY);
        if (overrideUrl != null && overrideUrl.length() > 0) {
            defaultConfig.put(RepositoryAccessor.REPOSITORY_URL_OVERRIDE_PROPERTY, overrideUrl);

            if (log.isInfoEnabled()) {
                log.info("{}={}, using it to create the default configuration",
                        RepositoryAccessor.REPOSITORY_URL_OVERRIDE_PROPERTY,
                        overrideUrl);
            }

        } else {
            defaultConfig = initDefaultConfig();
        }

        Configuration config = configurationAdmin.createFactoryConfiguration(SERVER_REPOSITORY_FACTORY_PID);
        config.update(defaultConfig);

        if (log.isInfoEnabled()) {
            log.info("verifyConfiguration: Created configuration {} for {}", config.getPid(), config.getFactoryPid());
        }
    }

    /**
     *
     *
     * @return
     * @throws Exception
     */
    private Hashtable<String, String> initDefaultConfig() throws Exception {
        Hashtable<String, String> defaultConfig = new Hashtable<String, String>();

        File homeDir = getConfigurationUtils().getHomeDir();
        if (homeDir == null) {
            return defaultConfig;
        }

        String configFileUrl = getConfigurationUtils().getConfigFileUrl(homeDir);
        String slingContext = bundleContext.getProperty(SLING_CONTEXT_DEFAULT);
        if (slingContext == null) {
            slingContext = "default";
        }
        defaultConfig.put(SLING_CONTEXT, slingContext);
        defaultConfig.put(SlingServerRepository.REPOSITORY_CONFIG_URL, configFileUrl);
        defaultConfig.put(SlingServerRepository.REPOSITORY_REGISTRATION_NAME, getConfigurationUtils().getRepositoryName());

        defaultConfig.put(AbstractSlingRepository.PROPERTY_ADMIN_PASS, "not-used");
        defaultConfig.put(AbstractSlingRepository.PROPERTY_ANONYMOUS_PASS, "not-used");

        return defaultConfig;
    }

    /**
     *
     *
     * @return
     */
    protected ConfigurationUtils getConfigurationUtils() {
        return this.configurationUtils == null ? new ConfigurationUtils(bundleContext) : this.configurationUtils;
    }

    /**
     *
     *
     * @return
     */
    AccessManagerFactoryTracker createAccessManagerFactoryTracker() {
        return new AccessManagerFactoryTracker(bundleContext);
    }

}
