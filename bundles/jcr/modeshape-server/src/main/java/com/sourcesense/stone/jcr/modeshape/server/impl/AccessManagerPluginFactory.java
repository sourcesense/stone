package com.sourcesense.stone.jcr.modeshape.server.impl;

import com.sourcesense.stone.jcr.modeshape.server.security.accessmanager.AccessManagerPlugin;

public interface AccessManagerPluginFactory {

    AccessManagerPlugin getAccessManager();

}