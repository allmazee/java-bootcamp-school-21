public class Program {
    public static void main(String[] args) throws InterruptedException {
        int count = getCount(args);
        DisputeThread hen = new DisputeThread("Hen", count);
        DisputeThread egg = new DisputeThread("Egg", count);
        hen.start();
        egg.start();
        hen.join();
        egg.join();
        for (int i = 0; i < count; i++) {
            System.out.println("Human");
        }
    }

    public static int getCount(String[] args) {
        if (args.length != 1 || !args[0].startsWith("--count=")) {
            System.out.println("Invalid arguments");
            System.exit(-1);
        }
        String[] tokens = args[0].split("--count=");
        if (tokens.length != 2) {
            System.out.println("Invalid arguments");
            System.exit(-1);
        }
        int count = 0;
        try {
            count = Integer.parseInt(tokens[1]);
        }
        catch (NumberFormatException e) {
            System.out.println("NumberFormatException: " + e.getMessage());
            System.exit(-1);
        }
        return count;
    }
}
