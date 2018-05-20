package list5.list5_3;


import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AirportTimeProxy extends Airport {

    private static LocalTime startHour = LocalTime.of(8, 0);
    private static LocalTime endHour = LocalTime.of(22, 0);

    private AirportTimeProxy(int max_size) {
        super(max_size);
    }

    public static AirportTimeProxy getInstance(int max_size) throws IllegalAccessException {
        checkTime();
        return new AirportTimeProxy(max_size);
    }

    public synchronized Plane acquirePlane() throws MaxPoolSizeReachedException, IllegalAccessException {
        checkTime();
        return super.acquirePlane();
    }

    public synchronized void releasePlane(Plane plane) throws IllegalAccessException {
        checkTime();
        super.releasePlane(plane);
    }

    private static void checkTime() throws IllegalAccessException {
        LocalTime currentTime = new TimeLocalFactory().getTime();
        if (currentTime.isBefore(startHour) || currentTime.isAfter(endHour)) {
            throw new IllegalAccessException("Tried to access, when factory was closed");
        }
    }
}

class AirportLoggingProxy extends Airport {
    private static final Logger logger = Logger.getLogger(AirportLoggingProxy.class.getName());

    private AirportLoggingProxy(int max_size) {
        super(max_size);
    }

    public static AirportLoggingProxy getInstance(int max_size) {
        logger.log(Level.INFO, LocalDateTime.now() + " Getting instance with max size=" + max_size);
        AirportLoggingProxy returnedValue = new AirportLoggingProxy(max_size);
        logger.log(Level.INFO, LocalDateTime.now() + " Returning new airport" + returnedValue.toString());
        return returnedValue;
    }

    public synchronized Plane acquirePlane() throws MaxPoolSizeReachedException, IllegalAccessException {
        logger.log(Level.INFO, LocalDateTime.now() + " Acquiring new plane");
        Plane returnedValue = super.acquirePlane();
        logger.log(Level.INFO, LocalDateTime.now() + " Acquired plane " + returnedValue.toString());
        return returnedValue;
    }

    public synchronized void releasePlane(Plane plane) throws IllegalAccessException {
        logger.log(Level.INFO, LocalDateTime.now() + " Releasing plane " + plane.toString());
        super.releasePlane(plane);
        logger.log(Level.INFO, LocalDateTime.now() + " Plane release");
    }

}


class Airport {

    private LinkedList<Plane> free;
    private LinkedList<Plane> acquired;
    private long max_size;


    Airport(int max_size) {
        this.max_size = max_size;
        this.free = new LinkedList<>();
        this.acquired = new LinkedList<>();
    }

    public static Airport getInstance(int max_size) throws IllegalAccessException {
        return new Airport(max_size);
    }

    public synchronized Plane acquirePlane() throws MaxPoolSizeReachedException, IllegalAccessException {
        if (free.size() > 0) {
            Plane plane = free.poll();
            acquired.add(plane);
            return plane;
        } else {
            if (acquired.size() < max_size) {
                Plane plane = new Plane(new Random().nextInt(10));
                acquired.add(plane);
                return plane;
            } else {
                throw new MaxPoolSizeReachedException();
            }
        }
    }

    public synchronized void releasePlane(Plane plane) throws IllegalAccessException {
        if (acquired.contains(plane)) {
            acquired.remove(plane);
            free.add(plane);
        } else {
            throw new IllegalArgumentException("Plane was not part of the pool");
        }
    }
}

class Plane {
    private long id;

    Plane(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}

class MaxPoolSizeReachedException extends Exception {
}