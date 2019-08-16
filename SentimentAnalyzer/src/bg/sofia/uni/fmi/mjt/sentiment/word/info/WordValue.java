package bg.sofia.uni.fmi.mjt.sentiment.word.info;

public class WordValue {
    private String name;
    private double scoreSum;
    private int timesEncountered;

    public WordValue(String name, double score, int timesEncountered) {
        this.name = name;
        this.scoreSum = score;
        this.timesEncountered = timesEncountered;
    }

    public WordValue(String name, double score) {
        this.name = name;
        this.scoreSum = score;
        this.timesEncountered = 1;
    }

    public double evaluateWord() {
        return scoreSum / timesEncountered;
    }


    public double getScoreSum() {
        return scoreSum;
    }

    public int getTimesEncountered() {
        return timesEncountered;
    }

    public String getName() {
        return name;
    }
    public void increaseCountEncountered() {
        timesEncountered++;
    }
    public void increaseScore(double score) {
        scoreSum+= score;
    }
}
