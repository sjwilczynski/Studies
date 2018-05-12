package list8.list8_1;

import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FileManagerTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeAll
    void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterAll
    void restore() {
        System.setOut(System.out);
    }

    @BeforeEach
    void resetStream() {
        outContent.reset();
    }

    @Test
    void downloadFileFTP() {
        String fileAddress = "address";
        Command command = new DownloadFileFTP(fileAddress);
        command.execute();
        assertEquals("Download file by FTP from " + fileAddress + "\n", outContent.toString());
    }

    @Test
    void downloadFileHTTP() {
        String fileAddress = "address";
        Command command = new DownloadFileHTTP(fileAddress);
        command.execute();
        assertEquals("Download file by HTTP from " + fileAddress + "\n", outContent.toString());
    }
}

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class InvokerTest {

    @Test
    void testInvoker() throws InterruptedException {
        Invoker invoker = new Invoker();
        invoker.start();
        int generatorCount = 0;
        while (generatorCount != Generator.MAX_GENERATED) {
            generatorCount = invoker.getGeneratorCount();
            Thread.sleep(1000);
        }
        invoker.turnOffDispatchers();
        invoker.joinAll();
        assertEquals(generatorCount, invoker.getDispatcher1Count() + invoker.getDispatcher2Count());
    }
}