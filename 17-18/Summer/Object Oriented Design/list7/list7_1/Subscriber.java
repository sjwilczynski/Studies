package list7.list7_1;

public interface Subscriber {
    void handle(Object notification);
}


enum SubscriberType {
    TREE_SELECTION, ADD_USER, MODIFY_USER
}
