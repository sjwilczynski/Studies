package list8.list8_2;

abstract class DataAccessHandler {

    abstract void openConnection() throws ConnectionException;

    abstract void getData() throws ConnectionException;

    abstract void processData();

    abstract void closeConnection();

    void execute() {

        try {
            openConnection();
            getData();
            processData();
        } catch (ConnectionException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }
}

class ConnectionException extends Exception {
    ConnectionException(String message) {
        super(message);
    }
}
