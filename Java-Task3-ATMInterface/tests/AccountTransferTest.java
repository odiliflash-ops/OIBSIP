import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AccountTransferTest {

    private static final double DELTA = 0.001;
    private Account sender;
    private Account recipient;

    @BeforeEach
    public void setUp() {
        sender = new Account("user1", "1234", 1000.00);
        recipient = new Account("user2", "4321", 200.00);
    }


    @Test
    public void testThatValidTransferMovesMoneyBetweenAccounts() {
        assertTrue(sender.sendTo(recipient, 300.00));

        assertEquals(700.00, sender.getBalance(), DELTA);
        assertEquals(500.00, recipient.getBalance(), DELTA);
    }



    @Test
    public void testThatBothAccountsLogsTheTransfer() {
        sender.sendTo(recipient, 300.00);

        assertEquals(1, sender.getHistory().size());
        assertEquals(1, recipient.getHistory().size());

        assertTrue(sender.getHistory().get(0).getType().startsWith("TRANSFER OUT"));
        assertTrue(recipient.getHistory().get(0).getType().startsWith("TRANSFER IN"));

        assertEquals(300.00, sender.getHistory().get(0).getAmount(), DELTA);
        assertEquals(300.00, recipient.getHistory().get(0).getAmount(), DELTA);
    }



    @Test
    public void testThatTransferAboveBalanceFailsAndChangesNothing() {
        assertFalse(sender.sendTo(recipient, 1000.01));

        assertEquals(1000.00, sender.getBalance(), DELTA);
        assertEquals(200.00, recipient.getBalance(), DELTA);
        assertTrue(sender.getHistory().isEmpty());
        assertTrue(recipient.getHistory().isEmpty());
    }



    @Test
    public void testThatTransferOfZeroOrNegativeAmountFailsAndChangesNothing() {
        assertFalse(sender.sendTo(recipient, 0));
        assertFalse(sender.sendTo(recipient, -50.00));

        assertEquals(1000.00, sender.getBalance(), DELTA);
        assertEquals(200.00, recipient.getBalance(), DELTA);

        assertTrue(sender.getHistory().isEmpty());
        assertTrue(recipient.getHistory().isEmpty());
    }


    @Test
    public void testThatIf_I_transferToMyself_itIsRejected() {
        assertFalse(sender.sendTo(sender, 100.00));

        assertEquals(1000.00, sender.getBalance(), DELTA);
        assertTrue(sender.getHistory().isEmpty());
    }


    @Test
    public void testThatTransferToNullRecipientIsRejected() {
        assertFalse(sender.sendTo(null, 100.00));

        assertEquals(1000.00, sender.getBalance(), DELTA);
        assertTrue(sender.getHistory().isEmpty());
    }


    @Test
    public void testThatTotalMoneyIsUnchangedByTransfers() {
        double before = sender.getBalance() + recipient.getBalance();

        sender.sendTo(recipient, 250.00);
        sender.sendTo(recipient, 99999.00); // fails
        recipient.sendTo(sender, 75.50);

        double after = sender.getBalance() + recipient.getBalance();
        assertEquals(before, after, DELTA);
    }



    @Test
    public void testThatTransferOfEntireBalanceLeavesSenderAtZero() {
        assertTrue(sender.sendTo(recipient, 1000.00));

        assertEquals(0.00, sender.getBalance(), DELTA);
        assertEquals(1200.00, recipient.getBalance(), DELTA);
    }
}