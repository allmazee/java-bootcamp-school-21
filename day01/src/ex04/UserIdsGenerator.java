public class UserIdsGenerator {
    private static UserIdsGenerator instance;
    private Integer id = -1;

    private UserIdsGenerator(){}

    public static UserIdsGenerator getInstance() {
        if (instance == null) {
            instance = new UserIdsGenerator();
        }
        return instance;
    }

    public Integer getId() {
        return id;
    }

    public int generateId() {
        id++;
        return id;
    }
}
