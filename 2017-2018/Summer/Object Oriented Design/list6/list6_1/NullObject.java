package list6.list6_1;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

interface Logger {
    void log(String message);
}

enum LogType {None, Console, File}

class LoggerFactory {
    private static LoggerFactory loggerFactory;

    public Logger getLogger(LogType logType, String parameters) {
        switch (logType) {
            case File:
                return new FileLogger(parameters);
            case None:
                return new NullLogger();
            case Console:
                return new ConsoleLogger();
            default:
                return null;
        }
    }

    public static LoggerFactory getInstance() {
        if (loggerFactory == null) {
            loggerFactory = new LoggerFactory();
        }
        return loggerFactory;
    }
}

class ConsoleLogger implements Logger {

    @Override
    public void log(String message) {
        System.out.println(message);
    }
}

class FileLogger implements Logger {
    private Path filename;

    FileLogger(String filename) {
        this.filename = Paths.get(filename);
    }

    @Override
    public void log(String message) {
        try {
            Files.write(filename, Collections.singletonList(message), Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class NullLogger implements Logger {
    @Override
    public void log(String message) {
    }
}