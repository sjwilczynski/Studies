package list9;

import com.google.common.annotations.VisibleForTesting;

import javax.inject.Inject;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;


public class SimpleContainer {


    private Map<Class, ContainerEntry> registeredTypes;

    SimpleContainer() {
        this.registeredTypes = new HashMap<>();
    }


    public <T> void registerType(Class<T> type, ContainerEntryType lifecycle)
            throws UnknownLifecycleException, RegisterClassException {
        registerType(type, type, lifecycle);
    }

    public <T, S> void registerType(Class<T> general, Class<S> concrete, ContainerEntryType lifecycle)
            throws UnknownLifecycleException, RegisterClassException {
        if (!general.isAssignableFrom(concrete)) {
            throw new RegisterClassException("Incompatible classes: " + general.getName() +
                    " " + concrete.getName());
        }
        registeredTypes.put(general, ContainerEntryFactory.getInstance().
                createContainerClassEntry(lifecycle, concrete, this));
    }

    public <T, S> void registerInstance(Class<T> general, S instance) throws RegisterClassException {
        if (!general.isAssignableFrom(instance.getClass())) {
            throw new RegisterClassException("Incompatible classes: " + general.getName() +
                    " " + instance.getClass().getName());
        }
        registeredTypes.put(general, ContainerEntryFactory.getInstance().
                createContainerInstanceEntry(instance));
    }

    public <T> T resolve(Class<T> type) throws ResolveException {
        if (registeredTypes.containsKey(type)) {
            ContainerEntry<T> containerEntry = registeredTypes.get(type);
            // we have to check dependencies of concrete class, not interface
            Class<T> concreteType = containerEntry.type;
            checkDependencies(concreteType, new ArrayList<>(Collections.singletonList(concreteType)));
            T instance = containerEntry.resolve();
            resolveInjectedMethods(instance);
            return instance;
        } else {
            throw new NotRegisteredClassException("Couldn't find class: " + type.getName());
        }
    }

    private <T> void resolveInjectedMethods(T instance) throws ResolveException {
        List<Executable> methods = Arrays.asList(instance.getClass().getMethods());
        List<Executable> annotatedMethods = getAnnotatedExecutables(methods);
        for(Executable executable : annotatedMethods){
            Method method = (Method) executable;
            if(!method.getReturnType().equals(Void.TYPE)){
                continue;
            }
            Class[] parameters = method.getParameterTypes();
            for(Class parameter : parameters){
                //TO DO - concrete types - tests for that
                checkDependencies(parameter, new ArrayList<>(Collections.singletonList(parameter)));
            }
            try {
                //getDependencies is wise enough to get concreteTypes
                method.invoke(instance, getDependencies(parameters));
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new ResolveException(e);
            }
        }
    }

    <T> T createNewInstance(Class<T> type) throws ResolveException {
        Constructor<T> constructor = getConstructor(type);
        try {
            return constructor.newInstance(getDependencies(constructor.getParameterTypes()));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new ResolveException(e);
        }
    }

    // it's ok because before this always checkDependencies is run
    private Object[] getDependencies(Class<?>[] parameterTypes) throws ResolveException {
        ArrayList<Object> parameters = new ArrayList<>();
        for (Class paramType : parameterTypes) {
            parameters.add(registeredTypes.get(paramType).resolve());
        }
        return parameters.toArray();
    }

    @VisibleForTesting
    void checkDependencies(Class type, List<Class> classes) throws ResolveException {
        //if it is registered as instance we don't care about constructors
        if (!(registeredTypes.get(type) instanceof Instance)) {
            Constructor constructor = getConstructor(type);
            Class[] params = constructor.getParameterTypes();
            for (Class param : params) {
                if (!registeredTypes.containsKey(param)) {
                    throw new NotRegisteredClassException("Couldn't find class: " + param.getName());
                }
                Class concreteClass = registeredTypes.get(param).type;
                if (classes.contains(concreteClass)) {
                    throw new DependencyCycleException("Cycle in dependencies");
                }
                classes.add(concreteClass);
                checkDependencies(concreteClass, classes);
                classes.remove(concreteClass);
            }
        }
    }

    @VisibleForTesting
    Constructor getConstructor(Class type) throws ResolveException {
        List<Executable> constructors = Arrays.asList(type.getConstructors());
        if (constructors.isEmpty()) {
            constructors = Arrays.asList(type.getDeclaredConstructors());
        }
        List<Executable> annotatedConstructors = getAnnotatedExecutables(constructors);

        switch (annotatedConstructors.size()) {
            case 0:
                List<Integer> lengths = constructors.stream().map(Executable::getParameterCount)
                        .collect(Collectors.toList());
                int maximumLength = Collections.max(lengths);
                if (lengths.indexOf(maximumLength) != lengths.lastIndexOf(maximumLength)) {
                    throw new EqualLengthConstructorsException("Two constructors with the same " +
                            "number of parameters");
                }
                return (Constructor) Collections.max(constructors, Comparator.comparing(Executable::getParameterCount));
            case 1:
                return (Constructor) annotatedConstructors.get(0);
            default:
                throw new TooManyInjectableConstructors("Too many constructors for:" + type.getName());
        }
    }

    public void clear() {
        registeredTypes.clear();
    }

    private List<Executable> getAnnotatedExecutables(List<Executable> executables) {
        return executables.stream()
                .filter(c -> Arrays.stream(c.getDeclaredAnnotations())
                        .map(Annotation::annotationType).collect(Collectors.toList())
                        .contains(Inject.class)).collect(Collectors.toList());
    }
}


class NotRegisteredClassException extends ResolveException {
    NotRegisteredClassException(String message) {
        super(message);
    }
}

class ResolveException extends Exception {

    ResolveException(Throwable e) {
        super(e);
    }

    ResolveException(String message) {
        super(message);
    }
}

class DependencyCycleException extends ResolveException {
    DependencyCycleException(String message) {
        super(message);
    }
}

class EqualLengthConstructorsException extends ResolveException {

    EqualLengthConstructorsException(String message) {
        super(message);
    }
}

class RegisterClassException extends Exception {
    RegisterClassException(String message) {
        super(message);
    }
}

class TooManyInjectableConstructors extends ResolveException {

    TooManyInjectableConstructors(String message) {
        super(message);
    }
}