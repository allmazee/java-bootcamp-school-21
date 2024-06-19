import java.util.UUID;

public class Program {
    public static void main(String[] args) {
        User mike = new User("Mike", 1000);
        User john = new User("John", 10500);
        Transaction transaction1 = new Transaction(mike, john, 500);
        Transaction transaction2 = new Transaction(john, mike, 150);
        
        transaction1.makeTransaction();
        transaction2.makeTransaction();

        System.out.println("John's transactions:");
        john.getTransactionsList().print();
        System.out.println("Mike's transactions:");
        mike.getTransactionsList().print();

        System.out.println();
        john.getTransactionsList().remove(transaction1.getId());
        System.out.println("John's transactions after deleting his first transaction:");
        john.getTransactionsList().print();
        System.out.println("Mike's transactions after deleting John's first transaction:");
        Transaction[] mikeArray = mike.getTransactionsList().toArray();
        for (Transaction transaction : mikeArray) {
            transaction.printTransaction();
        }
        try {
            UUID uuid = UUID.randomUUID();
            System.out.println("\nTry to delete Mike's non-existent transaction " + uuid + ":");
            mike.getTransactionsList().remove(uuid);
        } catch (TransactionNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void deleteNonExistent(TransactionsLinkedList transactions) {
        try {
            UUID uuid = UUID.randomUUID();
            System.out.println("Try to delete a non-existent transaction " + uuid);
            transactions.remove(uuid);
        }
        catch (TransactionNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public static User createUser(int i) {
        User user = new User();
        user.setName("User" + i);
        user.setBalance(1000 * i);
        return user;
    }
}
