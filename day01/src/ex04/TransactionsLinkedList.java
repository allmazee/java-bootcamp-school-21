import java.util.UUID;

public class TransactionsLinkedList implements TransactionsList {
    private Node first;
    private Node last;
    private int size = 0;

    TransactionsLinkedList() {
    }

    public int getSize() {
        return size;
    }

    @Override
    public void add(Transaction transaction) {
        Node newNode = new Node(transaction);
        if (first == null) {
            newNode.next = null;
            newNode.previous = null;
            first = newNode;
        } else {
            last.next = newNode;
            newNode.previous = last;
        }
        last = newNode;
        size++;
    }

    @Override
    public void remove(UUID uuid) {
        Node node = first;
        for (int i = 0; i < size; i++) {
            if (node.transaction.getId().equals(uuid)) {
                if (node.previous == null && node.next == null) { // node = first and last
                    first = null;
                    last = null;
                } else if (node.previous == null && node.next != null) { // node = first
                    node.next.setPrevious(null);
                    first = node.next;
                } else if (node.previous != null && node.next == null) { // node = last
                    node.previous.setNext(null);
                    last = node.previous;
                } else {
                    node.previous.setNext(node.next);
                    node.next.setPrevious(node.previous);
                }
                break;
            } else if (i == size - 1) {
                throw new TransactionNotFoundException("UserNotFoundException: There is no transaction with uuid = " + uuid);
            }
            node = node.getNext();
        }
        size--;
    }

    @Override
    public Transaction[] toArray() {
        Transaction[] transactionsArray = new Transaction[size];
        Node node = first;
        for (int i = 0; i < size; i++) {
            transactionsArray[i] = node.getTransaction();
            node = node.getNext();
        }
        return transactionsArray;
    }

    @Override
    public void print() {
        Transaction[] array = this.toArray();
        for (Transaction transaction : array) {
            if (transaction.getCategory() == TransferCategory.DEBITS) {
                System.out.println(transaction.getSender().getName() + " -> " + transaction.getRecipient().getName() + ", "
                        + transaction.getAmount() + ", " + transaction.getCategory() + ", " + "transaction id: " + transaction.getId());
            } else {
                System.out.println(transaction.getRecipient().getName() + " -> " + transaction.getSender().getName() + ", "
                        + (-transaction.getAmount()) + ", " + transaction.getCategory() + ", " + "transaction id: " + transaction.getId());
            }
        }
    }

    public class Node {
        private Transaction transaction;
        private Node next;
        private Node previous;

        Node(Transaction transaction) {
            this.transaction = transaction;
        }

        public Transaction getTransaction() {
            return transaction;
        }

        public void setTransaction(Transaction transaction) {
            this.transaction = transaction;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public Node getPrevious() {
            return previous;
        }

        public void setPrevious(Node previous) {
            this.previous = previous;
        }
    }
}
