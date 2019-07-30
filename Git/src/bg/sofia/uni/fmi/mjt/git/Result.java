package bg.sofia.uni.fmi.mjt.git;

public class Result {
    private String message;
    private boolean isSuccessfull;

    public Result(String mess, boolean isSucc) {
        this.message = mess;
        this.isSuccessfull = isSucc;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccessful() {
        return isSuccessfull;
    }
}
