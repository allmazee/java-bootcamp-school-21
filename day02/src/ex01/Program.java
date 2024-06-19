public class Program {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Illegal input");
            System.exit(-1);
        }
        String file1 = args[0];
        String file2 = args[1];
        SimilarityComparer comparer = new SimilarityComparer(file1, file2);
        System.out.printf("Similarity = %.2f\n", comparer.getSimilarity());
    }
}