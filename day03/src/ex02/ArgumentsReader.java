public class ArgumentsReader {
    private String[] args;
    private int arraySize;
    private int threadsCount;

    ArgumentsReader(String[] args) {
        this.args = args;
    }

    public int getArraySize() {
        return arraySize;
    }

    public int getThreadsCount() {
        return threadsCount;
    }

    public void read() {
        if (args.length != 2 || !args[0].startsWith("--arraySize=")
                || !args[1].startsWith("--threadsCount=")) {
            System.out.println("Invalid arguments");
            System.exit(-1);
        }
        String[] arrayArg = args[0].split("--arraySize=");
        String[] threadArg = args[1].split("--threadsCount=");
        if (arrayArg.length != 2 || threadArg.length != 2) {
            System.out.println("Invalid arguments");
            System.exit(-1);
        }
        int arraySize = 0;
        int threadsCount = 0;
        try {
            arraySize = Integer.parseInt(arrayArg[1]);
            threadsCount = Integer.parseInt(threadArg[1]);
        } catch (NumberFormatException e) {
            System.out.println("NumberFormatException: " + e.getMessage());
            System.exit(-1);
        }
        this.arraySize = arraySize;
        this.threadsCount = threadsCount;
    }

}
