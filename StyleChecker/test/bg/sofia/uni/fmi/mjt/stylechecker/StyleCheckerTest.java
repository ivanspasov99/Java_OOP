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
    private static final String WildCardMessage = "// FIXME Wildcards are not allowed in import statements\r\n";
    private static final String StatementsPerLineMessage = "// FIXME Only one statement per line is allowed\r\n";
    private static final String LengthLineMessage = "// FIXME Length of line should not exceed %s characters\r\n";
    private static final String OpeningBracketsMessage = "// FIXME Opening brackets should be placed on the same line as the declaration\r\n";


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
        configPropertiesMixed = new ByteArrayInputStream(("wildcard.import.check.active=true\n" +
                "statements.per.line.check.active=false\n" +
                "length.of.line.check.active=true\n" +
                "opening.bracket.check.active=false\n" +
                "line.length.limit=60\n").getBytes());

        configPartOfProperties = new ByteArrayInputStream(
                ("statements.per.line.check.active=true\n" +
                        "length.of.line.check.active=false\n" +
                        "line.length.limit=10\n").getBytes());

        allPropertiesOff = new ByteArrayInputStream(("wildcard.import.check.active=false\n"
                + "statements.per.line.check.active=false\n"
                + "line.length.limit=100\n"
                + "length.of.line.check.active=false\n"
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

    /*
    @Test(expected = IOException.class)
     public void testShouldThrowIOException() {
         styleChecker = new StyleChecker(inputThrowException);
     }
     */
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
    public void testShouldPutWarningCommentForWildCardImport() {
        String javaCode = "   import     java.util.*;\r\n";
        InputStream wildCardImport = new ByteArrayInputStream(javaCode.getBytes());

        styleChecker = new StyleChecker();
        styleChecker.checkStyle(wildCardImport, outputStreamJavaCode);

        String expected = WildCardMessage + javaCode;

        assertEquals(expected, outputStreamJavaCode.toString());
    }

    @Test
    public void testShouldPutWarningCommentForStatementPerLine() {
        String javaCode = "    ivan();;;    do();;    \r\n";
        InputStream statementsPerLine = new ByteArrayInputStream(javaCode.getBytes());

        styleChecker = new StyleChecker();
        styleChecker.checkStyle(statementsPerLine, outputStreamJavaCode);

        String expected = StatementsPerLineMessage + javaCode;

        assertEquals(expected, outputStreamJavaCode.toString());
    }

    @Test
    public void testShouldPutWarningCommentForOpeningBracket() {
        String javaCode = "   {     \r\n";
        InputStream openingBracket = new ByteArrayInputStream(javaCode.getBytes());

        styleChecker = new StyleChecker();
        styleChecker.checkStyle(openingBracket, outputStreamJavaCode);

        String expected = OpeningBracketsMessage + javaCode;

        assertEquals(expected, outputStreamJavaCode.toString());
    }

    @Test
    public void testShouldPutWarningMessageForLengthLine() {
        String javaCode = " asdasdasdadasdasdasdasdasdasdasdlpasdkpoasdfp0isjdfpiajspfiajwpfaj9edrfasdadasdasdasdadasdadaaaadadaaadadqweqweqweqeqeqweqweqwe \r\n";
        InputStream lineLength = new ByteArrayInputStream(javaCode.getBytes());

        styleChecker = new StyleChecker();
        numOfChar = styleChecker.getProperties().getProperty(StyleProperties.LINE_LENGTH_LIMIT.getCheckName());

        styleChecker.checkStyle(lineLength, outputStreamJavaCode);

        String expected = String.format(LengthLineMessage, numOfChar) + javaCode;

        assertEquals(expected, outputStreamJavaCode.toString());
    }

    // TEST WITH MIXED STYLE CHECKER INPUT
    @Test
    public void testShouldNotPutWarning() {
        String javaCode = "ivan++     \r\n" +
                "    \r\n" +
                " \r\n" +
                "ivan++\r\n";
        InputStream lineLength = new ByteArrayInputStream(javaCode.getBytes());

        styleChecker = new StyleChecker(configPropertiesMixed);
        styleChecker.checkStyle(lineLength, outputStreamJavaCode);

        String expected = javaCode;

        assertEquals(expected, outputStreamJavaCode.toString());
    }

    @Test
    public void testShouldNotPutStatementsPerLineWarningItIsFalse() {
        String javaCode = "ivan++; get++;\r\n" +
                "    \r\n" +
                " \r\n" +
                "ivan++\r\n";
        InputStream lineLength = new ByteArrayInputStream(javaCode.getBytes());

        styleChecker = new StyleChecker(configPropertiesMixed);
        styleChecker.checkStyle(lineLength, outputStreamJavaCode);

        String expected = javaCode;

        assertEquals(expected, outputStreamJavaCode.toString());
    }

    @Test
    public void testShouldNotPutOpeningBracketWarningItIsFalse() {
        String javaCode = "ivan++; get++;\r\n" +
                " { \r\n" +
                " };\r\n" +
                "ivan++\r\n";
        InputStream lineLength = new ByteArrayInputStream(javaCode.getBytes());

        styleChecker = new StyleChecker(configPropertiesMixed);
        styleChecker.checkStyle(lineLength, outputStreamJavaCode);

        String expected = javaCode;

        assertEquals(expected, outputStreamJavaCode.toString());
    }

    @Test
    public void testShouldNotPutLineCheckWarningItItFalse() {
        String javaCode = "ivan++; get++; asdasdasdasdadasdasdasdasd\r\n" +
                " { \r\n" +
                " };\r\n" +
                "ivan++\r\n";
        InputStream lineLength = new ByteArrayInputStream(javaCode.getBytes());

        styleChecker = new StyleChecker(configPropertiesMixed);
        styleChecker.checkStyle(lineLength, outputStreamJavaCode);

        String expected = javaCode;

        assertEquals(expected, outputStreamJavaCode.toString());
    }
    @Test
    public void testShouldPutMiltipleLineLengthCheckWarning() {
        InputStream onlyLineCheckTrue = new ByteArrayInputStream(("wildcard.import.check.active=false\n" +
                "statements.per.line.check.active=false\n" +
                "length.of.line.check.active=true\n" +
                "opening.bracket.check.active=false\n" +
                "line.length.limit=10\n").getBytes());

        styleChecker = new StyleChecker(onlyLineCheckTrue);
        numOfChar = styleChecker.getProperties().getProperty(StyleProperties.LINE_LENGTH_LIMIT.getCheckName());

        String javaCode = "ivan++; get++; asdasdasdasdadasdasdasdasd\n" +
                " {\n" +
                " };\n" +
                "ivan++\n" +
                "import.*****************************\n"+
                "*+\n";

        String expected = String.format(LengthLineMessage, numOfChar) + "ivan++; get++; asdasdasdasdadasdasdasdasd\r\n" +
                " {\r\n" +
                " };\r\n" +
                "ivan++\r\n" +
                "import.*****************************\r\n"+
                "*+\r\n";

        InputStream lineLength = new ByteArrayInputStream(javaCode.getBytes());

        styleChecker.checkStyle(lineLength, outputStreamJavaCode);

        assertEquals(expected, outputStreamJavaCode.toString());
    }
    @Test
    public void testShouldNotPutAnyWarning() {
        styleChecker = new StyleChecker(allPropertiesOff);
        numOfChar = styleChecker.getProperties().getProperty(StyleProperties.LINE_LENGTH_LIMIT.getCheckName());

        String javaCode = "ivan++; get++; asdasdasdasdadasdasdasdasd\n" +
                " {\n" +
                " };\n" +
                "ivan++\n" +
                "import.*****************************\n" +
                "*+\n";

        String expected = "ivan++; get++; asdasdasdasdadasdasdasdasd\r\n" +
                " {\r\n" +
                " };\r\n" +
                "ivan++\r\n" +
                "import.*****************************\r\n" +
                "*+\r\n";

        InputStream lineLength = new ByteArrayInputStream(javaCode.getBytes());

        styleChecker.checkStyle(lineLength, outputStreamJavaCode);

        assertEquals(expected, outputStreamJavaCode.toString());
    }
}
