package bg.sofia.uni.fmi.mjt.grep;

import bg.sofia.uni.fmi.mjt.grep.CustomExceptions.NoSuchCommandException;

class InputCommandValidator {

    boolean validateRequiredCommand(String input) throws NoSuchCommandException {
        // "             grep" ->not valid input
        if (!input.startsWith("grep ")) {
            throw new NoSuchCommandException();
        }
        return true;
    }
}
