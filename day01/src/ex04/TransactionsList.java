import java.util.UUID;

public interface TransactionsList {
    public void add(Transaction transaction);
    public void remove(UUID uuid);
    public Transaction[] toArray();
    public void print();
}
