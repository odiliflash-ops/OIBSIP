import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {


    @Test
    public void testThatTransactionStoresTypeAndAmount() {
        Transaction transaction = new Transaction("DEPOSIT", 500.00);

        assertEquals("DEPOSIT", transaction.getType());
        assertEquals(500.00, transaction.getAmount(), 0.001);
    }


    @Test
    public void testThatTimestampIsSetAtCreation() {
        LocalDateTime before = LocalDateTime.now();
        Transaction transaction = new Transaction("WITHDRAW", 100.00);
        LocalDateTime after = LocalDateTime.now();

        assertNotNull(transaction.getTimestamp());
        assertFalse(transaction.getTimestamp().isBefore(before));
        assertFalse(transaction.getTimestamp().isAfter(after));
    }


    @Test
    public void testThatToStringMethodContainsType_Amount_AndTimestamp() {
        Transaction transaction = new Transaction("DEPOSIT", 500.00);
        String text = transaction.toString();

        assertTrue(text.contains("DEPOSIT"));
        assertTrue(text.contains("500.00"));
        assertTrue(text.contains(String.valueOf(transaction.getTimestamp().getYear())));
    }
}
