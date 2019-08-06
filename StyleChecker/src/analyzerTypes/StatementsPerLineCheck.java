package analyzerTypes;

import java.io.BufferedWriter;
import java.io.IOException;

public class StatementsPerLineCheck implements AnalyzerType {
    @Override
    public void analyze(String line, BufferedWriter output) throws IOException {
        if (line.trim().split(";").length > 1) {
            output.append("// FIXME Only one statement per line is allowed\r\n");
        }
    }
}
