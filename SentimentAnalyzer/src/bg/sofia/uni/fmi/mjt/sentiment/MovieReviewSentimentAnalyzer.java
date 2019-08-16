package bg.sofia.uni.fmi.mjt.sentiment;

import bg.sofia.uni.fmi.mjt.sentiment.sentence.operations.SentenceAnalyzer;
import bg.sofia.uni.fmi.mjt.sentiment.sentence.operations.SentenceCleaner;
import bg.sofia.uni.fmi.mjt.sentiment.sentence.operations.SentenceEvaluator;
import bg.sofia.uni.fmi.mjt.sentiment.word.info.WordValue;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class MovieReviewSentimentAnalyzer {
    private final static SentenceAnalyzer sentenceAnalyzer = new SentenceAnalyzer();
    private final SentenceEvaluator sentenceEvaluator = new SentenceEvaluator();
    private final static double DELTA = 0.001;
    private List<String> sentenceSet = new ArrayList<>();
    private final OutputStream outputStream;

    public MovieReviewSentimentAnalyzer(InputStream stopWordsInput, InputStream reviewsInput, OutputStream reviewsOutput) {
        SentenceCleaner.initStopWords(parseStopWords(stopWordsInput));
        parseReviewsIntoWordsMap(reviewsInput);
        this.outputStream = reviewsOutput;
    }

    public double getReviewSentiment(String review) {
        return sentenceEvaluator.evaluate(review);
    }

    public String getReviewSentimentAsName(String review) {
        return sentenceEvaluator.getNameReview(Math.round(sentenceEvaluator.evaluate(review)));
    }

    public double getWordSentiment(String word) {
        String keyWord = word.toLowerCase();
        if (sentenceEvaluator.getWordsMap().containsKey(keyWord)) {
            return sentenceEvaluator.getWordsMap().get(keyWord).evaluateWord();
        }
        return -1d;
    }

    public Collection<String> getMostFrequentWords(int n) {
        validate(n);
        return getMostOfType(Comparator.comparing((Map.Entry<String, WordValue> e) -> -(e.getValue().getTimesEncountered())), n);
    }

    public Collection<String> getMostPositiveWords(int n) {
        validate(n);
        return getMostOfType(Comparator.comparing((Map.Entry<String, WordValue> e) -> -(e.getValue().evaluateWord())), n);
    }

    public Collection<String> getMostNegativeWords(int n) {
        validate(n);
        return getMostOfType(Comparator.comparing((Map.Entry<String, WordValue> e) -> e.getValue().evaluateWord()), n);
    }

    public void appendReview(String review, int sentimentValue) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream))) {
            // add to current wordsMap
            String newSentence = sentimentValue + " " + review;
            sentenceAnalyzer.analyze(newSentence)
                    .forEach((key, value)
                            -> sentenceEvaluator.getWordsMap().merge(key, value, (v1, v2)
                            -> new WordValue(key, v1.getScoreSum() + v2.getScoreSum(), v1.getTimesEncountered() + v2.getTimesEncountered())));
            bufferedWriter.write(newSentence + System.lineSeparator());

        } catch (IOException ioEx) {
            System.out.println("FAILED TO WRITE FILE");
        }
    }

    public int getSentimentDictionarySize() {
        return sentenceEvaluator.getWordsMap().size();
    }

    public String getReview(double sentimentValue) {
        if (sentenceSet.stream().anyMatch(e -> Math.abs(sentenceEvaluator.evaluate(e) - sentimentValue) < DELTA)) {
            return sentenceSet.stream().filter(e -> Math.abs(sentenceEvaluator.evaluate(e) - sentimentValue) < DELTA).findFirst().get();
        }
        return null;
    }

    public boolean isStopWord(String word) {
        return SentenceCleaner.containsStopWord(word);
    }

    private Set<String> parseStopWords(InputStream stopWordsInput) {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stopWordsInput))) {
            return bufferedReader.lines().map(String::new).collect(Collectors.toSet());
        } catch (IOException ioEx) {
            System.out.println("Reading error occurred");
            return null;
        }
    }

    private void parseReviewsIntoWordsMap(InputStream reviewsInput) {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(reviewsInput))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // adding or updating words in current wordsMap
                sentenceSet.add(line);
                sentenceAnalyzer.analyze(line).forEach((key, value)
                        -> sentenceEvaluator.getWordsMap().merge(key, value, (v1, v2)
                        -> new WordValue(key, v1.getScoreSum() + v2.getScoreSum(), v1.getTimesEncountered() + v2.getTimesEncountered())));
            }
        } catch (IOException ioEx) {
            System.out.println("Reading error occurred");
        }

    }

    private void validate(int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
    }

    private Collection<String> getMostOfType(Comparator<Map.Entry<String, WordValue>> comparator, int n) {
        return sentenceEvaluator.getWordsMap()
                .entrySet()
                .stream()
                .sorted(comparator)
                .limit(n)
                .map(word -> word.getValue().getName())
                .collect(Collectors.toList());
    }

}
