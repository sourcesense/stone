package com.sourcesense.stone.jcr.modeshape.server;

import static org.junit.Assert.assertNotNull;
import static org.ops4j.pax.exam.CoreOptions.felix;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.CoreOptions.mavenConfiguration;
import static org.ops4j.pax.exam.CoreOptions.options;
import static org.ops4j.pax.exam.CoreOptions.wrappedBundle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.ConfigurationAdmin;

@RunWith( JUnit4TestRunner.class )
public class ModeshapeServerConfigurationTest extends AbstractTestCase {

    @Configuration
    public static Option[] configuration() {
        return options(felix(),
                       mavenConfiguration(),
                       wrappedBundle(mavenBundle("com.google.collections", "google-collections").version("1.0-rc3")));
    }

    @Test
    public void shouldHaveConfigAdminServiceReference( BundleContext bundleContext ) throws Exception {
        ServiceReference sr = getConfigurationAdminServiceReferenceFrom(bundleContext);

        assertNotNull(sr);
    }

    @Test
    public void shouldHaveConfigAdminServiceRegistered( BundleContext bundleContext ) throws Exception {

        ServiceReference sr = getConfigurationAdminServiceReferenceFrom(bundleContext);
        ConfigurationAdmin ca = (ConfigurationAdmin)bundleContext.getService(sr);

        assertNotNull(ca);
    }

    private ServiceReference getConfigurationAdminServiceReferenceFrom( BundleContext bundleContext ) {
        final String CONFIG_ADMIN_NAME = ConfigurationAdmin.class.getName();
        ServiceReference sr = bundleContext.getServiceReference(CONFIG_ADMIN_NAME);
        return sr;
    }
}