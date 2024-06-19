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
    private final UUID id;
    User recipient;
    User sender;
    TransferCategory category;
    Integer amount;

    public Transaction(User recipient, User sender, Integer amount) {
        this.id = UUID.randomUUID();
        this.recipient = recipient;
        this.sender = sender;
        this.amount = amount;
        this.category = amount < 0 ? TransferCategory.CREDITS : TransferCategory.DEBITS;

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
                handleError();
            }
            sender.setBalance(sender.getBalance() - Math.abs(amount));
            recipient.setBalance(recipient.getBalance() + Math.abs(amount));
            System.out.println("Transaction info:");
            System.out.println(sender.getName() + " -> " + recipient.getName() + ", "
                    + amount + ", " + category + ", " + "transaction id: " + id);
    }

    private void handleError() {
        System.err.println("Illegal argument");
        System.exit(-1);
    }
}


