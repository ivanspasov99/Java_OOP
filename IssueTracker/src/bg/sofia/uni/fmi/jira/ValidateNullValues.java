package bg.sofia.uni.fmi.jira;

public abstract class ValidateNullValues {
    public static void validatePar(Object[] objects) {
        for (Object obj: objects) {
            if(obj == null) {
                throw new IllegalArgumentException();
            }
        }
    }
}

