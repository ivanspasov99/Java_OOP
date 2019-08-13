package bg.sofia.uni.fmi.mjt.stylechecker;

import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

// INPUT STREAM IS WITH CORRECT KEYS AND VALUES
public class StyleCheckerTest {

    private static final String WILDCARD_IMPORT_CHECK = "wildcard.import.check.active";
    private static final String STATEMENTS_PER_LINE_CHECK = "statements.per.line.check.active";
    private static final String LENGTH_OF_LINE_CHECK = "length.of.line.check.active";
    private static final String OPENING_BRACKET_CHECK = "opening.bracket.check.active";
    private static final boolean DEFAULT_VALUE = true;
    private static String numOfChar;

    private static final String WILD_CARD_MESSAGE = "// FIXME Wildcards are not allowed in import statements" + System.lineSeparator() + "";
    private static final String STATEMENTS_PER_LINE_MESSAGE = "// FIXME Only one statement per line is allowed" + System.lineSeparator() + "";
    private static final String LENGTH_LINE_MESSAGE = "// FIXME Length of line should not exceed %s characters" + System.lineSeparator() + "";
    private static final String OPENING_BRACKET_MESSAGE = "// FIXME Opening brackets should be placed on the same line as the declaration" + System.lineSeparator() + "";


    private static final String LINE_LENGTH_LIMIT = "line.length.limit";
    private static final String DEFAULT_MAX_LENGTH_LIMIT = "100";

    private static final int PROPERTY_SIZE = 5;

    private static StyleChecker styleChecker;

    private static InputStream configPropertiesMixed;
    private static InputStream configPartOfProperties;
    private static InputStream allPropertiesOff;

    private static OutputStream outputStreamJavaCode;

    @Before
    public void setUp() {
        // main configs
        styleChecker = new StyleChecker();
        // we can make file to test or with ByteArray and inline String
        configPropertiesMixed = new ByteArrayInputStream(("wildcard.import.check.active=true" + System.lineSeparator() +
                "statements.per.line.check.active=false" + System.lineSeparator() +
                "length.of.line.check.active=true" + System.lineSeparator() +
                "opening.bracket.check.active=false" + System.lineSeparator() +
                "line.length.limit=60" + System.lineSeparator()).getBytes());

        configPartOfProperties = new ByteArrayInputStream(
                ("statements.per.line.check.active=true" + System.lineSeparator() +
                        "length.of.line.check.active=false" + System.lineSeparator() +
                        "line.length.limit=10" + System.lineSeparator()).getBytes());

        allPropertiesOff = new ByteArrayInputStream(("wildcard.import.check.active=false" + System.lineSeparator()
                + "statements.per.line.check.active=false" + System.lineSeparator()
                + "length.of.line.check.active=false" + System.lineSeparator()
                + "opening.bracket.check.active=false").getBytes());

        outputStreamJavaCode = new ByteArrayOutputStream();

    }

    @Test
    public void testShouldGetPropertiesCorrectValuesFromEnum() {
        assertEquals(WILDCARD_IMPORT_CHECK, StyleProperties.WILDCARD_IMPORT.getCheckName());
        assertEquals(STATEMENTS_PER_LINE_CHECK, StyleProperties.STATEMENTS_PER_LINE.getCheckName());
        assertEquals(LENGTH_OF_LINE_CHECK, StyleProperties.LENGTH_OF_LINE.getCheckName());
        assertEquals(OPENING_BRACKET_CHECK, StyleProperties.OPENING_BRACKET.getCheckName());
        assertEquals(LINE_LENGTH_LIMIT, StyleProperties.LINE_LENGTH_LIMIT.getCheckName());
    }

    @Test
    public void testShouldReturnCorrectSizeWhenInitializeProperties() {
        styleChecker = new StyleChecker();
        assertEquals(PROPERTY_SIZE, styleChecker.getProperties().size());
    }

    @Test
    public void testShouldSetCorrectKeyOnProperties() {
        styleChecker = new StyleChecker();
        assertTrue(styleChecker.getProperties().containsKey(WILDCARD_IMPORT_CHECK));
        assertTrue(styleChecker.getProperties().containsKey(STATEMENTS_PER_LINE_CHECK));
        assertTrue(styleChecker.getProperties().containsKey(LENGTH_OF_LINE_CHECK));
        assertTrue(styleChecker.getProperties().containsKey(OPENING_BRACKET_CHECK));
        assertTrue(styleChecker.getProperties().containsKey(LINE_LENGTH_LIMIT));
    }

