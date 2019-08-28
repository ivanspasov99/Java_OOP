package bg.sofia.uni.fmi.mjt.grep;

import bg.sofia.uni.fmi.mjt.grep.CustomExceptions.NoDirecrotyFound;
import bg.sofia.uni.fmi.mjt.grep.CustomExceptions.NoSuchCommandException;
import bg.sofia.uni.fmi.mjt.grep.CustomExceptions.NoWordFound;
import bg.sofia.uni.fmi.mjt.grep.CustomExceptions.NotValidInputException;

import java.io.InputStream;
import java.util.Scanner;

// grep foo /Users/my-user/git/java-course 2


// TO DO -> ValidateClass
// PARSING CLASS
// maybe to make different Exceptions for grep, and not valid command at all
public class Main {
    private String word;
    private String folder;
    private int threadsNumber;
    // could not exist(optional parameter)
    private String outPutFilePath;
    private boolean fullWords = false;
    private boolean caseSensitive = false;
    private InputValidator inputValidator;
    private InputGrepCommandParser inputGrepCommandParser;

    private InputStream source;

    public Main(InputStream source) throws NoSuchCommandException, NoWordFound, NoDirecrotyFound, NotValidInputException, NoThreadNumberException {
        this.source = source;

        String input = read(this.source);

        String[] tokens = input.split(" ");

        fullWords = checkForFullWordsCommand(tokens);
        caseSensitive = checkForCaseSensitive(tokens);

        inputValidator = new InputValidator();
        inputGrepCommandParser = new InputGrepCommandParser(fullWords, caseSensitive);

        inputValidator.validateInputLength(tokens);
        inputValidator.validateRequiredCommand(input);

        word = inputGrepCommandParser.getWord(tokens);

        folder = inputGrepCommandParser.getDirectory(tokens);

        threadsNumber = inputGrepCommandParser.getThreads(tokens);

        outPutFilePath = inputGrepCommandParser.getOutputFilePath(tokens);

    }

    private String read(InputStream inputStream) throws NotValidInputException {
        // There is no need to close the scanner.
        // Not closing might generate some warnings although.
        // As soon as the block in which you defined Scanner object is over the garbage collection does your job for you.
        Scanner scanner = new Scanner(inputStream);

        if (!scanner.hasNext()) {
            throw new NotValidInputException();
        }
        return scanner.nextLine();
    }

    private boolean checkForCaseSensitive(String[] input) {
        final int FULL_WORD_CHECK_COMMAND_POSITION = 1;
        return input[FULL_WORD_CHECK_COMMAND_POSITION].startsWith("-")
                && input[FULL_WORD_CHECK_COMMAND_POSITION].contains("i");
    }

    private boolean checkForFullWordsCommand(String[] input) {
        final int FULL_WORD_CHECK_COMMAND_POSITION = 1;
        return input[FULL_WORD_CHECK_COMMAND_POSITION].startsWith("-") && input[FULL_WORD_CHECK_COMMAND_POSITION].contains("w");
    }



}
