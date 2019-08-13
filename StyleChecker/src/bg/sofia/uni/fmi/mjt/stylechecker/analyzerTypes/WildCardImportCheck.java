package bg.sofia.uni.fmi.mjt.stylechecker.analyzerTypes;

import java.io.IOException;

public class WildCardImportCheck implements AnalyzerType {

    @Override
    public void analyze(String line, StringBuilder outputString) throws IOException {
        // comments are not allowed
        // will not run if '*' is used for something different from import
        if (line.trim().split(";")[0].endsWith(".*")) {
            outputString.append("// FIXME Wildcards are not allowed in import statements" + System.lineSeparator());
        }
    }
}
