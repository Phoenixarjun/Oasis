import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Bank {
    private Map<String, Account> accounts;

    public Bank() {
        this.accounts = new HashMap<>();
    }

    public void addAccount(String userId, String pin, double balance) {
        accounts.put(userId, new Account(userId, pin, balance));
    }

    public Account getAccount(String userId) {
        return accounts.get(userId);
    }
}

class Account {
    private String userId;
    private String pin;
    private double balance;
    private StringBuilder transactionHistory;
    
    public String getPin() {
      return pin;
  }
    public Account(String userId, String pin, double balance) {
        this.userId = userId;
        this.pin = pin;
        this.balance = balance;
        this.transactionHistory = new StringBuilder();
    }

    public String getUserId() {
        return userId;
    }

    public double getBalance() {
        return balance;
    }

    public String getTransactionHistory() {
        return transactionHistory.toString();
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.append("Deposit: +").append(amount).append("\n");
    }

    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            transactionHistory.append("Withdrawal: -").append(amount).append("\n");
        } else {
            System.out.println("Insufficient funds");
        }
    }

    public void transfer(Account receiver, double amount) {
        if (balance >= amount) {
            balance -= amount;
            receiver.deposit(amount);
            transactionHistory.append("Transfer to ").append(receiver.getUserId())
                    .append(": -").append(amount).append("\n");
        } else {
            System.out.println("Insufficient funds");
        }
    }
}

class ATM {
    private Bank bank;
    private Account currentUser;

    public ATM(Bank bank) {
        this.bank = bank;
        this.currentUser = null;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            if (currentUser == null) {
                System.out.print("Enter User ID: ");
                String userId = scanner.next();
                System.out.print("Enter PIN: ");
                String pin = scanner.next();

                if (bank.getAccount(userId) != null && bank.getAccount(userId).getPin().equals(pin)) {
                    currentUser = bank.getAccount(userId);
                    System.out.println("Login successful!");
                } else {
                    System.out.println("Invalid User ID or PIN. Please try again.");
                }
            } else {
                displayMenu();
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        System.out.println("Transaction History:\n" + currentUser.getTransactionHistory());
                        break;
                    case 2:
                        System.out.print("Enter amount to deposit: ");
                        double depositAmount = scanner.nextDouble();
                        currentUser.deposit(depositAmount);
                        System.out.println("Deposit successful!");
                        break;
                    case 3:
                        System.out.print("Enter amount to withdraw: ");
                        double withdrawAmount = scanner.nextDouble();
                        currentUser.withdraw(withdrawAmount);
                        break;
                    case 4:
                        System.out.print("Enter recipient's User ID: ");
                        String recipientId = scanner.next();
                        Account recipient = bank.getAccount(recipientId);
                        if (recipient != null) {
                            System.out.print("Enter amount to transfer: ");
                            double transferAmount = scanner.nextDouble();
                            currentUser.transfer(recipient, transferAmount);
                            System.out.println("Transfer successful!");
                        } else {
                            System.out.println("Recipient not found.");
                        }
                        break;
                    case 5:
                        System.out.println("Quitting ATM. Goodbye!");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }

    private void displayMenu() {
        System.out.println("\nATM Menu:");
        System.out.println("1. Transaction History");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Transfer");
        System.out.println("5. Quit");
        System.out.print("Choose an option: ");
    }
}

public class ATMInterface {
    public static void main(String[] args) {
        Bank bank = new Bank();
        bank.addAccount("user1", "1234", 1000.0);
        bank.addAccount("user2", "5678", 500.0);

        ATM atm = new ATM(bank);
        atm.start();
    }
}
