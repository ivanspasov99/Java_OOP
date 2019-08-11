package bg.sofia.uni.fmi.mjt.stylechecker;

import bg.sofia.uni.fmi.mjt.stylechecker.analyzerTypes.*;

import java.io.*;
import java.util.Map;
import java.util.Properties;

public class StyleChecker {
    // properties work only with String, return NULL when integer
    private static final String DEFAULT_MAX_LENGTH_LIMIT = "100";
    private static final String DEFAULT_VALUE = "true";

    private Properties properties = new Properties();

    private static final Map<String, Object> propertyMap = Map.of(
            // i do not like, too long
            StyleProperties.WILDCARD_IMPORT.getCheckName(), DEFAULT_VALUE,
            StyleProperties.STATEMENTS_PER_LINE.getCheckName(), DEFAULT_VALUE,
            StyleProperties.OPENING_BRACKET.getCheckName(), DEFAULT_VALUE,
            StyleProperties.LENGTH_OF_LINE.getCheckName(), DEFAULT_VALUE,
            StyleProperties.LINE_LENGTH_LIMIT.getCheckName(), DEFAULT_MAX_LENGTH_LIMIT
    );
    private static final Map<String, AnalyzerType> analyzerTypeMap = Map.of(
            StyleProperties.WILDCARD_IMPORT.getCheckName(), new WildCardImportCheck(),
            StyleProperties.STATEMENTS_PER_LINE.getCheckName(), new StatementsPerLineCheck(),
            StyleProperties.OPENING_BRACKET.getCheckName(), new OpeningBracketCheck(),
            StyleProperties.LENGTH_OF_LINE.getCheckName(), new LengthOfLineCheck()
    );

    public StyleChecker() {
        this(null);
    }

    public StyleChecker(InputStream inputStream) {
        initDefaultProperties();
        if (inputStream != null) {
            initSpecificProperties(inputStream);
        }
    }

    // try() is closing and flushing files in it automatically
    public void checkStyle(InputStream source, OutputStream output) {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(source));
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(output))) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.trim().equals("")) {
                    bufferedWriter.append(line + System.lineSeparator());
                    continue;
                }
                if (isPropertySet(StyleProperties.WILDCARD_IMPORT.getCheckName())) {
                    analyzerTypeMap.get(StyleProperties.WILDCARD_IMPORT.getCheckName()).analyze(line, bufferedWriter);
                }
                if (isPropertySet(StyleProperties.STATEMENTS_PER_LINE.getCheckName())) {
                    analyzerTypeMap.get(StyleProperties.STATEMENTS_PER_LINE.getCheckName()).analyze(line, bufferedWriter);
                }
                if (isPropertySet(StyleProperties.OPENING_BRACKET.getCheckName())) {
                    analyzerTypeMap.get(StyleProperties.OPENING_BRACKET.getCheckName()).analyze(line, bufferedWriter);
                }
                if (isPropertySet(StyleProperties.LENGTH_OF_LINE.getCheckName())) {
                    LengthOfLineCheck tempLine = (LengthOfLineCheck) analyzerTypeMap.get(StyleProperties.LENGTH_OF_LINE.getCheckName());
                    tempLine.setMaxLength(Integer.parseInt(properties.getProperty(StyleProperties.LINE_LENGTH_LIMIT.getCheckName())));
                    tempLine.analyze(line, bufferedWriter);
                }
                bufferedWriter.append(line + System.lineSeparator());

            }

        } catch (IOException e) {
            System.out.println("invalid file");
        }
    }

    public Properties getProperties() {
        return properties;
    }

    private void initDefaultProperties() {
        properties = new Properties();
        properties.putAll(propertyMap);
    }
    // IOException

    private void initSpecificProperties(InputStream inputStream) {
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            // maybe message?
            initDefaultProperties();
        }
    }

    private boolean isPropertySet(String propertyName) {
        return Boolean.parseBoolean(properties.getProperty(propertyName));
    }
}
