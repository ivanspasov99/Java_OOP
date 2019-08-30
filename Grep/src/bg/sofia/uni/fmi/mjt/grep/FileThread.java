package bg.sofia.uni.fmi.mjt.grep;

import java.io.*;
import java.util.*;
import java.util.concurrent.Callable;
// callable is used when you want to return result form thread
public class FileThread implements Callable<String> {
    private final String filePath;
    private final String word;
    private Map<String, Boolean> OPTION_MAP;

    public FileThread(String filePath, String word, Map<String, Boolean> OPTION_MAP) {
        this.filePath = filePath;
        this.word = word;
        this.OPTION_MAP = OPTION_MAP;
    }

    @Override
    public String call() {
        List<String> matches = new ArrayList<>();

        int lineCounter = 0;
        try {
            try(BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    lineCounter++;

                    if(optionsChecking(line)) {
                        matches.add(filePath + ": " + lineCounter + ": " + word);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Unable to open file 'food': No such file or directory");
            e.printStackTrace();
        }
        return String.join(System.lineSeparator(), matches);
    }

    private boolean optionsChecking(String line) {
        String tempWord = word;
        if(OPTION_MAP.isEmpty()) {
            return line.contains(tempWord);
        }
        if(OPTION_MAP.containsKey("i")) {
            line = line.toLowerCase();
            tempWord = tempWord.toLowerCase();
        }
        if(OPTION_MAP.containsKey("w")) {
            List<String> wordsList = Arrays.asList(line.split("[\\p{Punct}\\s]+"));

            return wordsList.contains(tempWord);
        }
        return line.contains(tempWord);
    }
    // can be made, interface Option with one functions that can be implemented by all possible option
    // then you can assign all inputted option in List, iterate it and dynamically call checkFunction
    // :) just giving an idea for better future modifications and additions
}
