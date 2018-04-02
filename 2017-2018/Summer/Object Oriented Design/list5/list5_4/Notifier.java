package list5.list5_4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface Notifier {
    String notifyPeople(List<Person> people);
}

class SMSNotifier implements Notifier {

    @Override
    public String notifyPeople(List<Person> people) {
        StringBuilder message = new StringBuilder();
        for (Person person : people) {
            message.append("Notifying person by sms: ").append(person.name).append("\n");
        }
        return message.toString();
    }
}

class MailNotifier implements Notifier {

    @Override
    public String notifyPeople(List<Person> people) {
        StringBuilder message = new StringBuilder();
        for (Person person : people) {
            message.append("Notifying person by mail: ").append(person.name).append("\n");
        }
        return message.toString();
    }
}

interface Loader {
    List<Person> loadPeople();
}

class XMLLoader implements Loader {

    @Override
    public List<Person> loadPeople() {
        return new ArrayList<>(Arrays.asList(new Person("xmlP1"), new Person("xmlP2")));
    }
}

class DBLoader implements Loader {

    @Override
    public List<Person> loadPeople() {
        return new ArrayList<>(Arrays.asList(new Person("dbP1"), new Person("dbP2")));
    }
}

