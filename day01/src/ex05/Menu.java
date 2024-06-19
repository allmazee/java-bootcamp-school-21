import java.util.*;
import java.util.Scanner;
import java.util.UUID;

public class Menu {
    private TransactionsService service;
    private String mode;
    private Scanner scanner;
    private boolean isDevMode;

    Menu() {
        service = new TransactionsService();
        scanner = new Scanner(System.in);
    }

    public boolean getDevMode() {
        return isDevMode;
    }

    public void setDevMode(boolean devMode) {
        isDevMode = devMode;
    }

    public void startMenu() {
        int intInput = 0;
        String strInput = "";
        while (intInput != 7) {
            printInstructions();
            try {
                intInput = scanner.nextInt();
            } catch (InputMismatchException e) {
            }
            scanner.nextLine();
            handleCommand(intInput);
        }
    }

    private void printInstructions() {
        System.out.print("1. Add a user\n" +
                "2. View user balances\n" +
                "3. Perform a transfer\n" +
                "4. View all transactions for a specific user\n" +
                "5. DEV – remove a transfer by ID\n" +
                "6. DEV – check transfer validity\n" +
                "7. Finish execution\n->");
    }

    private void handleCommand(int command) {
        switch (command) {
            case 1:
                addUser();
                break;
            case 2:
                viewBalance();
                break;
            case 3:
                makeTransfer();
                break;
            case 4:
                viewTransactions();
                break;
            case 5: // dev mode
                removeTransfer();
                break;
            case 6: // dev mode
                checkTransferValidity();
                break;
            case 7:
                System.out.println("Execution finished");
                System.exit(0);
            default:
                System.out.println("Choose command from 1 to 7");
                break;
        }
    }

    private void addUser() {
        System.out.println("Enter a user name and a balance");
        String name = scanner.next();
        int balance;
        try {
            balance = scanner.nextInt();
        } catch (InputMismatchException e) {
            return;
        }
        scanner.nextLine();
        if (balance < 0) {
            System.out.println("Balance can not be negative");
            return;
        }
        User user = new User(name, balance);
        service.addUser(user);
        System.out.println("User with id = " + user.getId() + " is added");
    }

    private void viewBalance() {
        System.out.println("Enter a user ID");
        try {
            int userId = scanner.nextInt();
            scanner.nextLine();
            int balance = service.getBalance(userId);
            String name = service.getUsersList().getUserById(userId).getName();
            System.out.println(name + " - " + balance);
        } catch (UserNotFoundException | InputMismatchException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void makeTransfer() {
        System.out.println("Enter a sender ID, a recipient ID, and a transfer amount");
        int sender = scanner.nextInt();
        int recipient = scanner.nextInt();
        int amount = Math.abs(scanner.nextInt());
        scanner.nextLine();
        try {
            service.makeTransaction(recipient, sender, amount);
            System.out.println("The transfer is completed");
        } catch (IllegalTransactionException | UserNotFoundException exception) {
            System.out.println(exception.getMessage());
        }

    }

    private void viewTransactions() {
        System.out.println("Enter a user ID");
        int id;
        try {
            id = scanner.nextInt();
        } catch (UserNotFoundException | InputMismatchException exception) {
            System.out.println(exception.getMessage());
            return;
        }
        scanner.nextLine();
        Transaction[] transactions = service.getTransactions(id);
        for (Transaction transaction : transactions) {
            transaction.printTransaction();
        }
    }

    private void removeTransfer() {
        if (!isDevMode) {
            System.out.println("Only available in dev mode");
            return;
        }
        System.out.println("Enter a user ID and a transfer ID");
        int id;
        UUID uuid;
        try {
            id = scanner.nextInt();
            uuid = UUID.fromString(scanner.next());
        } catch (IllegalArgumentException | InputMismatchException exception) {
            System.out.println(exception.getMessage());
            return;
        }
        scanner.nextLine();
        User partner;
        Transaction transaction;
        try {
            partner = service.getPartner(id, uuid);
            transaction = service.getTransaction(id, uuid);
        } catch (UserNotFoundException | TransactionNotFoundException exception) {
            System.out.println(exception.getMessage());
            return;
        }
        if (transaction.getCategory() == TransferCategory.DEBITS) {
            System.out.println("Transfer from " + partner.getName() + "(id = " + partner.getId() + ") " + transaction.getAmount() + " removed");
        } else {
            System.out.println("Transfer to " + partner.getName() + "(id = " + partner.getId() + ") " + transaction.getAmount() + " removed");
        }
        service.removeTransaction(uuid, id);
    }

    private void checkTransferValidity() {
        if (!isDevMode) {
            System.out.println("Only available in dev mode");
            return;
        }
        Transaction[] unpairedTransactions = service.getUnpairedTransactions();
        for (Transaction transaction : unpairedTransactions) {
            printUnpairedTransaction(transaction);
        }
    }

    private void printUnpairedTransaction(Transaction transaction) {
        User user;
        User partner;
        String transactionStr;
        if (transaction.getCategory() == TransferCategory.DEBITS) {
            user = transaction.getSender();
            partner = transaction.getRecipient();
            transactionStr = " from ";
        } else {
            user = transaction.getRecipient();
            partner = transaction.getSender();
            transactionStr = " to ";
        }
        System.out.println(user.getName() + "(id = " + user.getId()
                + ") has an unacknowledged transfer id = " + transaction.getId()
                + transactionStr + partner.getName() + "(id = " + partner.getId()
                + ") for " + transaction.getAmount());

    }
}
