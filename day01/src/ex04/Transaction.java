import java.util.UUID;

enum TransferCategory {
    DEBITS("INCOME"),
    CREDITS("OUTCOME");

    private final String title;

    TransferCategory(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
    @Override
    public String toString() {
        return title;
    }
}

public class Transaction {
    private UUID id;
    private User recipient;
    private User sender;
    private TransferCategory category;
    private Integer amount;

    public Transaction(User recipient, User sender, Integer amount) {
        this.id = UUID.randomUUID();
        this.recipient = recipient;
        this.sender = sender;
        this.amount = amount;
        if (sender.getBalance() < 0 || recipient.getBalance() < 0) {
            handleError();
        }
    }

    public UUID getId() {
        return id;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public TransferCategory getCategory() {
        return category;
    }

    public void setCategory(TransferCategory category) {
        this.category = category;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void makeTransaction() {
        if (sender.getBalance() < Math.abs(amount)) {
            throw new IllegalTransactionException("IllegalTransactionException: User does not have enough funds");
        }

        sender.setBalance(sender.getBalance() - Math.abs(amount));
        recipient.setBalance(recipient.getBalance() + Math.abs(amount));

        Transaction t1 = new Transaction(sender, recipient, amount);
        t1.id = this.id;
        t1.category = TransferCategory.CREDITS;
        sender.getTransactionsList().add(t1);

        Transaction t2 = new Transaction(sender, recipient, amount);
        t2.id = t1.id;
        t2.category = TransferCategory.DEBITS;
        recipient.getTransactionsList().add(t2);
    }

    public void printTransaction() {
        if (getCategory() == TransferCategory.DEBITS) {
            System.out.println(getSender().getName() + " -> " + getRecipient().getName() + ", "
                    + getAmount() + ", " + getCategory() + ", " + "transaction id: " + getId());
        } else if (getCategory() == TransferCategory.CREDITS) {
            System.out.println(getRecipient().getName() + " -> " + getSender().getName() + ", "
                    + (-getAmount()) + ", " + getCategory() + ", " + "transaction id: " + getId());
        }
    }

    private void handleError() {
        System.err.println("Illegal argument");
        System.exit(-1);
    }
}


