public class ArgumentsReader {
    private final String[] args;
    private int threadsCount;

    ArgumentsReader(String[] args) {
        this.args = args;
    }

    public int getThreadsCount() {
        return threadsCount;
    }

    public void read() {
        if (args.length != 1 || !args[0].startsWith("--threadsCount=")) {
            System.out.println("Invalid arguments");
            System.exit(-1);
        }
        String[] threadArg = args[0].split("--threadsCount=");
        if (threadArg.length != 2) {
            System.out.println("Invalid arguments");
            System.exit(-1);
        }
        int threadsCount = 0;
        try {
            threadsCount = Integer.parseInt(threadArg[1]);
        } catch (NumberFormatException e) {
            System.out.println("NumberFormatException: " + e.getMessage());
            System.exit(-1);
        }
        this.threadsCount = threadsCount;
    }

}
