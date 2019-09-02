package bg.sofia.uni.fmi.mjt.grep;

import bg.sofia.uni.fmi.mjt.grep.CustomExceptions.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

// executor shutdown()??
public class StringSearcher {
    private String word;
    private String folderPath;
    private ExecutorService executor;
    private String outPutFilePath;
    private Map<String, Boolean> OPTION_MAP;
    private InputGrepCommandParser inputGrepCommandParser;
    private InputCommandValidator inputCommandValidator;

    // close the source when??
    private InputStream source;

    public StringSearcher() {
    }

    public List<String> stringSearch(InputStream source) throws Throwable {
        // add while until quit
        this.source = source;

        String input = read(this.source);

        inputCommandValidator = new InputCommandValidator();
        inputCommandValidator.validateRequiredCommand(input);

        inputGrepCommandParser = new InputGrepCommandParser(input.split(" "));


        // We can encapsulate all of these operations in InputCommandParser and return Map with appropriate values, directly from
        // let's say function getCommandData().
        this.OPTION_MAP = inputGrepCommandParser.getOptions();
        this.word = inputGrepCommandParser.getWord();
        this.folderPath = inputGrepCommandParser.getDirectory();
        this.executor = Executors.newFixedThreadPool(inputGrepCommandParser.getThreadNumber());
        this.outPutFilePath = inputGrepCommandParser.getOutPutFilePath();

        return startExecution();
    }

    private List<String> startExecution() {
        // maybe we need safe collection
        // CopyOnWriteArrayList
        List<Path> filesPathList = new ArrayList<>();

        try {
            filesPathList = Files.walk(Paths.get(this.folderPath))
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (filesPathList.isEmpty()) {
            System.out.println("No Files to search!");
            return null;
        }

        // every match form file - all threads
        List<String> futures = new ArrayList<>();

        for (Path path : filesPathList) {
            FileThread fileThread = new FileThread(path.toString(), this.word, OPTION_MAP);
            Future<String> future = executor.submit(fileThread);
            try {
                if (!(future.get().isEmpty())) {
                    List<String> oneThreadWordMatches = Arrays.asList(future.get().split(System.lineSeparator()));
                    futures.addAll(oneThreadWordMatches);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        this.executor.shutdown();

        return futures;
    }

    private String read(InputStream inputStream) throws EmptyFileException {
        // There is no need to close the scanner.
        // Not closing might generate some warnings although.
        // As soon as the block in which you defined Scanner object is over the garbage collection does your job for you.
        Scanner scanner = new Scanner(inputStream);

        if (!scanner.hasNext()) {
            throw new EmptyFileException();
        }
        String temp = scanner.nextLine();
        scanner.close();
        return temp;
    }
}
