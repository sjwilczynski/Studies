package list5.list5_4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


abstract class PersonRegistry {
    List<Person> people;

    abstract public void loadPeople();

    abstract String notifyPeople();
}

abstract class LoaderPersonRegistry extends PersonRegistry {

    abstract public void loadPeople();

    private Notifier notifier;

    public String notifyPeople() {
        return notifier.notifyPeople(people);
    }

    LoaderPersonRegistry(Notifier notifier) {
        this.notifier = notifier;
    }
}

class XMLLoaderPersonRegistry extends LoaderPersonRegistry {

    XMLLoaderPersonRegistry(Notifier notifier) {
        super(notifier);
    }

    @Override
    public void loadPeople() {
        people = new ArrayList<>(Arrays.asList(new Person("xmlP1"), new Person("xmlP2")));
    }
}

class DBLoaderPersonRegistry extends LoaderPersonRegistry {

    DBLoaderPersonRegistry(Notifier notifier) {
        super(notifier);
    }

    @Override
    public void loadPeople() {
        people = new ArrayList<>(Arrays.asList(new Person("dbP1"), new Person("dbP2")));
    }
}


abstract class NotifierPersonRegistry extends PersonRegistry {
    private Loader loader;

    public void loadPeople() {
        people = loader.loadPeople();
    }

    abstract public String notifyPeople();

    NotifierPersonRegistry(Loader loader) {
        this.loader = loader;
    }
}

class SMSNotifierPersonRegistry extends NotifierPersonRegistry {

    SMSNotifierPersonRegistry(Loader loader) {
        super(loader);
    }

    @Override
    public String notifyPeople() {
        StringBuilder message = new StringBuilder();
        for (Person person : people) {
            message.append("Notifying person by sms: ").append(person.name).append("\n");
        }
        return message.toString();
    }
}

class MailNotifierPersonRegistry extends NotifierPersonRegistry {

    MailNotifierPersonRegistry(Loader loader) {
        super(loader);
    }

    @Override
    public String notifyPeople() {
        StringBuilder message = new StringBuilder();
        for (Person person : people) {
            message.append("Notifying person by mail: ").append(person.name).append("\n");
        }
        return message.toString();
    }
}


class Person {
    public String name;

    Person(String name) {
        this.name = name;
    }
}