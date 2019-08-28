package bg.sofia.uni.fmi.mjt.grep;

import bg.sofia.uni.fmi.mjt.grep.CustomExceptions.NoSuchCommandException;
import bg.sofia.uni.fmi.mjt.grep.CustomExceptions.NotValidInputException;

public class InputValidator {
    final static int REQUIRED_MIN_LENGTH = 4;
    final static int REQUIRED_MAX_LENGTH = 6;

    public boolean validateRequiredCommand(String input) throws NoSuchCommandException {
        if (!input.startsWith("grep ")) {
            throw new NoSuchCommandException();
        }
        return true;
    }

    // another name????
    public boolean validateInputLength(String[] input) throws NotValidInputException {
        // check for number of required fields
        if (!(input.length >= REQUIRED_MIN_LENGTH && input.length <= REQUIRED_MAX_LENGTH)) {
            throw new NotValidInputException();
        }
        return true;
    }
}
