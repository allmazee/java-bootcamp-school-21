import java.util.UUID;

public class TransactionsService {
    private UsersList usersList = new UsersArrayList();

    public void addUser(User user) {
        usersList.addUser(user);
    }

    public int getBalance(User user) {
        return user.getBalance();
    }

    public void makeTransaction(int recipientId, int senderId, int amount) {
        User recipient = usersList.getUserById(recipientId);
        User sender = usersList.getUserById(senderId);
        Transaction transaction = new Transaction(recipient, sender, amount);
        transaction.makeTransaction();
    }

    public Transaction[] getTransactions(User user) {
        return user.getTransactionsList().toArray();
    }

    public void removeTransaction(UUID uuid, int userId) {
        User user = usersList.getUserById(userId);
        user.getTransactionsList().remove(uuid);
    }

    public Transaction[] getUnpairedTransactions() {
        TransactionsLinkedList unpairedTransactions = new TransactionsLinkedList();
        for (int i = 0; i < usersList.getNumberOfUsers(); i++) {
            User user = usersList.getUserByIndex(i);
            Transaction[] transactionsArray = user.getTransactionsList().toArray();
            for (Transaction transaction : transactionsArray) {
                UUID transactionId = transaction.getId();
                boolean hasPair = false;
                User partner;
                if (transaction.getCategory() == TransferCategory.CREDITS) {
                    partner = transaction.getSender();
                } else {
                    partner = transaction.getRecipient();
                }
                Transaction[] partnerTransactions = partner.getTransactionsList().toArray();
                for (Transaction partnerTransaction : partnerTransactions) {
                    UUID partnerTransactionId = partnerTransaction.getId();
                    if (partnerTransactionId.equals(transactionId)) {
                        hasPair = true;
                        break;
                    }
                }
                if (!hasPair) {
                    unpairedTransactions.add(transaction);
                }
            }
        }
        return unpairedTransactions.toArray();
    }
}
