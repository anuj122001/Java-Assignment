package Assignment1;
import java.io.*;
import java.net.*;
import java.util.*;

public class WordCounter {

    public static void main(String[] args) {
        List<String> urls = readLines("urls.txt");
        List<String> words = readLines("words.txt");

        if (urls.isEmpty() || words.isEmpty()) {
            System.out.println("URLs or words list is empty.");
            return;
        }

        Map<String, Integer> totalCounts = new HashMap<>();

        for (String url : urls) {
            Map<String, Integer> wordCounts = countWords(url, words);
            printTopWords(url, wordCounts);

            for (Map.Entry<String, Integer> entry : wordCounts.entrySet()) {
                totalCounts.put(entry.getKey(), totalCounts.getOrDefault(entry.getKey(), 0) + entry.getValue());
            }
        }

        printTotalCounts(totalCounts);
    }

    private static List<String> readLines(String file) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line.trim().toLowerCase());
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + file);
        }
        return lines;
    }

    private static Map<String, Integer> countWords(String url, List<String> words) {
        Map<String, Integer> counts = new HashMap<>();
        for (String word : words) {
            counts.put(word, 0);
        }

        try {
            URLConnection conn = new URL(url).openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line.toLowerCase()).append(" ");
            }
            br.close();

            String text = sb.toString().replaceAll("<[^>]+>", " ");
            String[] tokens = text.split("\\W+");

            for (String token : tokens) {
                if (counts.containsKey(token)) {
                    counts.put(token, counts.get(token) + 1);
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to fetch content from: " + url);
        }

        return counts;
    }

    private static void printTopWords(String url, Map<String, Integer> counts) {
        System.out.println("---------\n" + url);
        List<Map.Entry<String, Integer>> list = new ArrayList<>(counts.entrySet());
        Collections.sort(list, (a, b) -> b.getValue() - a.getValue());

        int limit = Math.min(3, list.size());
        for (int i = 0; i < limit; i++) {
            System.out.println(list.get(i).getKey() + " - " + list.get(i).getValue());
        }
    }

    private static void printTotalCounts(Map<String, Integer> totalCounts) {
        System.out.println("==============================");
        List<Map.Entry<String, Integer>> list = new ArrayList<>(totalCounts.entrySet());
        Collections.sort(list, (a, b) -> b.getValue() - a.getValue());

        for (Map.Entry<String, Integer> entry : list) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
    }
}