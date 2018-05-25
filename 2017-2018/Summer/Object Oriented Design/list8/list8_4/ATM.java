package list8.list8_4;

import java.util.List;
//We could change it so that there would be one handle for each state - more code but better reflection of State Machine

class ATM {

    private ATMState state;

    ATM() {
        state = new IdleState(null);
    }

    public void setState(ATMState state) {
        this.state = state;
    }

    void doOperation(ATMOperation operation, StateParameters parameters) throws InvalidOperationException {
        state.handle(this, StateSetterFactory.getInstance().createStateSetter(operation, parameters));
    }

    public ATMState getState() {
        return state;
    }

}

enum ATMOperation {
    SELECT, CHECK_ACCOUNT, WITHDRAW, CANCEL, PRINT_CONFIRMATION
}

abstract class ATMState {
    List<ATMOperation> allowedOperations;
    private StateParameters parameters;

    ATMState(StateParameters parameters) {
        this.parameters = parameters;
    }

    StateParameters getParameters() {
        return parameters;
    }

    private boolean acceptsOperation(ATMOperation operation) {
        return allowedOperations.contains(operation);
    }

    void handle(ATM atm, StateSetter setter) throws InvalidOperationException {
        if (acceptsOperation(setter.getOperation())) {
            setter.setState(atm);
        } else {
            throw new InvalidOperationException();
        }
    }
}

abstract class StateParameters {
}

class CashAmount extends StateParameters {
    public int getCashAmount() {
        return cashAmount;
    }

    private int cashAmount;

    CashAmount(int cashAmount) {
        this.cashAmount = cashAmount;
    }
}


class InvalidOperationException extends Exception {
}
