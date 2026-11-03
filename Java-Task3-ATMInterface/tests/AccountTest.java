import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    private static final double DELTA = 0.001;
    private Account account;

    @BeforeEach
    public void setUp() {
        account = new Account("user1", "1234", 1000.00);
    }


    @Test
    public void testThatNewAccountHasOpeningBalanceAndEmptyHistory() {
        assertEquals(1000.00, account.getBalance(), DELTA);
        assertTrue(account.getHistory().isEmpty());
    }


    @Test
    public void testThatOnlyCorrectPinIsAccepted() {
        assertTrue(account.checkPin("1234"));
    }



    @Test
    public void testThatWrong_EmptyOrNullPinIsRejected() {
        assertFalse(account.checkPin("9999"));
        assertFalse(account.checkPin(""));
        assertFalse(account.checkPin(null));
    }


    @Test
    public void testThatDepositIncreasesBalanceAndLogsTransaction() {
        account.deposit(500.00);

        assertEquals(1500.00, account.getBalance(), DELTA);
        assertEquals(1, account.getHistory().size());
    }


    @Test
    public void testThatDepositOfZeroOrNegativeAmountIsRejected() {
        assertFalse(account.deposit(0));
        assertFalse(account.deposit(-50.00));

        assertEquals(1000.00, account.getBalance(), DELTA);
        assertTrue(account.getHistory().isEmpty());
    }

    @Test
    public void testThatIWithdrawAnAmountLessThanMyBalance_transactionSucceeds() {
        assertTrue(account.withdraw(300.00));

        assertEquals(700.00, account.getBalance(), DELTA);
        assertEquals(1, account.getHistory().size());
    }


    @Test
    public void testThatWithdrawExactBalanceLeavesZero() {
        assertTrue(account.withdraw(1000.00));

        assertEquals(0.00, account.getBalance(), DELTA);
    }


    @Test
    public void testThatIWithdrawMoreThanMyBalanceAndNothingChanges() {
        assertFalse(account.withdraw(1000.01));

        assertEquals(1000.00, account.getBalance(), DELTA);
        assertTrue(account.getHistory().isEmpty());
    }


    @Test
    public void testThatIWithdrawZeroOrNegativeAmountAndNothingChanges() {
        assertFalse(account.withdraw(0));
        assertFalse(account.withdraw(-20.00));

        assertEquals(1000.00, account.getBalance(), DELTA);
        assertTrue(account.getHistory().isEmpty());
    }


    @Test
    public void testThatHistoryPreservesOrderOfOperations() {
        account.deposit(200.00);
        account.withdraw(50.00);
        account.deposit(10.00);

        List<Transaction> history = account.getHistory();
        assertEquals(3, history.size());
        assertEquals("DEPOSIT", history.get(0).getType());
        assertEquals("WITHDRAW", history.get(1).getType());
        assertEquals("DEPOSIT", history.get(2).getType());
    }




    @Test
    public void testThatDecimalAmountsAreHandledAccurately() {
        Account accountOne= new Account("user3", "0000", 0.00);

        accountOne.deposit(10.10);
        accountOne.deposit(20.20);

        assertEquals(30.30, accountOne.getBalance(), DELTA);
    }
}