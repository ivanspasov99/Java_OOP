package bg.sofia.uni.fmi.mjt.stylechecker.analyzerTypes;

import java.io.BufferedWriter;
import java.io.IOException;

public class WildCardImportCheck implements AnalyzerType {

    @Override
    public void analyze(String line, BufferedWriter output) throws IOException {
        // comments are not allowed
        // will not run if '*' is used for something different from import
        if (line.trim().contains("import") && line.trim().contains("*")) {
            output.append("// FIXME Wildcards are not allowed in import statements\r\n");
        }
    }
}
