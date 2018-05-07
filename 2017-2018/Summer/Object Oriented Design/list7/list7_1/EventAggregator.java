package list7.list7_1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventAggregator {

    private Map<SubscriberType, List<Subscriber>> subscribers;

    private EventAggregator() {
        subscribers = new HashMap<>();
    }

    private static EventAggregator instance;

    public static EventAggregator getInstance() {
        if (instance == null) {
            instance = new EventAggregator();
        }
        return instance;
    }

    void addSubscriber(SubscriberType type, Subscriber subscriber) {
        if (!subscribers.containsKey(type)) {
            subscribers.put(type, new ArrayList<>());
        }
        subscribers.get(type).add(subscriber);
    }

    void removeSubscriber(SubscriberType type, Subscriber subscriber) {
        if (subscribers.containsKey(type)) {
            subscribers.get(type).remove(subscriber);
        }
    }

    <T> void publish(SubscriberType type, T event) {
        System.out.println("Event in aggregator " + event.toString());
        if (subscribers.containsKey(type)) {
            for (Subscriber sub : subscribers.get(type)) {
                sub.handle(event);
            }
        }
    }
}
