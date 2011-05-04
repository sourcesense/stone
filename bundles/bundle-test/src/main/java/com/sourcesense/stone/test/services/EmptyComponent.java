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

/**
 * The <code>EmptyComponent</code> TODO add policy="require"
 *
 * @scr.component label="%repository.name" description="%repository.description" name=
 *                "com.sourcesense.stone.test.services.EmptyComponent" configurationFactory="true" policy="require"
 * @scr.service interface="com.sourcesense.stone.test.services.SimpleComponent"
 * @scr.property name="service.vendor" value="Prova"
 * @scr.property name="service.description" value="It is a Test Component"
 */
public class EmptyComponent implements SimpleComponent {

}
