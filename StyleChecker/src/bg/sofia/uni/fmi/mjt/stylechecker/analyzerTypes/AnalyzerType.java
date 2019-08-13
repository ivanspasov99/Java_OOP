package bg.sofia.uni.fmi.mjt.stylechecker.analyzerTypes;

import java.io.IOException;

public interface AnalyzerType {
    // It would be better if we return the String error and with "HandlerClass" ( consumer of the analyzer ) to modify the output or ....
    // We could return error in browser for example
    void analyze(String line, StringBuilder outputString) throws IOException;
}
