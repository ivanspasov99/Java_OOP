package bg.sofia.uni.fmi.mjt.stylechecker.analyzerTypes;

import java.io.BufferedWriter;
import java.io.IOException;

public interface AnalyzerType {
    void analyze(String line, BufferedWriter output) throws IOException;
}
