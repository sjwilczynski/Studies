package list9;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ServiceLocatorTest {

    @Test
    void simpleResolveTest() throws RegisterClassException, UnknownLifecycleException, ResolveException {
        SimpleContainer container = new SimpleContainer();
        container.registerType(Bar.class, SubBar.class, ContainerEntryType.INSTANTIABLE);
        ServiceLocator.getInstance().setProvider(() -> container);
        Bar bar = ServiceLocator.getInstance().getInstance(Bar.class);
        assertTrue(bar instanceof SubBar);
    }

    @Test
    void resolveContainer() throws ResolveException {
        SimpleContainer container = new SimpleContainer();
        ServiceLocator.getInstance().setProvider(() -> container);
        SimpleContainer newContainer = ServiceLocator.getInstance().getInstance(SimpleContainer.class);
        assertEquals(newContainer, container);
    }
}