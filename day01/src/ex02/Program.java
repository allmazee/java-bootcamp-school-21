public class Program {
    public static void main(String[] args) {
        UsersArrayList users = new UsersArrayList();
        for (int i = 0; i < 15; i++) {
            users.addUser(createUser(i));
        }
        System.out.println(users);
        System.out.println("Number of users: " + users.getNumberOfUsers());
        System.out.println("Get right user with id = 10: " + users.getUserById(10));
        System.out.println("Get right user with index = 5: " + users.getUserByIndex(5));
        try {
            System.out.println("Get non-existent user with id = 15:");
            System.out.println(users.getUserById(15));
        } catch (UserNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
    }
    public static User createUser(int i) {
        User user = new User();
        user.setName("User" + i);
        user.setBalance(10 * i);
        return user;
    }
}
