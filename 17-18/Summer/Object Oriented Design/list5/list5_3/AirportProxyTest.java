package list5.list5_3;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


class AirportProxyTest {

    @Test
    void testLegalTime() throws IllegalAccessException, MaxPoolSizeReachedException {
        TimeLocalFactory.setProvider(LocalTime::now);
        //the same way for the other version
        TimeLocalFactory2.setProvider(LocalTime::now);
        AirportTimeProxy atp = AirportTimeProxy.getInstance(3);
        Plane p = atp.acquirePlane();
        assertNotNull(p);
    }

    @Test
    void testIllegalTime() {
        TimeLocalFactory.setProvider(() -> LocalTime.of(5, 20));
        assertThrows(IllegalAccessException.class, () -> AirportTimeProxy.getInstance(3));
    }

    @Test
    void testLogging() throws MaxPoolSizeReachedException, IllegalAccessException {
        AirportLoggingProxy alp = AirportLoggingProxy.getInstance(3);
        Plane p = alp.acquirePlane();
        alp.releasePlane(p);
    }

}