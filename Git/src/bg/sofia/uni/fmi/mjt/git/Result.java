package bg.sofia.uni.fmi.mjt.git;

public class Result {
    private String message;
    private boolean isSuccessful;

    Result(String mess, boolean isSucc) {
        this.message = mess;
        this.isSuccessful = isSucc;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }
}