    @Test
    public void testShouldSetCorrectValueOnProperties() {
        styleChecker = new StyleChecker();
        assertEquals(DEFAULT_VALUE, Boolean.parseBoolean(styleChecker.getProperties().get(WILDCARD_IMPORT_CHECK).toString()));
        assertEquals(DEFAULT_VALUE, Boolean.parseBoolean(styleChecker.getProperties().get(STATEMENTS_PER_LINE_CHECK).toString()));
        assertEquals(DEFAULT_VALUE, Boolean.parseBoolean(styleChecker.getProperties().get(LENGTH_OF_LINE_CHECK).toString()));
        assertEquals(DEFAULT_VALUE, Boolean.parseBoolean(styleChecker.getProperties().get(OPENING_BRACKET_CHECK).toString()));
        assertEquals(DEFAULT_MAX_LENGTH_LIMIT, styleChecker.getProperties().get(LINE_LENGTH_LIMIT));
    }

    @Test
    public void testShouldReturnCorrectSizeWhenStreamInitializeProperties() {
        styleChecker = new StyleChecker(configPropertiesMixed);
        assertEquals(PROPERTY_SIZE, styleChecker.getProperties().size());
    }

    @Test
    public void testShouldSetCorrectValueOnStreamProperties() {
        styleChecker = new StyleChecker(configPropertiesMixed);
        assertTrue(Boolean.parseBoolean(styleChecker.getProperties().get(WILDCARD_IMPORT_CHECK).toString()));
        assertFalse(Boolean.parseBoolean(styleChecker.getProperties().get(STATEMENTS_PER_LINE_CHECK).toString()));
        assertTrue(Boolean.parseBoolean(styleChecker.getProperties().get(LENGTH_OF_LINE_CHECK).toString()));
        assertFalse(Boolean.parseBoolean(styleChecker.getProperties().get(OPENING_BRACKET_CHECK).toString()));
        assertEquals(60, Integer.parseInt(styleChecker.getProperties().get(LINE_LENGTH_LIMIT).toString()));
    }

    @Test
    public void testShouldOverwritePropertiesEntry() {
        styleChecker = new StyleChecker(configPartOfProperties);
        assertTrue(Boolean.parseBoolean(styleChecker.getProperties().get(WILDCARD_IMPORT_CHECK).toString()));
        assertTrue(Boolean.parseBoolean(styleChecker.getProperties().get(STATEMENTS_PER_LINE_CHECK).toString()));
        assertFalse(Boolean.parseBoolean(styleChecker.getProperties().get(LENGTH_OF_LINE_CHECK).toString()));
        assertTrue(Boolean.parseBoolean(styleChecker.getProperties().get(OPENING_BRACKET_CHECK).toString()));
        assertEquals(10, Integer.parseInt(styleChecker.getProperties().get(LINE_LENGTH_LIMIT).toString()));
    }

    @Test
    public void testShouldOverwriteAllFalsePropertiesEntry() {
        styleChecker = new StyleChecker(allPropertiesOff);
        assertFalse(Boolean.parseBoolean(styleChecker.getProperties().get(WILDCARD_IMPORT_CHECK).toString()));
        assertFalse(Boolean.parseBoolean(styleChecker.getProperties().get(STATEMENTS_PER_LINE_CHECK).toString()));
        assertFalse(Boolean.parseBoolean(styleChecker.getProperties().get(LENGTH_OF_LINE_CHECK).toString()));
        assertFalse(Boolean.parseBoolean(styleChecker.getProperties().get(OPENING_BRACKET_CHECK).toString()));
        assertEquals(100, Integer.parseInt(styleChecker.getProperties().get(LINE_LENGTH_LIMIT).toString()));
    }

    @Test
    public void testShouldPutWarningCommentForWildCardImport() {
        String javaCode = "import     java.util.*;";
        InputStream wildCardImport = new ByteArrayInputStream(javaCode.getBytes());

        styleChecker = new StyleChecker();
        styleChecker.checkStyle(wildCardImport, outputStreamJavaCode);

        String expected = WILD_CARD_MESSAGE + javaCode + System.lineSeparator();

        assertEquals(expected, outputStreamJavaCode.toString());
    }

