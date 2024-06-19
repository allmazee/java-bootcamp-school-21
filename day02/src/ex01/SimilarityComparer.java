import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class SimilarityComparer {
    private String fileName1;
    private String fileName2;
    private Set<String> dictionary;
    private List<String> wordsFile1;
    private List<String> wordsFile2;
    private List<Integer> frequency1;
    private List<Integer> frequency2;

    SimilarityComparer(String fileName1, String fileName2) {
        dictionary = new TreeSet<>();
        wordsFile1 = new ArrayList<>();
        wordsFile2 = new ArrayList<>();
        frequency1 = new ArrayList<>();
        frequency2 = new ArrayList<>();
        this.fileName1 = fileName1;
        this.fileName2 = fileName2; 
    }

    public double getSimilarity() {
        fillDictionary();
        fillFrequencies();
        double numerator = 0;
        double normA = 0;
        double normB = 0;
        for (int index = 0; index < frequency1.size(); index++) {
            numerator += frequency1.get(index) * frequency2.get(index);
            normA += Math.pow(frequency1.get(index), 2);
            normB += Math.pow(frequency2.get(index), 2);
        }
        double denominator = Math.sqrt(normA) * Math.sqrt(normB);
        return numerator / denominator;
    }

    private void fillDictionary() {
        readFile(fileName1, wordsFile1);
        readFile(fileName2, wordsFile2);
        saveDictionary();
    }

    private void fillFrequencies() {
        countFrequency(wordsFile1, frequency1);
        countFrequency(wordsFile2, frequency2);
    }

    private void countFrequency(List<String> wordsFile, List<Integer> frequency) {
        for(String dictionaryWord : dictionary) {
            int count = 0;
            for(String word : wordsFile) {
                if (dictionaryWord.equals(word)) {
                    count++;
                }
            }
            frequency.add(count);
        }
    }

    private void readFile(String fileName, List<String> wordsFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                addWord(line, wordsFile);
            }
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
            System.exit(-1);
        }
    }

    private void addWord(String line, List<String> wordsFile) {
        String[] words = line.split(" ");
        for(String word : words) {
            wordsFile.add(word);
            dictionary.add(word);
        }
    } 

    private void saveDictionary() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("dictionary.txt"))) {
            for(String word : dictionary) {
                writer.write(word + " ");
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}