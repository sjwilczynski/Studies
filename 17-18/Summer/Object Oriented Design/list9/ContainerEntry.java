package list9;

abstract class ContainerEntry<T> {

    Class<T> type;

    ContainerEntry(Class<T> type) {
        this.type = type;
    }

    public abstract T resolve() throws ResolveException;
}

class SingletonClass<T> extends ContainerEntry<T> {

    private T instance;
    private SimpleContainer container;

    SingletonClass(Class<T> type, SimpleContainer container) {
        super(type);
        this.container = container;
    }

    @Override
    public T resolve() throws ResolveException {
        if (instance == null) {
            instance = container.createNewInstance(type);
        }
        return instance;
    }
}

class InstantiableClass<T> extends ContainerEntry<T> {

    private SimpleContainer container;

    InstantiableClass(Class<T> type, SimpleContainer container) {
        super(type);
        this.container = container;
    }

    @Override
    public T resolve() throws ResolveException {
        return container.createNewInstance(type);
    }
}

class Instance<T> extends ContainerEntry<T> {

    private T instance;

    Instance(Class<T> type, T instance) {
        super(type);
        this.instance = instance;
    }

    @Override
    public T resolve() {
        return instance;
    }
}