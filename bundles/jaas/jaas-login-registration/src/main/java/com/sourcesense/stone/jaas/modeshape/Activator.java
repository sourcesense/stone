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
package com.sourcesense.stone.jaas.modeshape;

import javax.security.auth.login.Configuration;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;

/**
 * TODO fill me
 *
 * @version $Id: Activator.java 41258 2011-02-16 11:07:32Z s.tripodi $
 */
public class Activator implements BundleActivator, ServiceListener {

	private BundleContext bundleContext;

	private Configuration oldConfiguration;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serviceChanged(ServiceEvent event) {
		if (ServiceEvent.REGISTERED == event.getType()) {

			bundleContext.removeServiceListener(this);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void start(BundleContext bundleContext) throws Exception {
		this.bundleContext = bundleContext;
		try {
			oldConfiguration = Configuration.getConfiguration();
		} catch (SecurityException se) {
			// oldConfiguration remains null
		}
		Configuration.setConfiguration(new StoneConfiguration());

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		Configuration.setConfiguration(oldConfiguration);
	}
}
