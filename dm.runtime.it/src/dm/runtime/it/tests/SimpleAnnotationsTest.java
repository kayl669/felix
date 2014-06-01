package dm.runtime.it.tests;

import org.apache.felix.dm.itest.Ensure;
import org.apache.felix.dm.itest.TestBase;
import org.osgi.framework.ServiceRegistration;

import dm.runtime.it.components.SimpleAnnotations.Consumer;
import dm.runtime.it.components.SimpleAnnotations.Producer;

/**
 * Use case: Ensure that a Provider can be injected into a Consumer, using simple DM annotations.
 */
public class SimpleAnnotationsTest extends TestBase {
    
    public SimpleAnnotationsTest() { 
        super(false); /* don't autoclear managers when one test is done */ 
    }

    public void testSimpleAnnotations() throws Throwable {
        Ensure e = new Ensure();
        ServiceRegistration er = register(e, Producer.ENSURE);
        e.waitForStep(3, 10000); // Producer registered
        ServiceRegistration er2 = register(e, Consumer.ENSURE);

        er2.unregister(); // stop consumer
        er.unregister(); // stop provider

        // And check if components have been deactivated orderly.
        e.waitForStep(10, 10000);
        e.ensure();
    }
}
