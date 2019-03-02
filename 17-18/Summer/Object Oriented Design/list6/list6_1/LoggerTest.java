package list6.list6_1;

import org.junit.jupiter.api.Test;

class LoggerTest {

    private LoggerFactory loggerFactory = LoggerFactory.getInstance();

    @Test
    void testConsoleLogger() {
        ConsoleLogger logger = (ConsoleLogger) loggerFactory.getLogger(LogType.Console, null);
        logger.log("message1");
    }

    @Test
    void testFileLogger() {
        FileLogger logger = (FileLogger) loggerFactory.getLogger(LogType.File, "log.txt");
        logger.log("message2");
    }

    @Test
    void testNullLogger() {
        NullLogger logger = (NullLogger) loggerFactory.getLogger(LogType.None, null);
        logger.log("message3");
    }

}