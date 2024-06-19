public class Program {
    public static void main(String[] args) {
        Menu menu = new Menu();
        if (args.length > 0) {
            if (args[0].equals("--profile=dev")) {
                menu.setDevMode(true);
            }
            else {
                System.err.println("Non-existent launch mode");
                System.exit(-1);
            }
        }
        menu.startMenu();
    }

    private static void printTransfersInfo(TransactionsService service, User mike, User john, User faye) {
        System.out.println("\nMike's transfers:");
        Transaction[] mikesTrans = service.getTransactions(mike);
        printArray(mikesTrans);

        System.out.println("John's transfers:");
        Transaction[] johnsTrans = service.getTransactions(john);
        printArray(johnsTrans);

        System.out.println("Faye's transfers:");
        Transaction[] fayeTrans = service.getTransactions(faye);
        printArray(fayeTrans);

        service.removeTransaction(fayeTrans[0].getId(), faye.getId());
        System.out.println("\nRemoving Faye's transaction:");
        mikesTrans = service.getTransactions(mike);
        System.out.println("Mike's transfers:");
        printArray(mikesTrans);
        fayeTrans = service.getTransactions(faye);
        System.out.println("Faye's transfers:");
        printArray(fayeTrans);

        Transaction[] unpairedTrans = service.getUnpairedTransactions();
        System.out.println("\nUnpaired transfers:");
        printArray(unpairedTrans);

        try {
            System.out.println("\nAn attempt to transfer more money than John has in his account:");
            service.makeTransaction(mike.getId(), john.getId(), 5555);
        } catch (IllegalTransactionException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void printBalanceInfo(TransactionsService service, User mike, User john, User faye) {
        System.out.println("User balances before transactions:");
        System.out.println("Mike: " + service.getBalance(mike) + ", John: "
                + service.getBalance(john) + ", Faye: " + service.getBalance(faye));

        service.makeTransaction(mike.getId(), john.getId(), 9990);
        service.makeTransaction(faye.getId(), mike.getId(), 750);

        System.out.println("User balances after transactions:");
        System.out.println("Mike: " + service.getBalance(mike) + ", John: "
                + service.getBalance(john) + ", Faye: " + service.getBalance(faye));
    }

    private static void printArray(Transaction[] mikesTrans) {
        for (Transaction transaction : mikesTrans) {
            transaction.printTransaction();
        }
    }
}
