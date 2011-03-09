package com.sourcesense.stone.jcr.modeshape.server.performances;

import static com.sourcesense.stone.jcr.modeshape.server.PaxConfigurations.slingBasicConfiguration;
import static com.sourcesense.stone.jcr.modeshape.server.PaxConfigurations.stoneH2Configuration;
import static com.sourcesense.stone.jcr.modeshape.server.PaxConfigurations.debug;
import static org.ops4j.pax.exam.CoreOptions.options;
import static org.ops4j.pax.exam.container.def.PaxRunnerOptions.vmOption;

import java.io.File;

import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;

@RunWith(JUnit4TestRunner.class)
public final class H2PerformanceTest extends AbstractPerformanceTest {

    @Configuration
    public static Option[] configuration() {
        new File("/tmp/sling").delete();
        return options(debug(), slingBasicConfiguration(),
                stoneH2Configuration(),vmOption("-Duser.language=en -Duser.country=US"));
    }

    public H2PerformanceTest() {
        super("H2");
    }

}