    @Test
    public void testShouldNotPutWarningCommentForWildCardImport() {
        String javaCode = "import     java.util.*;";
        InputStream wildCardImport = new ByteArrayInputStream(javaCode.getBytes());

        styleChecker = new StyleChecker(allPropertiesOff);
        styleChecker.checkStyle(wildCardImport, outputStreamJavaCode);

        String expected = javaCode + System.lineSeparator();

        assertEquals(expected, outputStreamJavaCode.toString());
    }

    @Test
    public void testShouldPutWarningCommentForStatementPerLine() {
        String javaCode = "method();;    do();;";
        InputStream statementsPerLine = new ByteArrayInputStream(javaCode.getBytes());

        styleChecker = new StyleChecker();
        styleChecker.checkStyle(statementsPerLine, outputStreamJavaCode);

        String expected = STATEMENTS_PER_LINE_MESSAGE + javaCode + System.lineSeparator();

        assertEquals(expected, outputStreamJavaCode.toString());
    }

    @Test
    public void testShouldNotPutWarningCommentForStatementPerLine() {
        String javaCode = "ivan();;;    do();;";
        InputStream statementsPerLine = new ByteArrayInputStream(javaCode.getBytes());

        styleChecker = new StyleChecker(allPropertiesOff);
        styleChecker.checkStyle(statementsPerLine, outputStreamJavaCode);

        String expected = javaCode + System.lineSeparator();

        assertEquals(expected, outputStreamJavaCode.toString());
    }

    @Test
    public void testShouldNotPutWarningCommentForStatementPerLineMultipleSemicolon() {
        String javaCode = "ivan();;;;;;";
        InputStream statementsPerLine = new ByteArrayInputStream(javaCode.getBytes());

        styleChecker = new StyleChecker();
        styleChecker.checkStyle(statementsPerLine, outputStreamJavaCode);

        String expected = javaCode + System.lineSeparator();

        assertEquals(expected, outputStreamJavaCode.toString());
    }
    @Test
    public void testShouldPutWarningMessageForLengthLine() {
        String javaCode = "asdasdasdadasdasdasdasdasdasdasdlpasdkpoasdfp0isjdfpiajspfiajwpfaj9edrfasdadasdasdasdadasdadaaaadadaaadadqweqweqweqeqeqweqweqwe";
        InputStream byteJavaCode = new ByteArrayInputStream(javaCode.getBytes());

        styleChecker = new StyleChecker();
        numOfChar = styleChecker.getProperties().getProperty(StyleProperties.LINE_LENGTH_LIMIT.getCheckName());

        styleChecker.checkStyle(byteJavaCode, outputStreamJavaCode);

        String expected = String.format(LENGTH_LINE_MESSAGE, numOfChar) + javaCode + System.lineSeparator();

        assertEquals(expected, outputStreamJavaCode.toString());
    }

    @Test
    public void testShouldNotPutWarningMessageForLengthLine() {
        String javaCode = "asdasdasdadasdasdasdasdasdasdasdlpasdkpoasdfp0isjdfpiajspfiajwpfaj9edrfasdadasdasdasdadasdadaaaadadaaadadqweqweqweqeqeqweqweqwe";
        InputStream byteJavaCode = new ByteArrayInputStream(javaCode.getBytes());

        styleChecker = new StyleChecker(allPropertiesOff);
        numOfChar = styleChecker.getProperties().getProperty(StyleProperties.LINE_LENGTH_LIMIT.getCheckName());

        styleChecker.checkStyle(byteJavaCode, outputStreamJavaCode);

        String expected = javaCode + System.lineSeparator();

        assertEquals(expected, outputStreamJavaCode.toString());
    }

    @Test
    public void testShouldNotPutWarning() {
        String javaCode = "var++;" + System.lineSeparator() + "var++;";
        InputStream byteJavaCode = new ByteArrayInputStream(javaCode.getBytes());

        styleChecker = new StyleChecker(configPropertiesMixed);
        styleChecker.checkStyle(byteJavaCode, outputStreamJavaCode);

        String expected = "var++;" + System.lineSeparator() + "var++;" + System.lineSeparator();

        assertEquals(expected, outputStreamJavaCode.toString());
    }

