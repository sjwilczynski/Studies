package list8.list8_4;

import java.util.Arrays;
import java.util.Collections;

class IdleState extends ATMState {

    IdleState(StateParameters parameters) {
        super(parameters);
        allowedOperations = Collections.singletonList(ATMOperation.SELECT);
    }

    @Override
    void handle(ATM atm, StateSetter setter) throws InvalidOperationException {
        super.handle(atm, setter);
        System.out.println("Changing to state: " + setter.getOperation().toString() + " from idle");
    }
}

class SelectState extends ATMState {

    SelectState(StateParameters parameters) {
        super(parameters);
        allowedOperations = Arrays.asList(ATMOperation.WITHDRAW, ATMOperation.CANCEL, ATMOperation.CHECK_ACCOUNT);
    }

    @Override
    void handle(ATM atm, StateSetter setter) throws InvalidOperationException {
        super.handle(atm, setter);
        System.out.println("Changing to state: " + setter.getOperation().toString() + "from select");
    }
}


class CheckAccountState extends ATMState {

    CheckAccountState(StateParameters parameters) {
        super(parameters);
        allowedOperations = Arrays.asList(ATMOperation.WITHDRAW, ATMOperation.CANCEL);
    }

    @Override
    void handle(ATM atm, StateSetter setter) throws InvalidOperationException {
        super.handle(atm, setter);
        System.out.println("Changing to state: " + setter.getOperation().toString() + "from check account");
    }
}

class WithdrawState extends ATMState {

    WithdrawState(StateParameters parameters) {
        super(parameters);
        allowedOperations = Arrays.asList(ATMOperation.CANCEL, ATMOperation.PRINT_CONFIRMATION);
    }

    @Override
    void handle(ATM atm, StateSetter setter) throws InvalidOperationException {
        super.handle(atm, setter);
        System.out.println("Changing to state: " + setter.getOperation().toString() + "from withdraw with " + ((CashAmount) getParameters()).getCashAmount());
    }
}

class PrintConfirmationState extends ATMState {

    PrintConfirmationState(StateParameters parameters) {
        super(parameters);
        allowedOperations = Collections.singletonList(ATMOperation.CANCEL);
    }

    @Override
    void handle(ATM atm, StateSetter setter) throws InvalidOperationException {
        super.handle(atm, setter);
        System.out.println("Changing to state: " + setter.getOperation().toString() + "from print confirmation");
    }
}