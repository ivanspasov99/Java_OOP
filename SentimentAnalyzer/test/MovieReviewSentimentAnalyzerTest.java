import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.stream.Collectors;

import bg.sofia.uni.fmi.mjt.sentiment.MovieReviewSentimentAnalyzer;
import org.junit.Before;
import org.junit.Test;

public class MovieReviewSentimentAnalyzerTest {

	private static final int DICTIONARY_SIZE = 33;
	private static final double DELTA = 0.001;

	private MovieReviewSentimentAnalyzer analyzer;

	private InputStream reviewsStream;
	private InputStream stopwordsStream;
	private ByteArrayOutputStream resultStream;

	@Before
	public void init() {
		stopwordsStream = SentimentStreamInitializer.initStopWordsStream();
		reviewsStream = SentimentStreamInitializer.initReviewsStream(); // File("test//../resources/reviews.txt"));
		resultStream = new ByteArrayOutputStream();
		analyzer = new MovieReviewSentimentAnalyzer(stopwordsStream, reviewsStream, resultStream);
	}

	@Test
	public void testIsStopWordNegativeFromDictionary() {
		String assertMessage = "A word should not be incorrectly identified as a stopword, if it is not part of the stopwords list";
		assertFalse(assertMessage, analyzer.isStopWord("effects"));
	}

	@Test
	public void testIsStopWordNegativeNotFromDictionary() {
		String assertMessage = "A word should not be incorrectly identified as a stopword, if it is not part of the stopwords list";
		assertFalse(assertMessage, analyzer.isStopWord("stoyo"));
	}

	@Test
	public void testIsStopWordEmpty() {
		assertFalse("The empty word is not a stopword", analyzer.isStopWord(""));
	}

	@Test
	public void testIsStopWordPositive() {
		assertTrue("Stop word not counted as stop word", analyzer.isStopWord("a"));
	}

	@Test
	public void testGetWordSentimentFromDictionary1() {
		String assertMessage = "Word sentiment should be properly calculated for non-stopword words found in reviews input stream";
		String word = "brilliant";
		final double score = 4.0;

		assertEquals(assertMessage, score, analyzer.getWordSentiment(word), DELTA);
	}

	@Test
	public void testGetWordSentimentFromDictionary2() {
		String assertMessage = "Word sentiment should be properly calculated for non-stopword words found in reviews input stream";
		String word = "movie";
		final double score = 1.75;

		assertEquals(assertMessage, score, analyzer.getWordSentiment(word), DELTA);
	}

	@Test
	public void testGetWordSentimentNotFromDictionary() {
		String assertMessage = "Word sentiment of a word not in the dictionary should be -1.0 (unknown)";
		final double score = -1.0;
		assertEquals(assertMessage, score, analyzer.getWordSentiment("stoyo"), DELTA);
	}

	@Test
	public void testGetWordSentimentStopword() {
		String assertMessage = "Word sentiment of a stopword should be -1.0 (unknown)";
		final double score = -1.0;

		assertEquals(assertMessage, score, analyzer.getWordSentiment("was"), DELTA);
	}

	@Test
	public void testGetWordSentimentEmptyWord() {
		String assertMessage = "Word sentiment of the empty word should be -1.0 (unknown)";
		final double score = -1.0;

		assertEquals(assertMessage, score, analyzer.getWordSentiment(""), DELTA);
	}

	@Test
	public void testGetWordSentimentCaseInsensitive() {
		String assertMessage = "Word sentiment should not depend on case - calculation should be case-insensitive";
		final double score = 1.75;

		assertEquals(assertMessage, score, analyzer.getWordSentiment("movie"), DELTA);
	}

	@Test
	public void testGetWordSentimentCaseInsensitive2() {
		String assertMessage = "Word sentiment should not depend on case - calculation should be case-insensitive";
		final double score = 1.75;

		assertEquals(assertMessage, score, analyzer.getWordSentiment("MoviE"), DELTA);
	}