    @Test
    public void testShouldPutWarningForOpeningBracket() {
        String javaCode = "{";

        InputStream byteJavaCode = new ByteArrayInputStream(javaCode.getBytes());

        styleChecker = new StyleChecker();
        styleChecker.checkStyle(byteJavaCode, outputStreamJavaCode);

        String expected = OPENING_BRACKET_MESSAGE + javaCode + System.lineSeparator();

        assertEquals(expected, outputStreamJavaCode.toString());
    }

    @Test
    public void testShouldNotPutWarningForOpeningBracket() {
        String javaCode = "{ asdasd" + System.lineSeparator() + "{";

        InputStream byteJavaCode = new ByteArrayInputStream(javaCode.getBytes());

        styleChecker = new StyleChecker(allPropertiesOff);
        styleChecker.checkStyle(byteJavaCode, outputStreamJavaCode);

        String expected = javaCode + System.lineSeparator();

        assertEquals(expected, outputStreamJavaCode.toString());
    }

    @Test
    public void testShouldPutWarningForOpeningBracketWithFakeText() {
        String javaCode = "{ asdasd";

        InputStream byteJavaCode = new ByteArrayInputStream(javaCode.getBytes());

        styleChecker = new StyleChecker();
        styleChecker.checkStyle(byteJavaCode, outputStreamJavaCode);

        String expected = OPENING_BRACKET_MESSAGE + javaCode + System.lineSeparator();

        assertEquals(expected, outputStreamJavaCode.toString());
    }

    @Test
    public void testShouldNotPutStatementsPerLineWarningItIsFalse() {
        String javaCode = "var++; get++;" + System.lineSeparator() + "" +
                "" + System.lineSeparator() + "" +
                "" + System.lineSeparator() + "" +
                "var++";
        InputStream byteJavaCode = new ByteArrayInputStream(javaCode.getBytes());

        styleChecker = new StyleChecker(configPropertiesMixed);
        styleChecker.checkStyle(byteJavaCode, outputStreamJavaCode);

        String expected = javaCode + System.lineSeparator();

        assertEquals(expected, outputStreamJavaCode.toString());
    }

    @Test
    public void testShouldNotPutOpeningBracketWarningItIsFalse() {
        String javaCode = "var++; get++;" + System.lineSeparator() + "" +
                "{" + System.lineSeparator() + "" +
                "};" + System.lineSeparator() + "" +
                "var++";
        InputStream byteJavaCode = new ByteArrayInputStream(javaCode.getBytes());

        styleChecker = new StyleChecker(configPropertiesMixed);
        styleChecker.checkStyle(byteJavaCode, outputStreamJavaCode);

        String expected = javaCode + System.lineSeparator();

        assertEquals(expected, outputStreamJavaCode.toString());
    }

    @Test
    public void testShouldNotPutLineCheckWarningItItFalse() {
        String javaCode = "var++; get++; asdasdasdasdadasdasdasdasd" + System.lineSeparator() + "" +
                "{" + System.lineSeparator() + "" +
                "};" + System.lineSeparator() + "" +
                "var++";
        InputStream byteJavaCode = new ByteArrayInputStream(javaCode.getBytes());

        styleChecker = new StyleChecker(configPropertiesMixed);
        styleChecker.checkStyle(byteJavaCode, outputStreamJavaCode);

        String expected = javaCode + System.lineSeparator();

        assertEquals(expected, outputStreamJavaCode.toString());
    }

