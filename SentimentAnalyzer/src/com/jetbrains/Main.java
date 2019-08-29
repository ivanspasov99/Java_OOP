package com.jetbrains;

import bg.sofia.uni.fmi.mjt.sentiment.MovieReviewSentimentAnalyzer;

import java.io.*;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        InputStream reviewInput = new FileInputStream("resources/reviews.txt");
        InputStream stopWordsInput = new FileInputStream("resources/stopwords.txt");
        OutputStream reviewOutput = new FileOutputStream("resources/reviews.txt", true);

        MovieReviewSentimentAnalyzer analyzer = new MovieReviewSentimentAnalyzer(stopWordsInput, reviewInput, reviewOutput);
//        System.out.println(analyzer.getReviewSentiment("Hello i love this game"));
//        System.out.println(analyzer.getReviewSentiment("The me"));
//        System.out.println(analyzer.getReviewSentimentAsName("hate you"));
//          System.out.println(analyzer.getWordSentiment("Love"));
//           System.out.println(analyzer.getMostFrequentWords(1));
        System.out.println(analyzer.getReview(2));
//        System.out.println(analyzer.getMostPositiveWords(2));
//        System.out.println(analyzer.getMostNegativeWords(2));
//        System.out.println(analyzer.getMostNegativeWords(2));
//        analyzer.appendReview("Love this game",4 );
//        System.out.println(analyzer.isStopWord("effects"));
    }
}
