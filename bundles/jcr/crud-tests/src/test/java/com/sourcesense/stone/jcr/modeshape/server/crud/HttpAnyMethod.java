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
package com.sourcesense.stone.jcr.modeshape.server.crud;

import org.apache.commons.httpclient.HttpMethodBase;

/** Allows any HTTP method for HtttpClient */
public class HttpAnyMethod extends HttpMethodBase {
    private final String methodName;

    public HttpAnyMethod(String methodName, String uri) {
        super(uri);
        this.methodName = methodName;
    }

    @Override
    public String getName() {
        return methodName;
    }
}
