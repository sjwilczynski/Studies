package list9;

import java.util.HashMap;
import java.util.Map;

public class SimpleContainer {


    private Map<Class, ClassProperties> registeredTypes;

    SimpleContainer() {
        this.registeredTypes = new HashMap<>();
    }


    public <T> void registerType(Class<T> type, ClassInstanceType lifecycle) throws UnknownLifecycleException {
        registerType(type, type, lifecycle);
    }

    public <T, S> void registerType(Class<T> general, Class<S> concrete, ClassInstanceType lifecycle) throws UnknownLifecycleException {
        registeredTypes.put(general, ClassPropertiesFactory.getInstance().createClassProperties(lifecycle, concrete));
    }

    public <T> T resolve(Class<T> type) throws NotRegisteredClassException, ResolveException {
        if (registeredTypes.containsKey(type)) {
            return (T) registeredTypes.get(type).resolve();
        } else {
            throw new NotRegisteredClassException();
        }
    }
}


abstract class ClassProperties<T> {

    Class<T> type;

    ClassProperties(Class<T> type) {
        this.type = type;
    }

    public abstract T resolve() throws ResolveException;
}

class SingletonClass<T> extends ClassProperties<T> {

    private T instance;

    SingletonClass(Class<T> type) {
        super(type);
    }

    @Override
    public T resolve() throws ResolveException {
        if (instance == null) {
            try {
                instance = type.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new ResolveException(e);
            }
        }
        return instance;
    }
}

class InstantiableClass<T> extends ClassProperties<T> {

    InstantiableClass(Class<T> type) {
        super(type);
    }

    @Override
    public T resolve() throws ResolveException {
        try {
            return type.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ResolveException(e);
        }
    }
}


class NotRegisteredClassException extends Exception {
}

class ResolveException extends Exception {

    ResolveException(Throwable e) {
        super(e);
    }
}