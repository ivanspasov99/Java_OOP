package bg.sofia.uni.fmi.mjt.stylechecker.analyzerTypes;

import java.io.BufferedWriter;
import java.io.IOException;

public class StatementsPerLineCheck implements AnalyzerType {
    // depends on what "valid input" is.
    @Override
    public void analyze(String line, StringBuilder outputString) throws IOException {

        if (line.trim().split(";").length > 1) {
             outputString.append("// FIXME Only one statement per line is allowed" + System.lineSeparator());
        }
    }
}