    @Test
    public void testShouldPutMultipleLineLengthCheckWarning() {
        InputStream onlyLineCheckTrue = new ByteArrayInputStream(("wildcard.import.check.active=false" + System.lineSeparator() +
                "statements.per.line.check.active=false" + System.lineSeparator() +
                "length.of.line.check.active=true" + System.lineSeparator() +
                "opening.bracket.check.active=false" + System.lineSeparator() +
                "line.length.limit=10" + System.lineSeparator()).getBytes());

        styleChecker = new StyleChecker(onlyLineCheckTrue);
        numOfChar = styleChecker.getProperties().getProperty(StyleProperties.LINE_LENGTH_LIMIT.getCheckName());

        // not valid import statement
        String javaCode = "ivan++; get++; asdasdasdasdadasdasdasdasd" + System.lineSeparator() +
                "{" + System.lineSeparator() +
                "};" + System.lineSeparator() +
                "ivan++" + System.lineSeparator() +
                "import.*****************************" + System.lineSeparator() +
                "*+";

        String expected = String.format(LENGTH_LINE_MESSAGE, numOfChar) + "ivan++; get++; asdasdasdasdadasdasdasdasd" + System.lineSeparator() + "" +
                "{" + System.lineSeparator() + "" +
                "};" + System.lineSeparator() + "" +
                "ivan++" + System.lineSeparator() + "" + String.format(LENGTH_LINE_MESSAGE, numOfChar) +
                "import.*****************************" + System.lineSeparator() +
                "*+" + System.lineSeparator();

        InputStream byteJavaCode = new ByteArrayInputStream(javaCode.getBytes());

        styleChecker.checkStyle(byteJavaCode, outputStreamJavaCode);

        assertEquals(expected, outputStreamJavaCode.toString());
    }

    @Test
    public void testShouldNotPutAnyWarning() {
        styleChecker = new StyleChecker(allPropertiesOff);
        numOfChar = styleChecker.getProperties().getProperty(StyleProperties.LINE_LENGTH_LIMIT.getCheckName());

        String javaCode = "ivan++; get++; asdasdasdasdadasdasdasdasd" + System.lineSeparator() +
                "{" + System.lineSeparator() +
                "};" + System.lineSeparator() +
                "ivan++" + System.lineSeparator() +
                "import.*****************************" + System.lineSeparator() +
                "*+";

        String expected = "ivan++; get++; asdasdasdasdadasdasdasdasd" + System.lineSeparator() + "" +
                "{" + System.lineSeparator() + "" +
                "};" + System.lineSeparator() + "" +
                "ivan++" + System.lineSeparator() + "" +
                "import.*****************************" + System.lineSeparator() + "" +
                "*+" + System.lineSeparator();

        InputStream byteJavaCode = new ByteArrayInputStream(javaCode.getBytes());

        styleChecker.checkStyle(byteJavaCode, outputStreamJavaCode);

        assertEquals(expected, outputStreamJavaCode.toString());
    }

    @Test
    public void testShouldReturnEmptyString() {
        styleChecker = new StyleChecker(allPropertiesOff);
        numOfChar = styleChecker.getProperties().getProperty(StyleProperties.LINE_LENGTH_LIMIT.getCheckName());

        String javaCode = "";
        String expected = javaCode;


        InputStream byteJavaCode = new ByteArrayInputStream(javaCode.getBytes());

        styleChecker.checkStyle(byteJavaCode, outputStreamJavaCode);

        assertEquals(expected, outputStreamJavaCode.toString());
    }

    @Test
    public void testShouldPutTwoWarningOnLine() {
        styleChecker = new StyleChecker();
        numOfChar = styleChecker.getProperties().getProperty(StyleProperties.LINE_LENGTH_LIMIT.getCheckName());

        String javaCode = "sayHello();sayHello();sayHello();sayHello();sayHello();sayHello();sayHello();sayHello();sayHello();sayHello();sayHello()";
        String expected = STATEMENTS_PER_LINE_MESSAGE + String.format(LENGTH_LINE_MESSAGE, numOfChar) + javaCode + System.lineSeparator();

        InputStream byteJavaCode = new ByteArrayInputStream(javaCode.getBytes());

        styleChecker.checkStyle(byteJavaCode, outputStreamJavaCode);

        assertEquals(expected, outputStreamJavaCode.toString());
    }

    @Test(expected = IOException.class)
    public void testShouldThrowException() throws IOException {
        // File tempFile = File.createTempFile("fileNotFound", "txt");
        final InputStream fileNotFoundInput = new FileInputStream("fileNotFound");
        final OutputStream fileNotFoundOutput = new FileOutputStream("OutputNotFound");
        styleChecker = new StyleChecker(fileNotFoundInput);
        styleChecker.checkStyle(fileNotFoundInput, fileNotFoundOutput);
    }
}
