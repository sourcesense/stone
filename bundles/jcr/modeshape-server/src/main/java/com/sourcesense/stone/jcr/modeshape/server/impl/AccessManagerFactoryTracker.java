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

import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

/**
 * TODO fill me
 *
 * @version $Id$
 */
public class AccessManagerFactoryTracker extends ServiceTracker {

    private BundleContext bundleContext;

    /**
     *
     *
     * @param bundleContext
     */
    public AccessManagerFactoryTracker(BundleContext bundleContext) {
        super(bundleContext, AccessManagerPluginFactory.class.getName(), null);
        this.bundleContext = bundleContext;
    }

    /**
     *
     *
     * @return
     */
    public BundleContext getAssociatedBundleContext() {
        return this.bundleContext;
    }

}
