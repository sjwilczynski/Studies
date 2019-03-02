package list8.list8_1;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.Random;

public class Invoker {

    private Queue<Command> commands;
    private Generator generator;
    private Dispatcher dispatcher1;
    private Dispatcher dispatcher2;

    Invoker() {
        commands = new LinkedList<>();
    }

    void start() {
        generator = new Generator(commands);
        dispatcher1 = new Dispatcher(commands, 0);
        dispatcher2 = new Dispatcher(commands, 1);
        generator.start();
        dispatcher1.start();
        dispatcher2.start();
    }

    int getGeneratorCount() {
        return generator.getGenerated();
    }

    int getDispatcher1Count() {
        return dispatcher1.getExecuted();
    }

    int getDispatcher2Count() {
        return dispatcher2.getExecuted();
    }

    void turnOffDispatchers() {
        dispatcher1.setRunning(false);
        dispatcher2.setRunning(false);
    }

    void joinAll() throws InterruptedException {
        generator.join();
        dispatcher1.join();
        dispatcher2.join();
    }
}

class Generator extends Thread {

    static final int MAX_GENERATED = 50;
    private static final int QUEUE_SIZE = 10;
    private static final int GENERATOR_SLEEP_TIME = 100;
    //synchronized method locks only the object on which method is invoked
    //adding lockObject helps synchronizing multiple instances
    private static final Object lockObject = new Object();

    public int getGenerated() {
        return generated;
    }

    private int generated;
    private Queue<Command> commands;

    Generator(Queue<Command> commands) {
        this.commands = commands;
    }

    @Override
    public void run() {
        System.out.println("Run generator");
        while (generated < MAX_GENERATED) {
            if (commands.size() < QUEUE_SIZE) {
                Command command = generateCommand();
                addCommand(command);
            }
            try {
                sleep(new Random().nextInt(GENERATOR_SLEEP_TIME));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void addCommand(Command command) {
        synchronized (lockObject) {
            commands.add(command);
            generated++;
        }
    }

    private Command generateCommand() {
        switch (generated % 4) {
            case 0:
                return new DownloadFileFTP("adFTP" + generated);
            case 1:
                return new DownloadFileHTTP("adHTTP" + generated);
            case 2:
                return new RandomFileCreation("fn" + generated);
            default:
                return new MovingFile("old" + generated, "new" + generated);
        }
    }
}

class Dispatcher extends Thread {
    private final int DISPATCHER_SLEEP_TIME = 300;
    private int executed;
    private Queue<Command> commands;
    private boolean running = true;
    private int id;
    private static final Object lockObject = new Object();

    Dispatcher(Queue<Command> commands, int id) {
        this.commands = commands;
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("Run dispatcher");
        while (running || commands.size() > 0) {
            processCommand();
            try {
                sleep(new Random().nextInt(DISPATCHER_SLEEP_TIME));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    private void processCommand() {
        synchronized (lockObject) {
            Optional<Command> command = getCommand();
            if (command.isPresent()) {
                System.out.print(id + ": ");
                command.get().execute();
                executed++;
            }
        }
    }

    private Optional<Command> getCommand() {
        if (commands.size() == 0) {
            return Optional.empty();
        } else {
            return Optional.of(commands.remove());
        }
    }

    public int getExecuted() {
        return executed;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}