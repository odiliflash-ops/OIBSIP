# ATM Interface (Java)

A console-based ATM simulation built in Java using object-oriented design. Users authenticate with a User ID and PIN, then perform standard banking operations.

**Track:** Java Development | **Task:** 3 | **Internship:** Oasis Infobyte (OIBSIP)

## Features

- Login with User ID and PIN, with access blocked after 3 failed attempts
- **Transaction History**: timestamped log of all transactions in the session
- **Withdraw**: validates amount and balance, shows "Insufficient Funds" when needed
- **Deposit**: adds funds and logs the transaction
- **Transfer**: moves money to another account, updating and logging both sides
- **Quit**: exits with a goodbye message
- Input validation for non-numeric, zero, and negative amounts, and for unknown recipient IDs

## Project Structure

```
Java-Task3-ATMInterface/
├── src/
│   ├── Main.java         # Entry point; seeds sample accounts
│   ├── ATM.java          # Menu loop and login logic
│   ├── Bank.java         # Stores accounts, authenticates users
│   ├── Account.java      # Balance, PIN check, deposit/withdraw/transfer
│   └── Transaction.java  # Immutable transaction record
├── screenshots/
└── README.md
```

## Class Overview

| Class | Responsibility |
|---|---|
| `Main` | Creates the bank, adds sample accounts, starts the ATM |
| `ATM` | Handles user interaction, the 3-attempt login, and the menu |
| `Bank` | Holds accounts in a `Map` and authenticates User ID and PIN |
| `Account` | Encapsulates balance and PIN; keeps an `ArrayList<Transaction>` history |
| `Transaction` | Records type, amount, and timestamp |

Design notes: fields are private with controlled access (encapsulation), and transaction history is stored in an `ArrayList`.

## How to Run

Requires JDK 17 or later.

```bash
cd Java-Task3-ATMInterface
javac -d out src/*.java
java -cp out Main
```

Or open the folder in IntelliJ IDEA and run `Main`.

## Sample Accounts

| User ID | PIN | Opening Balance |
|---|---|---|
| user1 | 1234 | 50,000 |
| user2 | 4321 | 20,000 |

## Sample Session

```
User ID: user1
PIN: 1234

1. Transaction History
2. Withdraw
3. Deposit
4. Transfer
5. Quit
Choose: 2
Enter amount: 5000
Withdrawal successful.
```

## Screenshots

See the `screenshots/` folder for: successful login, failed login lockout, withdraw, deposit, transfer, insufficient funds, and transaction history.

## Demo

LinkedIn demo video: <paste link here>

## Notes

Sample data lives in memory only, so balances reset each time the program starts. PINs are stored as plain text for simplicity, which is acceptable for this simulation but not for a real banking system.