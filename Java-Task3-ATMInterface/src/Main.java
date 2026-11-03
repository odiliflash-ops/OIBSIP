public class Main {
    public static void main(String[] args) {

        Bank bank = new Bank();

        bank.addAccount(new Account("user1", "1234", 50000));
        bank.addAccount(new Account("user2", "4321", 20000));

        new ATM(bank).start();
    }
}