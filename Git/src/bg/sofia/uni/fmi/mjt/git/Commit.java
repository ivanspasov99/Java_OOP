package bg.sofia.uni.fmi.mjt.git;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Commit {
    private String commitMessage;
    private String hash;
    private String date;

    public Commit(String message) {
        this.commitMessage = message;
        this.date = generateDateString();
        hash = hexDigest(message + date);
    }

    public String getMessage() {
        return commitMessage;
    }

    public String getHash() {
        return hash;
    }

    public String getDate() {
        return date;
    }

    // exception not okay, not in task
    private String hexDigest(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] bytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return convertBytesToHex(bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private String convertBytesToHex(byte[] bytes) {
        StringBuilder hex = new StringBuilder();
        for (byte current : bytes) {
            hex.append(String.format("%02x", current));
        }
        return hex.toString();
    }

    private String generateDateString() {
        LocalDateTime dateTime = LocalDateTime.now();
        // cool thing to format by pattern
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("E MMM dd hh:mm yyyy");
        return dateTime.format(dateTimeFormatter);


    }

}
