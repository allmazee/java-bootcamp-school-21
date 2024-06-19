public class Program {
    public static void main(String[] args) {
        User user1 = new User(0, "Mike", 100);
        User user2 = new User(1, "Yui", 25);
        System.out.println("Before transaction:");
        System.out.println(user1);
        System.out.println(user2);
        Transaction transaction = new Transaction(user2, user1, 90);
        transaction.makeTransaction();
        System.out.println("After transaction:");
        System.out.println(user1);
        System.out.println(user2);
    }
}
