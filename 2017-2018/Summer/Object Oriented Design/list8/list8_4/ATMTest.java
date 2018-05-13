package list8.list8_4;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ATMTest {

    @Test
    void testATM() throws InvalidOperationException {
        ATM atm = new ATM();
        atm.doOperation(ATMOperation.SELECT, null);
        assertTrue(atm.getState() instanceof SelectState);
        atm.doOperation(ATMOperation.CHECK_ACCOUNT, null);
        assertTrue(atm.getState() instanceof CheckAccountState);
        atm.doOperation(ATMOperation.WITHDRAW, new CashAmount(100));
        assertTrue(atm.getState() instanceof WithdrawState);
        atm.doOperation(ATMOperation.PRINT_CONFIRMATION, null);
        assertTrue(atm.getState() instanceof PrintConfirmationState);
        atm.doOperation(ATMOperation.CANCEL, null);
        assertTrue(atm.getState() instanceof IdleState);
    }
}