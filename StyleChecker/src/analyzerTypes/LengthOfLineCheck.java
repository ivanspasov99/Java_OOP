package analyzerTypes;

import java.io.BufferedWriter;
import java.io.IOException;

public class LengthOfLineCheck implements AnalyzerType {
    private int MAX_LENGTH;

    public void setMaxLength(int len) {
        this.MAX_LENGTH = len;
    }

    @Override
    public void analyze(String line, BufferedWriter output) throws IOException {
        if (line.indexOf("i") != 0 && line.trim().length() > MAX_LENGTH) {
            output.append("// FIXME Length of line should not exceed " + MAX_LENGTH + " characters\r\n");
        }
    }
}
