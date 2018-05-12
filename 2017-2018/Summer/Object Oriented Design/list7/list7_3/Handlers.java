package list7.list7_3;

abstract class MessageHandler {

    MessageHandler nextHandler;

    abstract void handleMessage(Message message);

    void setNextHandler(MessageHandler handler) {
        if (nextHandler == null) {
            nextHandler = handler;
        } else {
            nextHandler.setNextHandler(handler);
        }
    }
}

class ArchiveHandler extends MessageHandler {

    @Override
    void handleMessage(Message message) {
        MessageArchive.getInstance().archiveMessage(message);
        if (nextHandler != null) {
            nextHandler.handleMessage(message);
        }
    }
}

class PraiseHandler extends MessageHandler {
    @Override
    void handleMessage(Message message) {
        if (message.getType().equals(MessageType.PRAISE)) {
            System.out.println("Praise message");
        } else if (nextHandler != null) {
            nextHandler.handleMessage(message);
        }

    }
}

class ComplaintHandler extends MessageHandler {
    @Override
    void handleMessage(Message message) {
        if (message.getType().equals(MessageType.COMPLAINT)) {
            System.out.println("Complaint message");
        } else if (nextHandler != null) {
            nextHandler.handleMessage(message);
        }

    }
}

class OrderHandler extends MessageHandler {
    @Override
    void handleMessage(Message message) {
        if (message.getType().equals(MessageType.ORDER)) {
            System.out.println("Order message");
        } else if (nextHandler != null) {
            nextHandler.handleMessage(message);
        }

    }
}

class OtherHandler extends MessageHandler {
    @Override
    void handleMessage(Message message) {
        if (message.getType().equals(MessageType.OTHER)) {
            System.out.println("Other message");
        } else if (nextHandler != null) {
            nextHandler.handleMessage(message);
        }
    }
}
