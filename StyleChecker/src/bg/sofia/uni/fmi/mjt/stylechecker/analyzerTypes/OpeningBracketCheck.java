package bg.sofia.uni.fmi.mjt.stylechecker.analyzerTypes;

import java.io.BufferedWriter;
import java.io.IOException;

public class OpeningBracketCheck implements AnalyzerType {
    @Override
    public void analyze(String line, StringBuilder outputString) throws IOException {
        if (line.trim().startsWith("{")) {
            outputString.append("// FIXME Opening brackets should be placed on the same line as the declaration" + System.lineSeparator());
        }
    }
}
