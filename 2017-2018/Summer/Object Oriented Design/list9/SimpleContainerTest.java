package list9;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class SimpleContainerTest {

    private SimpleContainer container = new SimpleContainer();

    @BeforeEach
    void clear() {
        container.clear();
    }

    // list 9 tests
    @Test
    void registerIncompatibleClasses() {
        assertThrows(RegisterClassException.class, () -> container.registerType(Foo.class, A.class,
                ContainerEntryType.INSTANTIABLE));
    }

    @Test
    void testSingletonClass() throws ResolveException, UnknownLifecycleException, RegisterClassException {
        container.registerType(Woo.class, ContainerEntryType.SINGLETON);
        Woo w1 = container.resolve(Woo.class);
        Woo w2 = container.resolve(Woo.class);
        assertTrue(w1.equals(w2));
    }

    @Test
    void testInstantiableClass() throws ResolveException, UnknownLifecycleException, RegisterClassException {
        container.registerType(Woo.class, ContainerEntryType.INSTANTIABLE);
        Woo w1 = container.resolve(Woo.class);
        Woo w2 = container.resolve(Woo.class);
        assertTrue(!w1.equals(w2));
    }

    @Test
    void testUnregisteredType() {
        assertThrows(NotRegisteredClassException.class, () -> container.resolve(Woo.class));
    }

    @Test
    void testFromInterface() throws ResolveException, UnknownLifecycleException, RegisterClassException {
        container.registerType(Fooable.class, Foo.class, ContainerEntryType.INSTANTIABLE);
        assertTrue(container.resolve(Fooable.class) instanceof Foo);
        container.registerType(Fooable.class, Bar.class, ContainerEntryType.INSTANTIABLE);
        assertTrue(container.resolve(Fooable.class) instanceof Bar);
    }

    @Test
    void testSuperClass() throws ResolveException, UnknownLifecycleException, RegisterClassException {
        container.registerType(Bar.class, SubBar.class, ContainerEntryType.INSTANTIABLE);
        assertTrue(container.resolve(Bar.class) instanceof SubBar);
    }

    @Test
    void testUnknownLifecycle() {
        assertThrows(UnknownLifecycleException.class, () -> container.registerType(Foo.class,
                ContainerEntryType.THREAD));
    }

    // list 10 tests
    @Test
    void testRegisterInstance() throws ResolveException, RegisterClassException {
        Foo foo = new Foo();
        container.registerInstance(Foo.class, foo);
        Foo fooInstance = container.resolve(Foo.class);
        assertEquals(foo, fooInstance);
    }

    @Test
    void oneLongestConstructor() throws ResolveException {
        Constructor constructor = container.getConstructor(B.class);
        assertEquals(constructor.getParameterCount(), 3);
    }

    @Test
    void twoLongestConstructors() {
        assertThrows(EqualLengthConstructorsException.class, () ->
                container.getConstructor(Moo.class));
    }

    private void checkDependencyCycle(Class type) throws ResolveException,
            UnknownLifecycleException, RegisterClassException {
        container.registerInstance(Foo.class, new Foo());
        container.registerType(C.class, ContainerEntryType.SINGLETON);
        container.registerType(Woo.class, ContainerEntryType.INSTANTIABLE);
        container.registerType(D.class, ContainerEntryType.SINGLETON);
        container.registerType(E.class, ContainerEntryType.INSTANTIABLE);
        container.registerType(A.class, ContainerEntryType.INSTANTIABLE);
        container.registerType(B.class, ContainerEntryType.INSTANTIABLE);
        container.registerType(Bar.class, ContainerEntryType.INSTANTIABLE);
        container.registerType(SubBar.class, ContainerEntryType.INSTANTIABLE);
        container.registerType(F.class, ContainerEntryType.INSTANTIABLE);
        container.checkDependencies(type, new ArrayList<>(Collections.singletonList(type)));
    }

    @Test
    void noDependencyCycle() throws ResolveException, UnknownLifecycleException, RegisterClassException {
        checkDependencyCycle(Foo.class);
        checkDependencyCycle(Woo.class);
        checkDependencyCycle(E.class);
    }

    @Test
    void simpleDependencyCycle() {
        assertThrows(DependencyCycleException.class, () -> checkDependencyCycle(C.class));
    }

    @Test
    void dependencyCycle() {
        assertThrows(DependencyCycleException.class, () -> checkDependencyCycle(A.class));
    }

    @Test
    void unregisteredDependency() throws RegisterClassException, UnknownLifecycleException {
        container.registerType(H.class, ContainerEntryType.INSTANTIABLE);
        assertThrows(NotRegisteredClassException.class, () -> container.resolve(H.class));
    }

    @Test
    void nestedObjects() throws UnknownLifecycleException, ResolveException, RegisterClassException {
        container.registerInstance(Foo.class, new Foo());
        container.registerType(Woo.class, ContainerEntryType.INSTANTIABLE);
        container.registerType(D.class, ContainerEntryType.SINGLETON);
        container.registerType(E.class, ContainerEntryType.INSTANTIABLE);
        D d = container.resolve(D.class);
        E e = container.resolve(E.class);
        assertNotNull(d.woo);
        assertNotNull(e.d);
        assertEquals(e.foo, d.foo);
        assertEquals(d, e.d);
    }

    @Test
    void instanceInsteadOfConstructors() throws UnknownLifecycleException, ResolveException,
            RegisterClassException {
        container.registerType(Moo.class, ContainerEntryType.INSTANTIABLE);
        container.registerType(G.class, ContainerEntryType.INSTANTIABLE);
        assertThrows(EqualLengthConstructorsException.class, () -> container.resolve(G.class));
        container.registerInstance(Moo.class, new Moo("Instance"));
        G g = container.resolve(G.class);
        assertNotNull(g.moo);
    }

    @Test
    void cyclePreventedByInstances() throws RegisterClassException, UnknownLifecycleException,
            ResolveException {
        container.registerInstance(A.class, new A());
        container.registerType(Woo.class, ContainerEntryType.SINGLETON);
        container.registerType(B.class, ContainerEntryType.INSTANTIABLE);
        container.registerType(Bar.class, ContainerEntryType.INSTANTIABLE);
        container.registerType(SubBar.class, ContainerEntryType.INSTANTIABLE);
        container.registerType(F.class, ContainerEntryType.INSTANTIABLE);
        container.registerType(F.class, ContainerEntryType.INSTANTIABLE);
        B b = container.resolve(B.class);
        assertNotNull(b);
    }

    @Test
    void interfaceAsConstructorParam() throws UnknownLifecycleException, ResolveException,
            RegisterClassException {
        container.registerType(Fooable.class, Foo.class, ContainerEntryType.INSTANTIABLE);
        container.registerType(H.class, ContainerEntryType.INSTANTIABLE);
        H h = container.resolve(H.class);
        assertNotNull(h.fooable);
    }

    @Test
    void annotatedConstructor() throws RegisterClassException, UnknownLifecycleException, ResolveException {
        container.registerType(Foo.class, ContainerEntryType.INSTANTIABLE);
        container.registerType(K.class, ContainerEntryType.INSTANTIABLE);
        K k = container.resolve(K.class);
        assertNotNull(k.foo);
    }

    @Test
    void twoAnnotatedConstructors() throws RegisterClassException, UnknownLifecycleException {
        container.registerType(L.class, ContainerEntryType.INSTANTIABLE);
        assertThrows(TooManyInjectableConstuctors.class, () -> container.resolve(L.class));
    }
}