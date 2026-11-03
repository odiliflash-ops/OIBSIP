import java.util.Scanner;

public class ATM {
    private final Bank bank;
    private final Scanner in = new Scanner(System.in);

    public ATM(Bank bank) {
        this.bank = bank;
    }

    public void start() {
        Account account = login();
        if (account == null) {
            System.out.println("Too many failed attempts. Card blocked.");
            return;
        }
        menu(account);
    }

    private Account login() {
        for (int attempt = 1; attempt <= 3; attempt++) {
            System.out.println("Please enter your login details to continue: ");
            System.out.print("User ID: ");
            String id = in.nextLine().trim();
            System.out.print("PIN: ");
            String pin = in.nextLine().trim();

            Account a = bank.authenticate(id, pin);
            if (a != null) return a;
            System.out.println("Invalid credentials. Attempts left: " + (3 - attempt));
        }
        return null;
    }

    private void menu(Account account) {
        while (true) {
            System.out.println("\n1. Transaction History\n2. Withdraw\n3. Deposit\n4. Transfer\n5. Quit");
            System.out.print("Choose: ");
            switch (in.nextLine().trim()) {
                case "1" -> showHistory(account);
                case "2" -> withdraw(account);
                case "3" -> deposit(account);
                case "4" -> transfer(account);
                case "5" -> {
                    System.out.println("Thank you for banking with us. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void showHistory(Account acc) {
        if (acc.getHistory().isEmpty()) {
            System.out.println("No transactions yet.");
            return;
        }
        acc.getHistory().forEach(System.out::println);
    }

    private void withdraw(Account acc) {
        double amount = readAmount();
        if (acc.withdraw(amount)) {
            System.out.printf("Withdrawal successful. Balance: %.2f%n", acc.getBalance());
        } else {
            System.out.println("Insufficient Funds.");
        }
    }

    private void deposit(Account acc) {
        double amount = readAmount();
        acc.deposit(amount);
        System.out.printf("Deposit successful. Balance: %.2f%n", acc.getBalance());
    }

    private void transfer(Account acc) {
        System.out.print("Recipient User ID: ");
        Account recipient = bank.findAccount(in.nextLine());

        if (recipient == null) {
            System.out.println("Recipient not found.");
            return;
        }
        if (recipient == acc) {
            System.out.println("You cannot transfer to your own account.");
            return;
        }

        double amount = readAmount();
        if (acc.sendTo(recipient, amount)) {
            System.out.printf("Transfer successful. Balance: %.2f%n", acc.getBalance());
        } else {
            System.out.println("Insufficient Funds.");
        }
    }


    private double readAmount() {
        while (true) {
            System.out.print("Enter amount: ");
            try {
                double amount = Double.parseDouble(in.nextLine().trim());
                if (amount > 0) return amount;
                System.out.println("Amount must be greater than 0.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}