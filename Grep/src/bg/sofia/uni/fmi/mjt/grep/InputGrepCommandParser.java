package bg.sofia.uni.fmi.mjt.grep;

import bg.sofia.uni.fmi.mjt.grep.CustomExceptions.NoDirecrotyFound;
import bg.sofia.uni.fmi.mjt.grep.CustomExceptions.NoWordFound;

class InputGrepCommandParser {
    private static boolean fullWords;
    private static boolean caseSensitive;

    InputGrepCommandParser(boolean fullWords, boolean caseSensitive) {
        this.fullWords = fullWords;
        this.caseSensitive = caseSensitive;
    }

    String getWord(String[] input) throws NoWordFound {
        // depending if specific command is invoked
        final int WORD_POSITION = (fullWords || caseSensitive) ? 2 : 1;

        // checking for no word scenario
        if (input[WORD_POSITION].startsWith("/")) {
            throw new NoWordFound();
        }
        return input[WORD_POSITION];
    }

    String getDirectory(String[] input) throws NoDirecrotyFound {
        // is it possible to start without /??
        final int DIRECTORY_POSITION = (fullWords || caseSensitive) ? 3 : 2;

        if (!input[DIRECTORY_POSITION].startsWith("/")) {
            throw new NoDirecrotyFound();
        }
        return input[DIRECTORY_POSITION];
    }

    String getOutputFilePath(String[] input) throws NoDirecrotyFound {
        final int OPTIONAL_FILE_PATH_POSITION = (fullWords || caseSensitive) ? 5 : 4;
        if (input.length > OPTIONAL_FILE_PATH_POSITION) {
            return input[OPTIONAL_FILE_PATH_POSITION];
        }
        return "";
    }

    int getThreads(String[] input) throws NoThreadNumberException {
        final int THREAD_POSITION = (fullWords || caseSensitive) ? 4 : 3;

        try {
            return Integer.parseInt(input[THREAD_POSITION]);
        } catch (NumberFormatException e) {
            throw new NoThreadNumberException();
        }
    }

}
