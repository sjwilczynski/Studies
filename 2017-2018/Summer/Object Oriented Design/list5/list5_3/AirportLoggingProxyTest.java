package list5.list5_3;

import org.junit.jupiter.api.Test;


class AirportLoggingProxyTest {

    @Test
    void testLogging() throws MaxPoolSizeReachedException, IllegalAccessException {
        AirportLoggingProxy alp = AirportLoggingProxy.getInstance(3);
        Plane p = alp.acquirePlane();
        alp.releasePlane(p);
    }

}