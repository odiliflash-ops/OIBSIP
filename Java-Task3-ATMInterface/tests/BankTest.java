import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankTest {

    private Bank bank;
    private Account user1;
    private Account user2;

    @BeforeEach
    public void setUp() {
        bank = new Bank();

        user1 = new Account("user1", "1234", 50000.00);
        user2 = new Account("user2", "4321", 20000.00);

        bank.addAccount(user1);
        bank.addAccount(user2);
    }


    @Test
    public void testThatFindAccountMethodReturnsTheSameAccountThatWasAdded() {
        assertSame(user1, bank.findAccount("user1"));
        assertSame(user2, bank.findAccount("user2"));
    }


    @Test
    public void testThatFindAccountMethodReturnsNullForUnknownId() {
        assertNull(bank.findAccount("ghost"));
    }


    @Test
    public void testThatAuthenticateMethodReturnsAccountForCorrectCredentials() {
        assertSame(user1, bank.authenticate("user1", "1234"));
    }


    @Test
    public void testThatAuthenticateMethodReturnsNullForWrongPin() {
        assertNull(bank.authenticate("user1", "9999"));
    }



    @Test
    public void testThatAuthenticateMethodReturnsNullForUnknownId() {
        assertNull(bank.authenticate("ghost", "1234"));
    }


    @Test
    public void testThatAddingDuplicateIdIsRejectedAndOriginalIsKept() {
        Account impostor = new Account("user1", "0000", 1.00);

        assertFalse(bank.addAccount(impostor));

        assertSame(user1, bank.findAccount("user1"));
        assertNull(bank.authenticate("user1", "0000"));
    }


    @Test
    public void testThatIdLookupIgnoresCaseAndSurroundingSpaces() {
        assertSame(user1, bank.findAccount("USER1"));
        assertSame(user1, bank.findAccount("  user1  "));
        assertSame(user1, bank.authenticate(" User1 ", "1234"));
    }


    @Test
    public void duplicateCheckAlsoIgnoresCase() {
        assertFalse(bank.addAccount(new Account("USER1", "0000", 1.00)));
    }


    @Test
    public void testThatNullInputsDoNotCrash() {
        assertNull(bank.findAccount(null));
        assertNull(bank.authenticate(null, "1234"));
        assertNull(bank.authenticate("user1", null));
        assertFalse(bank.addAccount(null));
    }


    @Test
    public void testThatBlankIdOrPinIsRejected() {
        assertNull(bank.findAccount(""));
        assertNull(bank.authenticate("", ""));
        assertNull(bank.authenticate("user1", ""));
    }


    @Test
    public void testThatAccountsAreIndependent() {
        bank.findAccount("user1").deposit(100.00);

        assertEquals(50100.00, user1.getBalance(), 0.001);
        assertEquals(20000.00, user2.getBalance(), 0.001);
    }
}