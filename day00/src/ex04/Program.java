import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char[] input = scanner.next().toCharArray();
        int[] frequencies = countFrequencies(input);
        int[] indexes = getTopIndexes(frequencies);
        printHistogram(frequencies, indexes);
        printSymbols(indexes);
        scanner.close();
    }

    static int[] countFrequencies(char[] input) {
        int[] frequencies = new int[65536];
        for (char c : input) {
            frequencies[c]++;
        }
        return frequencies;
    }

    static int[] getTopIndexes(int[] frequencies) {
        int[] tempFrequencies = new int[frequencies.length];
        for (int i = 0; i < frequencies.length; i++) {
            tempFrequencies[i] = frequencies[i];
        }
        int[] indexes = new int[10];
        for (int i = 0; i < 10; i++) {
            int maxValue = 0;
            int maxIndex = 0;
            for (int j = 0; j < tempFrequencies.length; j++) {
                if (tempFrequencies[j] > maxValue) {
                    maxValue = tempFrequencies[j];
                    maxIndex = j;
                }
            }
            indexes[i] = maxIndex;
            tempFrequencies[maxIndex] = 0;
        }
        return indexes;
    }

    static void printHistogram(int[] frequencies, int[] indexes) {
        double coefficient = (double) frequencies[indexes[0]] / 10;
        int scale = 9;
        System.out.print(" " + frequencies[indexes[0]]);
        for (int i = 1; i < 10; i++) {
            int frequency = frequencies[indexes[i]];
            int height = (int) (frequency / coefficient);
            int prevHeight = (int) (frequencies[indexes[i - 1]] / coefficient);
            if (height >= scale) {
                if (height == prevHeight) {
                    if (frequency < 10) System.out.print(" ");
                    System.out.print("  " + frequency);
                    scale--;
                    continue;
                }
                System.out.println();
                System.out.print(" ");
                for (int j = 0; j < i; j++) {
                    System.out.print(" #  ");
                }
            } else {
                while (height < scale) {
                    System.out.println();
                    System.out.print(" ");
                    for (int j = 0; j < i; j++) {
                        System.out.print(" #  ");
                    }
                    scale--;
                }
                System.out.println();
                System.out.print(" ");
                for (int j = 0; j < i; j++) {
                    System.out.print(" #  ");
                }
            }
            if (frequency < 10) System.out.print(" ");
            System.out.print(frequency);
            scale--;
        }
    }

    static void printSymbols(int[] indexes) {
        System.out.println();
        for (int i = 0; i < 10; i++) {
            System.out.print("  " + (char) indexes[i] + " ");
        }
        System.out.println();
    }
}

