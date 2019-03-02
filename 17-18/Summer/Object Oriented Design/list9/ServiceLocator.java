package list9;

import java.util.function.Supplier;

public class ServiceLocator {

    private Supplier<SimpleContainer> provider;
    private static ServiceLocator ourInstance = new ServiceLocator();

    public static ServiceLocator getInstance() {
        return ourInstance;
    }

    private ServiceLocator() {
    }

    public void setProvider(Supplier<SimpleContainer> provider) {
        this.provider = provider;
    }

    public <T> T getInstance(Class<T> type) throws ResolveException {
        if (type.equals(SimpleContainer.class)) {
            return (T) provider.get();
        } else {
            return provider.get().resolve(type);
        }
    }
}
