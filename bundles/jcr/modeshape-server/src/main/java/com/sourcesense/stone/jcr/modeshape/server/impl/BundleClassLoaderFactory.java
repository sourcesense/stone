package com.sourcesense.stone.jcr.modeshape.server.impl;

import org.modeshape.common.component.ClassLoaderFactory;
import org.osgi.framework.Bundle;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BundleClassLoaderFactory implements ClassLoaderFactory {

    private final ComponentContext componentContext;
    private static final Logger log = LoggerFactory.getLogger(BundleClassLoaderFactory.class);

    public BundleClassLoaderFactory( ComponentContext componentContext ) {
        this.componentContext = componentContext;
    }

    @Override
    public ClassLoader getClassLoader( String... classpath ) {
        return new ClassLoader() {
            @Override
            protected Class<?> findClass( String name ) throws ClassNotFoundException {

                Bundle[] bundles = componentContext.getBundleContext().getBundles();
                for (Bundle bundle : bundles) {
                    try {
                        @SuppressWarnings( "rawtypes" )
                        Class loadedClass = bundle.loadClass(name);
                        if (log.isInfoEnabled()) {
                            log.info("Class {} found in bundle {}", name, bundle.getSymbolicName());
                        }
                        return loadedClass;
                    } catch (ClassNotFoundException e) {
                        if (log.isInfoEnabled()) {
                            log.info("Bundle {} does not contain class {}", bundle.getSymbolicName(), name);
                        }
                    }
                }
                throw new ClassNotFoundException(String.format("Class with name %s not found in loaded bundles", name));
            }

        };
    }
}