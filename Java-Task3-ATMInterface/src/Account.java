import java.util.ArrayList;
import java.util.List;

public class Account {
    private final String userId;
    private final String pin;
    private double balance;
    private final List<Transaction> history = new ArrayList<>();

    public Account(String userId, String pin, double openingBalance) {
        this.userId = userId;
        this.pin = pin;
        this.balance = openingBalance;
    }


    public boolean checkPin(String input) {
        return pin.equals(input);
    }

    public String getUserId() {
        return userId;
    }

    public double getBalance() {
        return balance;
    }

    public List<Transaction> getHistory() {
        return history;
    }

    public boolean withdraw(double amount) {
        if (amount <= 0 || amount > balance) return false;
        balance -= amount;
        history.add(new Transaction("WITHDRAW", amount));
        return true;
    }

    public boolean deposit(double amount) {
        if (amount <= 0) {
            return false;
        }
        balance += amount;
        history.add(new Transaction("DEPOSIT", amount));
        return true;
    }


    
    public void receive(double amount, String from) {
        balance += amount;
        history.add(new Transaction("TRANSFER IN (" + from + ")", amount));
    }


    public boolean sendTo(Account to, double amount) {
        if (to == null || to == this) return false;
        if (amount <= 0 || amount > balance) return false;

        balance -= amount;
        history.add(new Transaction("TRANSFER OUT (" + to.getUserId() + ")", amount));
        to.receive(amount, userId);
        return true;


    }
}