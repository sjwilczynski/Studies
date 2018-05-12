package list7.list7_3;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class Message {
    private String message;

    public MessageType getType() {
        return type;
    }

    private MessageType type;

    Message(String message, MessageType type) {
        this.message = message;
        this.type = type;
    }
}

class MessageArchive {
    private List<Message> archive;
    private static MessageArchive instance;

    static MessageArchive getInstance() {
        if (instance == null) {
            instance = new MessageArchive();
        }
        return instance;
    }

    private MessageArchive() {
        archive = new ArrayList<>();
    }

    void clear() {
        archive.clear();
    }

    void archiveMessage(Message message) {
        archive.add(message);
    }

    List<Message> getMessagesOfType(MessageType type) {
        return archive.stream().filter(x -> x.getType().equals(type)).collect(Collectors.toList());
    }

    List<Message> getAllMessages() {
        return new ArrayList<>(archive);
    }
}

enum MessageType {
    PRAISE, COMPLAINT, ORDER, OTHER
}