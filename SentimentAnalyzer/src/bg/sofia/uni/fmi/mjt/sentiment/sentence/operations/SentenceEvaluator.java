package bg.sofia.uni.fmi.mjt.sentiment.sentence.operations;

import bg.sofia.uni.fmi.mjt.sentiment.word.info.WordValue;

import java.util.HashMap;
import java.util.Map;

public class SentenceEvaluator {
    private Map<String, WordValue> wordsMap = new HashMap<>();
    private static final Map<Integer, String> RATING_NAME_MAP = Map.of(
            -1, "unknown",
            0, "negative",
            1, "somewhat negative",
            2, "neutral",
            3, "somewhat positive",
            4, "positive"
    );

    public float evaluate(String sentence) {
        String[] cleanedWords = SentenceCleaner.clean(sentence);
        boolean flag = false;
        float sentenceScore = 0;
        int matchedWordInSentence = 0;
        for (int i = 0; i < cleanedWords.length; i++) {
            if (wordsMap.containsKey(cleanedWords[i].toLowerCase())) {
                flag = true;
                sentenceScore += wordsMap.get(cleanedWords[i].toLowerCase()).evaluateWord();
                matchedWordInSentence++;
            }
        }

        return (flag) ? (sentenceScore / matchedWordInSentence) : -1;
    }

    public Map<String, WordValue> getWordsMap() {
        return wordsMap;
    }

    public String getNameReview(int value) {
        return RATING_NAME_MAP.get(value);
    }


}