	@Test
	public void testGetMostFrequentWords5() {
		String assertMessage = "Getting the 5 most frequent words should work properly";
		Collection<String> list = analyzer.getMostFrequentWords(5).stream().map(String::toLowerCase)
				.collect(Collectors.toList());

		assertTrue(assertMessage, list.contains("horrible") && list.contains("acting") && list.contains("good"));
	}

	@Test
	public void testGetMostFrequentWord() {
		String assertMessage = "Getting the single most frequent word should work properly";
		Collection<String> list = analyzer.getMostFrequentWords(1).stream().map(String::toLowerCase)
				.collect(Collectors.toList());

		assertTrue(assertMessage, list.contains("good"));
	}

	@Test
	public void testGetMostFrequentWords0() {
		String assertMessage = "Getting the 0 most frequent word should return an empty list";
		Collection<String> list = analyzer.getMostFrequentWords(0);

		assertTrue(assertMessage, list.isEmpty());
	}

	@Test
	public void testGetMostPositiveWords5() {
		Collection<String> list = analyzer.getMostPositiveWords(5).stream().map(String::toLowerCase)
				.collect(Collectors.toList());

		String assertFirst = "Getting the N most positive words should return a list with N elements as longs as there are at least N words in the dictionary";
		assertEquals(assertFirst, 5, list.size());
		String assertSecond = "Getting the most positive words should word properly";
		assertTrue(assertSecond, list.contains("brilliant") && list.contains("good") && list.contains("awesome"));
	}

	@Test
	public void testGetMostPositiveWords0() {
		String assertMessage = "Getting the 0 most positive words should return an empty list";
		Collection<String> list = analyzer.getMostPositiveWords(0);
		assertTrue(assertMessage, list.isEmpty());
	}

	@Test
	public void testGetMostPositiveWords100000() {
		String assertMessage = "Getting the N most positive words should return a list with K elements, if there are K words in the dictionary and N > K";
		Collection<String> list = analyzer.getMostPositiveWords(100000);
		assertEquals(assertMessage, DICTIONARY_SIZE, list.size());
	}

	@Test
	public void testGetMostNegativeWords1000() {
		Collection<String> list = analyzer.getMostNegativeWords(3).stream().map(String::toLowerCase)
				.collect(Collectors.toList());

		String assertFirst = "Getting the N most negative words should return a list with N elements as longs as there are at least N words in the dictionary";
		assertEquals(assertFirst, 3, list.size());

		String assertSecond = "Getting the most negative words should work properly";
		assertTrue(assertSecond, list.contains("awful") && list.contains("bad") && list.contains("seen"));
	}

	@Test
	public void testGetMostNegativeWords0() {
		String assertMessage = "Getting the 0 most negative words should return an empty list";
		Collection<String> list = analyzer.getMostPositiveWords(0);

		assertTrue(assertMessage, list.isEmpty());
	}

	@Test
	public void testGetMostNegativeWords3() {
		String assertMessage = "Getting the N most negative words should return a list with K elements, if there are K words in the dictionary and N > K";
		Collection<String> list = analyzer.getMostPositiveWords(100);

		assertEquals(assertMessage, DICTIONARY_SIZE, list.size());
	}

	@Test
	public void testGetReviewSentimentNotInDictionary() {
		String assertMessage = "Review sentiment should be -1 (unknown) if all words in it are unknown (not in the dictionary)";

		assertEquals(assertMessage, -1.0, analyzer.getReviewSentiment("stoyo- stoyo STOYO boyo fmiisthebest"), DELTA);
	}

	@Test
	public void testGetReviewSentimentAllStopwords() {
		String assertMessage = "Review sentiment should be -1 (unknown) if all words in it are stopwords";
		assertEquals(assertMessage, -1.0, analyzer.getReviewSentiment("i THE was"), DELTA);
	}

	@Test
	public void testGetReviewSentimentMixedUnknown() {
		String assertMessage = "Review sentiment should be -1 (unknown) if all words in it are with unknown sentiment";
		assertEquals(assertMessage, -1.0, analyzer.getReviewSentiment(" is vellev **&&"), DELTA);
	}

