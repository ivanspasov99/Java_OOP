package bg.sofia.uni.fmi.mjt.sentiment.sentence.operations;

import java.util.Arrays;

import java.util.Set;

public class SentenceCleaner {
    public static Set<String> wordsSet;

    public static void initStopWords(Set<String> set) {
        wordsSet = set;
    }

    public static boolean containsStopWord(String word) {
        return wordsSet.contains(word);
    }

    static String[] clean(String line) {
        // \\W (non letter characters), +(in a row)
        String[] words = line.trim().split("[\\p{Punct}\\s]+");
        Object[] cleanedWordsArray = Arrays.stream(words)
                .filter(word -> !wordsSet.contains(word))
                .map(String::trim)
                .toArray();

        return Arrays.copyOf(cleanedWordsArray, cleanedWordsArray.length, String[].class);
    }
}
