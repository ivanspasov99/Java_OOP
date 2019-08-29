package bg.sofia.uni.fmi.mjt.grep;

import bg.sofia.uni.fmi.mjt.grep.CustomExceptions.InvalidInputException;

import java.util.*;

class InputGrepCommandParser {
    // could add OptionsList and traverse it to check for possible options
    private ArrayList<String> list;

    InputGrepCommandParser(String[] input) {
        final int COMMAND_POSITION = 0;
        list = new ArrayList<>(Arrays.asList(input));
        list.remove(COMMAND_POSITION);
    }

    String getWord() throws InvalidInputException {
        return getNextString();
    }

    String getDirectory() throws InvalidInputException {
        return getNextString();
    }

    Map<String, Boolean> getOptions() {
        Map<String, Boolean> optionMap = new HashMap<>();
        if (list.remove("-wi") || list.remove("-iw")) {
            optionMap.put("i", true);
            optionMap.put("w", true);
            return optionMap;
        }
        if (list.remove("-i")) {
            optionMap.put("i", true);
        }
        if (list.remove("-w")) {
            optionMap.put("w", true);
        }
        return optionMap;
    }

    int getThreadNumber() throws InvalidInputException {
        final int threads;
        try {
            threads = Integer.parseInt(getNextString());
        } catch (NumberFormatException e) {
            throw new InvalidInputException();
        }
        return threads;
    }

    String getOutPutFilePath() throws InvalidInputException {
        if (!list.isEmpty()) {
            getNextString();
        }
        return null;
    }

    private String getNextString() throws InvalidInputException {
        Optional<String> string = list.stream().findFirst();
        if (!string.isPresent()) {
            throw new InvalidInputException();
        }
        list.remove(string.get());
        return string.get();
    }
}
