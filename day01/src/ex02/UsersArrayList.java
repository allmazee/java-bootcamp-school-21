public class UsersArrayList implements UsersList{
    private final int DEFAULT_CAPACITY = 10;
    private int size = 0;
    private User[] users = new User[DEFAULT_CAPACITY];

    public UsersArrayList() {
        users = new User[DEFAULT_CAPACITY];
    }

    @Override
    public void addUser(User user) {
        if (users.length <= size) {
            increaseCapacity();
        }
        users[size] = user;
        size++;
    }

    @Override
    public User getUserById(int id) {
        for (int j = 0; j < size; j++){
            if (users[j].getId() == id){
                return users[j];
            }
        }
        throw new UserNotFoundException("UserNotFoundException: There is no user with id = " + id);
    }

    @Override
    public User getUserByIndex(int index) {
        if (index >= size || index < 0) {
            throw new UserNotFoundException("UserNotFoundException: There is no user with index = " + index);
        }
        return users[index];
    }

    @Override
    public int getNumberOfUsers() {
        return size;
    }


    public String toString() {
        for (int i = 0; i < size; i++) {
            System.out.println(users[i]);
        }
        return "------------------------------------------";
    }

    private void increaseCapacity() {
        int newCapacity = users.length * 2;
        User[] newUsers = new User[newCapacity];
        for (int i = 0; i < users.length; i++) {
            newUsers[i] = users[i];
        }
        users = newUsers;
    }
}
