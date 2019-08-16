package bg.sofia.uni.fmi.mjt.sentiment.sentence.operations;

import bg.sofia.uni.fmi.mjt.sentiment.word.info.WordValue;

import java.util.HashMap;
import java.util.Map;

public class SentenceAnalyzer {
    private final static int WORD_VALUE_POSITION = 0;

    // could be diff type of cleaner
    public Map<String, WordValue> analyze(String line) {

        Map<String, WordValue> tempWordsMap = new HashMap<>();
        String[] cleanedSentenceWords = SentenceCleaner.clean(line);

        final int VALUE_OF_WORD = Integer.parseInt(cleanedSentenceWords[WORD_VALUE_POSITION]);

        for (int i = 1; i < cleanedSentenceWords.length; i++) {
            String name = cleanedSentenceWords[i];
            if(tempWordsMap.containsKey(name)) {
                WordValue wordValue = tempWordsMap.get(name);
                wordValue.increaseScore(VALUE_OF_WORD);
                wordValue.increaseCountEncountered();
            } else {
                tempWordsMap.put(name.toLowerCase(), new WordValue(name, VALUE_OF_WORD));
            }
        }
        return tempWordsMap;
    }
}
