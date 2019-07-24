package bg.sofia.uni.fmi.jira;

public abstract class GenerateId {
    private static int idCounter = 1;

    public static int generateId() {
        return  idCounter++;
    }
}
