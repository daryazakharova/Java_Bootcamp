package ex01;
import java.io.*;
import java.util.*;

public class Words {
    private final String file1;
    private final String file2;
    private final Set<String> dictionary;
    private final Map<String, Integer> freq1;
    private final Map<String, Integer> freq2;

    public Words(String file1, String file2) {
        this.file1 = file1;
        this.file2 = file2;
        this.dictionary = new HashSet<>();
        this.freq1 = new HashMap<>();
        this.freq2 = new HashMap<>();
    }

    public void run() throws IOException {
        try {
            fillContent(file1, freq1);
            fillContent(file2, freq2);
            createDictionary();
            double similarity = calculateSimilarity();
            //System.out.printf("Similarity = %.2f%n", similarity);
            System.out.printf("Similarity = %.2f%n", Math.floor(similarity * 100) / 100);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void fillContent(String file, Map<String, Integer> freqMap) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split("\\W+"); // Разделить по символам, не являющимся словами
                for (String word : words) {
                    if (!word.isEmpty()) {
                        word = word.toLowerCase(); 
                        dictionary.add(word);
                        freqMap.put(word, freqMap.getOrDefault(word, 0) + 1);
                    }
                }
            }
        }
    }

    private void createDictionary() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("dictionary.txt"))) {
            for (String word : dictionary) {
                writer.write(word);
                writer.newLine(); 
            }
        }
    }

    private double calculateSimilarity() {
        int[] vector1 = new int[dictionary.size()];
        int[] vector2 = new int[dictionary.size()];
        List<String> dictList = new ArrayList<>(dictionary);

        for (int i = 0; i < dictList.size(); i++) {
            String word = dictList.get(i);
            vector1[i] = freq1.getOrDefault(word, 0);
            vector2[i] = freq2.getOrDefault(word, 0);
        }

        double numerator = 0.0;
        double normA = 0.0;
        double normB = 0.0;

        for (int i = 0; i < vector1.length; i++) {
            numerator += vector1[i] * vector2[i];
            normA += vector1[i] * vector1[i];
            normB += vector2[i] * vector2[i];
        }

        if (normA == 0 || normB == 0) {
            return 0.0; 
        }

        return numerator / (Math.sqrt(normA) * Math.sqrt(normB));
    }
}