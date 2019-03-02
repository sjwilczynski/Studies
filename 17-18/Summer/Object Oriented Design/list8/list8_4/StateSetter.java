package list8.list8_4;

abstract class StateSetter {


    ATMOperation operation;

    StateSetter(StateParameters parameters) {
        this.parameters = parameters;
    }

    StateParameters parameters;

    abstract void setState(ATM atm);


    public ATMOperation getOperation() {
        return operation;
    }
}

class IdleStateSetter extends StateSetter {

    IdleStateSetter(StateParameters parameters) {
        super(parameters);
        operation = ATMOperation.CANCEL;
    }

    @Override
    void setState(ATM atm) {
        atm.setState(new IdleState(parameters));
    }
}


class SelectStateSetter extends StateSetter {

    SelectStateSetter(StateParameters parameters) {
        super(parameters);
        operation = ATMOperation.SELECT;
    }

    @Override
    void setState(ATM atm) {
        atm.setState(new SelectState(parameters));
    }
}


class CheckAccountStateSetter extends StateSetter {

    CheckAccountStateSetter(StateParameters parameters) {
        super(parameters);
        operation = ATMOperation.CHECK_ACCOUNT;
    }

    @Override
    void setState(ATM atm) {
        atm.setState(new CheckAccountState(parameters));
    }
}


class WithdrawStateSetter extends StateSetter {

    WithdrawStateSetter(StateParameters parameters) {
        super(parameters);
        operation = ATMOperation.WITHDRAW;
    }

    @Override
    void setState(ATM atm) {
        atm.setState(new WithdrawState(parameters));
    }
}

class PrintConfirmationStateSetter extends StateSetter {

    PrintConfirmationStateSetter(StateParameters parameters) {
        super(parameters);
        operation = ATMOperation.PRINT_CONFIRMATION;
    }

    @Override
    void setState(ATM atm) {
        atm.setState(new PrintConfirmationState(parameters));
    }
}



