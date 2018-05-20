package list9;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SimpleContainerTest {

    private SimpleContainer container;

    @Test
    void testSingletonClass() throws ResolveException, NotRegisteredClassException, UnknownLifecycleException {
        container = new SimpleContainer();
        container.registerType(Woo.class, ClassInstanceType.SINGLETON);
        Woo w1 = container.resolve(Woo.class);
        Woo w2 = container.resolve(Woo.class);
        assertTrue(w1.equals(w2));
    }

    @Test
    void testInstantiableClass() throws ResolveException, NotRegisteredClassException, UnknownLifecycleException {
        container = new SimpleContainer();
        container.registerType(Woo.class, ClassInstanceType.INSTANTIABLE);
        Woo w1 = container.resolve(Woo.class);
        Woo w2 = container.resolve(Woo.class);
        assertTrue(!w1.equals(w2));
    }

    @Test
    void testUnregisteredType() {
        container = new SimpleContainer();
        assertThrows(NotRegisteredClassException.class, () -> container.resolve(Woo.class));
    }

    @Test
    void testFromInterface() throws ResolveException, NotRegisteredClassException, UnknownLifecycleException {
        container = new SimpleContainer();
        container.registerType(Fooable.class, Foo.class, ClassInstanceType.INSTANTIABLE);
        assertTrue(container.resolve(Fooable.class) instanceof Foo);

        container.registerType(Fooable.class, Bar.class, ClassInstanceType.INSTANTIABLE);
        assertTrue(container.resolve(Fooable.class) instanceof Bar);
    }

    @Test
    void testSuperClass() throws ResolveException, NotRegisteredClassException, UnknownLifecycleException {
        container = new SimpleContainer();
        container.registerType(Bar.class, SubBar.class, ClassInstanceType.INSTANTIABLE);
        assertTrue(container.resolve(Bar.class) instanceof SubBar);
    }

    @Test
    void testNoDefaultConstructor() throws UnknownLifecycleException {
        container = new SimpleContainer();
        container.registerType(Moo.class, ClassInstanceType.INSTANTIABLE);
        assertThrows(ResolveException.class, () -> container.resolve(Moo.class));
    }

    @Test
    void testUnknownLifecycle() {
        container = new SimpleContainer();
        assertThrows(UnknownLifecycleException.class, () -> container.registerType(Foo.class, ClassInstanceType.THREAD));
    }
}