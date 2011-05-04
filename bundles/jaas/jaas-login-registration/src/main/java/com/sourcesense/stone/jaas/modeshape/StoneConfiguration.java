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
package com.sourcesense.stone.jaas.modeshape;

import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.Configuration;

/**
 * @version $Rev: 56022 $ $Date: 2004-10-30 00:16:18 -0500 (Sat, 30 Oct 2004) $
 */
public class StoneConfiguration extends Configuration {

    @Override
    public void refresh() {
    }

    @Override
    public AppConfigurationEntry[] getAppConfigurationEntry(String name) {
        return new AppConfigurationEntry[] {
                new StoneConfigurationEntry(AppConfigurationEntry.LoginModuleControlFlag.REQUIRED, false),
                new StoneConfigurationEntry(AppConfigurationEntry.LoginModuleControlFlag.SUFFICIENT, true)
        };
    }

}
