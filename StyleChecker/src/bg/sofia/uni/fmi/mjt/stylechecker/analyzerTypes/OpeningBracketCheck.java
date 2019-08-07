package bg.sofia.uni.fmi.mjt.stylechecker.analyzerTypes;

import java.io.BufferedWriter;
import java.io.IOException;

public class OpeningBracketCheck implements AnalyzerType {
    @Override
    public void analyze(String line, BufferedWriter output) throws IOException {
        if (line.trim().equals("{")) {
            output.append("// FIXME Opening brackets should be placed on the same line as the declaration\r\n");
        }
    }
}
