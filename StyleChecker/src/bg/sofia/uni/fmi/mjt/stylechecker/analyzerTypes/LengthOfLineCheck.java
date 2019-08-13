package bg.sofia.uni.fmi.mjt.stylechecker.analyzerTypes;

import java.io.IOException;

public class LengthOfLineCheck implements AnalyzerType {
    private int MAX_LENGTH;

    public void setMaxLength(int len) {
        this.MAX_LENGTH = len;
    }

    @Override
    public void analyze(String line, StringBuilder outputString) throws IOException {
        if (!line.trim().startsWith("import ") && line.trim().length() >= MAX_LENGTH) {
            outputString.append("// FIXME Length of line should not exceed " + MAX_LENGTH + " characters" + System.lineSeparator());
        }
    }
}
