package list4_1;

public class Singleton {
    private Singleton() {
    }

    private static Singleton instance;

    public static synchronized Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}

class ThreadSingleton {
    private ThreadSingleton() {
    }

    private static ThreadLocal<ThreadSingleton> instance;

    public static synchronized ThreadSingleton getInstance() {
        if (instance == null) {
            instance = ThreadLocal.withInitial(ThreadSingleton::new);
        }
        return instance.get();
    }
}

class FiveSecSingleton {
    private FiveSecSingleton() {
    }
    private static FiveSecSingleton instance;
    private static long timeAcquired;

    public static synchronized FiveSecSingleton getInstance() {
        if (instance == null || System.currentTimeMillis() - timeAcquired > 5000) {
            instance = new FiveSecSingleton();
            timeAcquired = System.currentTimeMillis();
        }
        return instance;
    }

}