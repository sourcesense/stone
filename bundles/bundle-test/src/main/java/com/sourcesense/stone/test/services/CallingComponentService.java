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
package com.sourcesense.stone.test.services;

import org.apache.sling.jcr.api.SlingRepository;

/**
 * @scr.component immediate="true" label="%resource.resolver.name"
 *                description="%resource.resolver.description" specVersion="1.1"
 * @scr.service interface="com.sourcesense.stone.test.services.SimpleService"
 *
 * @scr.property name="service.vendor" value="Sourcesense"
 */
public class CallingComponentService implements SimpleService {

    /**
     * The JCR Repository we access to resolve resources
     *
     * @scr.reference
     */
	private SlingRepository slingRepository;

	public CallingComponentService() {

	}

	public SlingRepository getComponent() {
		return slingRepository;
	}

}
