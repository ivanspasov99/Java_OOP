import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class SentimentStreamInitializer {

    public static InputStream initStopWordsStream() {
        String stopWords = "i\n" + "a\n" + "of\n" + "to\n" + "can\n" + "it\n" + "you\n" + "this\n" + "think\n"
                + "have\n" + "ever\n" + "the\n" + "and\n" + "really\n" + "was\n" + "just\n";

        return new ByteArrayInputStream(stopWords.getBytes());
    }

    public static InputStream initReviewsStream() {
        String reviews = "0 disgusting ! the most awful movie i have ever seen\n" + "0 just plain awful\n"
                + "0 horrible acting\n" + "0 horrible, terrible and everything bad that you can think of\n"
                + "0 i really hate this movie, it was horrible\n" + "1 lame and poor acting\n" + "1 lame\n" + "1 lame\n"
                + "1 nasty and gross\n" + "1 gross\n" + "2 predictable and boring\n"
                + "2 nothing to say about this, really boring and predictable\n" + "2 i fell asleep\n"
                + "2 netflix and fell asleep\n" + "2 boring af\n" + "3 nice\n" + "3 nice\n" + "3 a pretty good movie\n"
                + "3 good acting and pretty good visual effects\n" + "3 good good good\n" + "4 awesome movie\n"
                + "4 brilliant\n" + "4 brilliant\n" + "4 brilliant\n" + "4 awesome  ! good acting \n";

        return new ByteArrayInputStream(reviews.getBytes());
    }
}
