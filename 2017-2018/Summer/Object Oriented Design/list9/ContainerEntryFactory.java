package list9;

import java.util.ArrayList;
import java.util.List;

public class ContainerEntryFactory {

    private static ContainerEntryFactory instance;
    private List<ContainerClassEntryWorker> workers;

    static ContainerEntryFactory getInstance() {
        if (instance == null) {
            instance = new ContainerEntryFactory();
        }
        return instance;
    }

    private ContainerEntryFactory() {
        workers = new ArrayList<>();
        workers.add(new InstantiableWorkerClass());
        workers.add(new SingletonWorkerClass());

    }

    public <T> ContainerEntry<T> createContainerClassEntry(ContainerEntryType type, Class<T> concreteType, SimpleContainer simpleContainer) throws UnknownLifecycleException {
        for (ContainerClassEntryWorker w : workers) {
            if (w.acceptParameters(type)) {
                return w.createContainerEntry(concreteType, simpleContainer);
            }
        }
        throw new UnknownLifecycleException();
    }

    public <T> ContainerEntry<T> createContainerInstanceEntry(T instance) {
        return new Instance<>((Class<T>) instance.getClass(), instance);
    }
}


interface ContainerClassEntryWorker {
    boolean acceptParameters(ContainerEntryType type);

    <T> ContainerEntry<T> createContainerEntry(Class<T> type, SimpleContainer simpleContainer);
}

class InstantiableWorkerClass implements ContainerClassEntryWorker {

    @Override
    public boolean acceptParameters(ContainerEntryType type) {
        return type.equals(ContainerEntryType.INSTANTIABLE);
    }

    @Override
    public <T> ContainerEntry<T> createContainerEntry(Class<T> type, SimpleContainer simpleContainer) {
        return new InstantiableClass<>(type, simpleContainer);
    }

}

class SingletonWorkerClass implements ContainerClassEntryWorker {

    @Override
    public boolean acceptParameters(ContainerEntryType type) {
        return type.equals(ContainerEntryType.SINGLETON);
    }

    @Override
    public <T> ContainerEntry<T> createContainerEntry(Class<T> type, SimpleContainer simpleContainer) {
        return new SingletonClass<>(type, simpleContainer);
    }
}


enum ContainerEntryType {
    SINGLETON, INSTANTIABLE, THREAD
}

class UnknownLifecycleException extends Exception {
}