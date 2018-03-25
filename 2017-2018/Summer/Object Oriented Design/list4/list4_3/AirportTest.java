package list4.list4_3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AirportTest {

    @Test
    void testAcquire() throws MaxPoolSizeReachedException {
        Airport airport = Airport.getInstance();
        Plane p1 = airport.acquirePlane();
        Plane p2 = airport.acquirePlane();
        assertNotEquals(p1, p2);
    }

    @Test
    void testGoodRelease() throws MaxPoolSizeReachedException {
        Airport airport = Airport.getInstance();
        Plane p1 = airport.acquirePlane();
        airport.acquirePlane();
        airport.releasePlane(p1);
        p1 = airport.acquirePlane();
        assertNotNull(p1);
    }

    @Test
    void testReleaseWrongAirport() throws MaxPoolSizeReachedException {
        Airport a1 = Airport.getInstance();
        Airport a2 = Airport.getInstance();
        Plane p1 = a1.acquirePlane();
        assertThrows(IllegalArgumentException.class, () -> a2.releasePlane(p1));
    }

    @Test
    void testReleaseTwice() throws MaxPoolSizeReachedException {
        Airport a1 = Airport.getInstance();
        Plane p1 = a1.acquirePlane();
        a1.releasePlane(p1);
        assertThrows(IllegalArgumentException.class, () -> a1.releasePlane(p1));
    }

    @Test
    void testMaxPoolSize() {
        Airport a1 = Airport.getInstance();
        Plane[] planes = new Plane[4];
        assertThrows(MaxPoolSizeReachedException.class, () ->
        {
            for (int i = 0; i < 4; i++) {
                planes[i] = a1.acquirePlane();
            }
        });
    }
}