package list8.list8_3;


interface DataAccessStrategy {
    void openConnection() throws ConnectionException;

    void getData() throws ConnectionException;

    void processData();

    void closeConnection();
}

class DataAccessHandler {

    DataAccessHandler(DataAccessStrategy strategy) {
        this.strategy = strategy;
    }

    private DataAccessStrategy strategy;

    void execute() {
        try {
            strategy.openConnection();
            strategy.getData();
            strategy.processData();
        } catch (ConnectionException e) {
            e.printStackTrace();
        } finally {
            strategy.closeConnection();
        }
    }

}


class ConnectionException extends Exception {
    ConnectionException(String message) {
        super(message);
    }
}