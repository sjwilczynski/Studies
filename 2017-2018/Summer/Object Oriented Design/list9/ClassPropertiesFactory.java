package list9;

import java.util.ArrayList;
import java.util.List;

public class ClassPropertiesFactory {

    private static ClassPropertiesFactory instance;
    private List<ClassPropertiesWorker> workers;

    static ClassPropertiesFactory getInstance() {
        if (instance == null) {
            instance = new ClassPropertiesFactory();
        }
        return instance;
    }

    private ClassPropertiesFactory() {
        workers = new ArrayList<>();
        workers.add(new InstantiableWorker());
        workers.add(new SingletonWorker());

    }

    public <T> ClassProperties<T> createClassProperties(ClassInstanceType type, Class<T> concreteType) throws UnknownLifecycleException {
        for (ClassPropertiesWorker w : workers) {
            if (w.acceptParameters(type)) {
                return w.createClassProperties(concreteType);
            }
        }
        throw new UnknownLifecycleException();
    }
}


interface ClassPropertiesWorker {
    boolean acceptParameters(ClassInstanceType type);

    <T> ClassProperties<T> createClassProperties(Class<T> type);
}

class InstantiableWorker implements ClassPropertiesWorker {

    @Override
    public boolean acceptParameters(ClassInstanceType type) {
        return type.equals(ClassInstanceType.INSTANTIABLE);
    }

    @Override
    public <T> ClassProperties<T> createClassProperties(Class<T> type) {
        return new InstantiableClass<>(type);
    }


}

class SingletonWorker implements ClassPropertiesWorker {

    @Override
    public boolean acceptParameters(ClassInstanceType type) {
        return type.equals(ClassInstanceType.SINGLETON);
    }

    @Override
    public <T> ClassProperties<T> createClassProperties(Class<T> type) {
        return new SingletonClass<>(type);
    }

}


enum ClassInstanceType {
    SINGLETON, INSTANTIABLE, THREAD
}

class UnknownLifecycleException extends Exception {
}