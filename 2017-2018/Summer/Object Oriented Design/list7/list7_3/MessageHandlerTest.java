package list7.list7_3;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessageHandlersTest {
    private MessageHandler handler1;

    @Test
    void testArchiveSize() {
        List<Message> messages = initializeMessages();
        for (Message m : messages) {
            handler1.handleMessage(m);
        }
        assertEquals(MessageArchive.getInstance().getAllMessages().size(), 4);
    }

    @Test
    void testGroupSizes() {
        List<Message> messages = initializeMessages();
        for (Message m : messages) {
            handler1.handleMessage(m);
        }
        assertEquals(MessageArchive.getInstance().getMessagesOfType(MessageType.PRAISE).size(), 1);
        assertEquals(MessageArchive.getInstance().getMessagesOfType(MessageType.COMPLAINT).size(), 1);
        assertEquals(MessageArchive.getInstance().getMessagesOfType(MessageType.ORDER).size(), 1);
        assertEquals(MessageArchive.getInstance().getMessagesOfType(MessageType.OTHER).size(), 1);
    }

    private List<Message> initializeMessages() {
        MessageArchive.getInstance().clear();
        handler1 = new ArchiveHandler();
        MessageHandler handler2 = new PraiseHandler();
        MessageHandler handler3 = new ComplaintHandler();
        MessageHandler handler4 = new OrderHandler();
        MessageHandler handler5 = new OtherHandler();
        handler1.setNextHandler(handler2);
        handler2.setNextHandler(handler3);
        handler3.setNextHandler(handler4);
        handler4.setNextHandler(handler5);
        Message m1 = new Message("m1", MessageType.PRAISE);
        Message m2 = new Message("m2", MessageType.COMPLAINT);
        Message m3 = new Message("m3", MessageType.ORDER);
        Message m4 = new Message("m4", MessageType.OTHER);
        return Arrays.asList(m1, m2, m3, m4);
    }
}