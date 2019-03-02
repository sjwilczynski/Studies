package list8.list8_4;

import java.util.ArrayList;
import java.util.List;

class StateSetterFactory {
    private static StateSetterFactory instance;
    private List<StateSetterWorker> workers;

    static StateSetterFactory getInstance() {
        if (instance == null) {
            instance = new StateSetterFactory();
        }
        return instance;
    }

    private StateSetterFactory() {
        workers = new ArrayList<>();
        workers.add(new IdleWorker());
        workers.add(new SelectWorker());
        workers.add(new CheckAccountWorker());
        workers.add(new WithdrawWorker());
        workers.add(new PrintConfirmationWorker());
    }

    public StateSetter createStateSetter(ATMOperation operation, StateParameters parameters) {
        for (StateSetterWorker w : workers) {
            if (w.acceptParameters(operation)) {
                return w.createStateSetter(parameters);
            }
        }
        return null;
    }
}


interface StateSetterWorker {
    boolean acceptParameters(ATMOperation operation);

    StateSetter createStateSetter(StateParameters parameters);
}

class IdleWorker implements StateSetterWorker {

    @Override
    public boolean acceptParameters(ATMOperation operation) {
        return operation.equals(ATMOperation.CANCEL);
    }

    @Override
    public StateSetter createStateSetter(StateParameters parameters) {
        return new IdleStateSetter(parameters);
    }
}

class SelectWorker implements StateSetterWorker {

    @Override
    public boolean acceptParameters(ATMOperation operation) {
        return operation.equals(ATMOperation.SELECT);
    }

    @Override
    public StateSetter createStateSetter(StateParameters parameters) {
        return new SelectStateSetter(parameters);
    }
}

class CheckAccountWorker implements StateSetterWorker {

    @Override
    public boolean acceptParameters(ATMOperation operation) {
        return operation.equals(ATMOperation.CHECK_ACCOUNT);
    }

    @Override
    public StateSetter createStateSetter(StateParameters parameters) {
        return new CheckAccountStateSetter(parameters);
    }
}

class WithdrawWorker implements StateSetterWorker {

    @Override
    public boolean acceptParameters(ATMOperation operation) {
        return operation.equals(ATMOperation.WITHDRAW);
    }

    @Override
    public StateSetter createStateSetter(StateParameters parameters) {
        return new WithdrawStateSetter(parameters);
    }
}

class PrintConfirmationWorker implements StateSetterWorker {

    @Override
    public boolean acceptParameters(ATMOperation operation) {
        return operation.equals(ATMOperation.PRINT_CONFIRMATION);
    }

    @Override
    public StateSetter createStateSetter(StateParameters parameters) {
        return new PrintConfirmationStateSetter(parameters);
    }
}

