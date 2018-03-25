package list4.list4_3;

import java.util.LinkedList;
import java.util.Random;

public class Airport {

    private LinkedList<Plane> free;
    private LinkedList<Plane> acquired;
    long max_size;


    private Airport(int max_size) {
        this.max_size = max_size;
        this.free = new LinkedList<>();
        this.acquired = new LinkedList<>();
    }

    public static Airport getInstance(){
        return new Airport(3);
    }

    public synchronized Plane acquirePlane() throws MaxPoolSizeReachedException {
        if (free.size() > 0){
            Plane plane = free.poll();
            acquired.add(plane);
            return plane;
        } else {
            if (acquired.size() < max_size){
                Plane plane = new Plane(new Random().nextInt(10));
                acquired.add(plane);
                return plane;
            } else{
                throw new MaxPoolSizeReachedException();
            }
        }
    }

    public synchronized void releasePlane(Plane plane){
        if(acquired.contains(plane)){
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

class MaxPoolSizeReachedException extends Exception {}