	@Test
	public void testGetReviewSentimentMixedKnownAndUnknown() {
		String assertMessage = "Review sentiment should not be influenced by words with unknown sentiment";
		assertEquals(assertMessage, 4.0, analyzer.getReviewSentiment("brilliant and stoyo"), DELTA);
	}

	@Test
	public void testGetReviewSentimentAsNameNegative() {
		String assertMessage = "Review sentiment in the interval [0.0, 1.4999] has name \"negative\"";
		assertEquals(assertMessage, "negative", analyzer.getReviewSentimentAsName("disgusting"));
	}

	@Test
	public void testGetReviewSentimentAsNameSomewhatNegative() {
		String assertMessage = "Review sentiment in the interval [1.5, 2.4999] has name \"somewhat negative\"";
		assertEquals(assertMessage, "somewhat negative", analyzer.getReviewSentimentAsName("lame"));
	}

	@Test
	public void testGetReviewSentimentAsNameNeutral() {
		String assertMessage = "Review sentiment in the interval [1.5, 2.4999] has name \"neutral\"";
		assertEquals(assertMessage, "neutral", analyzer.getReviewSentimentAsName("acting"));
	}

	@Test
	public void testGetReviewSentimentAsNameSomewhatPositive() {
		String assertMessage = "Review sentiment in the interval [2.5, 3.4999] has name \"somewhat positive\"";
		assertEquals(assertMessage, "somewhat positive", analyzer.getReviewSentimentAsName("effects"));
	}

	@Test
	public void testGetReviewSentimentAsNamePositive() {
		String assertMessage = "Review sentiment in the interval [3.5, 4.0] has name \"positive\"";
		assertEquals(assertMessage, "positive", analyzer.getReviewSentimentAsName("awesome"));
	}

	@Test
	public void testGetReviewSentimentAsNameUnknown() {
		String assertMessage = "Review sentiment -1.0 has name \"unknown\"";
		String expected = "unknown";
		String actual = analyzer.getReviewSentimentAsName("stoyo");

		assertEquals(assertMessage, expected, actual);
	}

	@Test
	public void testGetReviewSentimentAverageOfWordSentiments() {
		String assertMessage = "Review sentiment should be the average sentiment of its words";
		final double expected = 2.0;
		double actual = analyzer.getReviewSentiment("predictable and boring |- |");

		assertEquals(assertMessage, expected, actual, DELTA);
	}
	@Test
	public void testGetReview() {
		String assertMessage = "Get review should return a review with a value equal to the value";
		final double expected = 3.142857;
		String review = analyzer.getReview(expected);
		double actual = analyzer.getReviewSentiment(review);

		assertEquals(assertMessage, expected, actual, DELTA);
	}

	@Test
	public void tesGetReviewWithWrongValue() {
		String assertMessage = "Get review should return a review with a value equal to the value";
		final double wrongScore = 4.1;
		String review = analyzer.getReview(wrongScore);

		assertNull(assertMessage, review);
	}

	@Test
	public void testIfSentimentIncreases() throws UnsupportedEncodingException {
		String word = "good";
		String review = "really nice movie, good acting and nice visual effects";

		final double before = analyzer.getWordSentiment(word);
		analyzer.appendReview(review, 4);
		final double after = analyzer.getWordSentiment(word);
		String output = resultStream.toString();

		assertTrue(after > before);
		assertEquals("4 really nice movie, good acting and nice visual effects" + System.lineSeparator(), output);
	}

	@Test
	public void testIfOutputIsusedCorrectly() {
		String review = "really nice movie, good acting and nice visual effects";
		final int score = 4;
		analyzer.appendReview(review, score);

		String expected = String.format("%d %s" + System.lineSeparator(), score, review);
		String actual = new String(resultStream.toByteArray());
		assertEquals(expected, actual);
	}
}
