package list4_1;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;


class SingletonTest {

    @Test
    void singletonTest() {
        Singleton s1 = Singleton.getInstance();
        Singleton s2 = Singleton.getInstance();
        assertNotNull(s1);
        assertNotNull(s2);
        assertEquals(s1, s2);
    }

    @Test
    void threadSingletonTest() throws InterruptedException {
        final ThreadSingleton[] s1 = new ThreadSingleton[2];
        final ThreadSingleton[] s2 = new ThreadSingleton[1];
        Thread t1 = new Thread(() -> {
            s1[0] = ThreadSingleton.getInstance();
            s1[1] = ThreadSingleton.getInstance();
        });
        Thread t2 = new Thread(() -> s2[0] = ThreadSingleton.getInstance());
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        assertNotNull(s1[0]);
        assertNotNull(s2[0]);
        assertEquals(s1[0], s1[1]);
        assertNotEquals(s1[0], s2[0]);
    }

    @Test
    void fiveSecondSingletonTest() throws InterruptedException {
        FiveSecSingleton s1 = FiveSecSingleton.getInstance();
        TimeUnit.SECONDS.sleep(3);
        FiveSecSingleton s2 = FiveSecSingleton.getInstance();
        TimeUnit.SECONDS.sleep(3);
        FiveSecSingleton s3 = FiveSecSingleton.getInstance();

        assertNotNull(s1);
        assertNotNull(s2);
        assertNotNull(s3);
        assertEquals(s1, s2);
        assertNotEquals(s1, s3);
    }
}