package list9;

import com.google.common.annotations.VisibleForTesting;

import javax.inject.Inject;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
            return containerEntry.resolve();
        } else {
            throw new NotRegisteredClassException("Couldn't find class: " + type.getName());
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
        List<Constructor> constructors = Arrays.asList(type.getConstructors());
        if (constructors.isEmpty()) {
            constructors = Arrays.asList(type.getDeclaredConstructors());
        }
        List<Constructor> annotatedConstructors = constructors.stream()
                .filter(c -> Arrays.stream(c.getDeclaredAnnotations())
                        .map(Annotation::annotationType).collect(Collectors.toList())
                        .contains(Inject.class)).collect(Collectors.toList());

        switch (annotatedConstructors.size()) {
            case 0:
                List<Integer> lengths = constructors.stream().map(Constructor::getParameterCount)
                        .collect(Collectors.toList());
                int maximumLength = Collections.max(lengths);
                if (lengths.indexOf(maximumLength) != lengths.lastIndexOf(maximumLength)) {
                    throw new EqualLengthConstructorsException("Two constructors with the same " +
                            "number of parameters");
                }
                return Collections.max(constructors, Comparator.comparing(Constructor::getParameterCount));
            case 1:
                return annotatedConstructors.get(0);
            default:
                throw new TooManyInjectableConstuctors("Too many constructors for:" + type.getName());


        }
    }

    public void clear() {
        registeredTypes.clear();
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

class TooManyInjectableConstuctors extends ResolveException {

    TooManyInjectableConstuctors(String message) {
        super(message);
    }
}