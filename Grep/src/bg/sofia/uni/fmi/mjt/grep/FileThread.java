package bg.sofia.uni.fmi.mjt.grep;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
// callable is used when you want to return result form thread
public class FileThread implements Callable<String> {
    private final String filePath;
    private final String word;

    public FileThread(String filePath, String word) {
        this.filePath = filePath;
        this.word = word;
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
                    if (line.contains(word)) {
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
}
