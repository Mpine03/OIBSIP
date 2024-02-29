import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class User {
    private String userId;
    private String pin;
    private double balance;
    private StringBuilder transactionHistory;

    public User(String userId, String pin, double balance) {
        this.userId = userId;
        this.pin = pin;
        this.balance = balance;
        this.transactionHistory = new StringBuilder("Transaction History:\n");
    }

    public String getUserId() {
        return userId;
    }

    public String getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public String getTransactionHistory() {
        return transactionHistory.toString();
    }

    public void deposit(double amount) {
        balance += amount;
        recordTransaction("Deposit", amount);
        System.out.println("Successfully deposited money!");
    }

    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            recordTransaction("Withdrawal", amount);
            System.out.println("Successfully withdrew money!");
        } else {
            System.out.println("Insufficient funds");
        }
    }

    public void transfer(User recipient, double amount) {
        if (amount <= balance) {
            balance -= amount;
            recipient.deposit(amount);
            recordTransaction("Transfer to " + recipient.getUserId(), amount);
            System.out.println("Successfully transferred money!");
        } else {
            System.out.println("Insufficient funds");
        }
    }

    public void checkBalance() {
        System.out.println("Current Balance: R" + balance);
    }

    private void recordTransaction(String transactionType, double amount) {
        transactionHistory.append(transactionType)
                .append(": R")
                .append(amount)
                .append(", Current Balance: R")
                .append(balance)
                .append("\n");
    }
}

public class App {
    private Map<String, User> users;

    public App() {
        users = new HashMap<>();
        // Initialize users with sample data
        users.put("123456", new User("123456", "1234", 1000));
        users.put("246810", new User("246810", "2468", 1500));
        users.put("369121", new User("369121", "3691", 500));
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Nthabeleng's ATM!");
        System.out.println("*******************************");
        System.out.print("Enter User ID: ");
        String userId = scanner.next();
        System.out.print("Enter PIN: ");
        String pin = scanner.next();

        if (authenticateUser(userId, pin)) {
            User currentUser = users.get(userId);
            System.out.println("Welcome, " + currentUser.getUserId() + "!");

            int choice;

            do {
                System.out.println("What operation would you like to perform? Choose (1-6): ");
                System.out.println("-----------------------------------------------------------");
                displayMenu();
                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        currentUser.checkBalance();
                        break;
                    case 2:
                        System.out.print("Enter amount to withdraw: R");
                        double withdrawAmount = scanner.nextDouble();
                        currentUser.withdraw(withdrawAmount);
                        break;
                    case 3:
                        System.out.print("Enter amount to deposit: R");
                        double depositAmount = scanner.nextDouble();
                        currentUser.deposit(depositAmount);
                        break;
                    case 4:
                        System.out.print("Enter recipient's User ID: ");
                        String recipientUserId = scanner.next();
                        User recipient = users.get(recipientUserId);
                        if (recipient != null) {
                            System.out.print("Enter amount to transfer: R");
                            double transferAmount = scanner.nextDouble();
                            currentUser.transfer(recipient, transferAmount);
                        } else {
                            System.out.println("Recipient not found.");
                        }
                        break;
                    case 5:
                        System.out.println(currentUser.getTransactionHistory());
                        break;
                    case 6:
                        System.out.println("Thank you for using Nthabeleng's ATM. Logging out...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 6.");
                }

            } while (choice != 6);

        } else {
            System.out.println("Authentication failed. Exiting...");
        }

        scanner.close();
    }

    private boolean authenticateUser(String userId, String pin) {
        User user = users.get(userId);
        return user != null && user.getPin().equals(pin);
    }

    private void displayMenu() {
        System.out.println("\n1. Check Balance");
        System.out.println("2. Withdraw");
        System.out.println("3. Deposit");
        System.out.println("4. Transfer");
        System.out.println("5. View Transaction History");
        System.out.println("6. Quit");
    }

    public static void main(String[] args) {
        App atm = new App();
        atm.run();
    }
}
