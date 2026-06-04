package test_2020.prvi;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Naloga11 {
    public void main(String[] args) {
        if (args.length != 1) return;
        String imeDatoteke = args[0];

        HashMap<String, Integer> wordFrequency = new HashMap<>();

        try (Scanner sc = new Scanner(new File(imeDatoteke))) {
            while (sc.hasNext()) {
                String word = sc.next();
                String cleanWord = wordCleaner(word);
                wordFrequency.put(cleanWord, wordFrequency.getOrDefault(cleanWord, 0) + 1);
            }
        } catch (FileNotFoundException e) {
            return;
        }

        Map.Entry<String, Integer> maxEntry = Collections.max(
                wordFrequency.entrySet(),
                Map.Entry.comparingByValue()
        );

        System.out.printf("V datoteki ’%s’ se beseda ’%s’ pojavi %d-krat.", Paths.get(imeDatoteke).getFileName().toString(), maxEntry.getKey(), maxEntry.getValue());
    }

    private static String wordCleaner(String word) {
        StringBuilder cleanWord = new StringBuilder();
        for (char c : word.toCharArray()) {
            if (!".,;:()".contains(String.valueOf(c))){
                cleanWord.append(c);
            }
        }

        return cleanWord.toString().toUpperCase();
    }
}
