import bg.sofia.uni.fmi.mjt.stylechecker.StyleChecker;
import bg.sofia.uni.fmi.mjt.stylechecker.StyleProperties;
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

    private static final String WildCardMessage = "// FIXME Wildcards are not allowed in import statements\n";
    private static final String StatementsPerLineMessage = "// FIXME Only one statement per line is allowed\n";
    private static final String LengthLineMessage = "// FIXME Length of line should not exceed [X] characters\n";
    private static final String OpeningBracketsMessage = "// FIXME Opening brackets should be placed on the same line as the declaration\n";


    private static final String LINE_LENGTH_LIMIT = "line.length.limit";
    private static final String DEFAULT_MAX_LENGTH_LIMIT = "100";

    private static final int PROPERTY_SIZE = 5;

    private static StyleChecker styleChecker;

    private static InputStream configPropertiesMixed;
    private static InputStream configPartOfProperties;

    private static OutputStream outputStreamJavaCode;

    @Before
    public void setUp() {

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
                        "line.length.limit=60\n").getBytes());

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
        assertEquals(60, Integer.parseInt(styleChecker.getProperties().get(LINE_LENGTH_LIMIT).toString()));
    }

    @Test
    public void testShouldPutWarningCommentForWildCardImport() {
        String javaCode = "   import     java.util.*;"     ;
        InputStream wildCardImport = new ByteArrayInputStream(javaCode.getBytes());

        styleChecker = new StyleChecker();
        styleChecker.checkStyle(wildCardImport, outputStreamJavaCode);

        String expected = WildCardMessage + javaCode;

        assertEquals(expected, outputStreamJavaCode.toString());
    }

    @Test
    public void testShouldPutWarningCommentForStatementPerLine() {
        String javaCode = "    ivan();;;    do();;    ";
        InputStream statementsPerLine = new ByteArrayInputStream(javaCode.getBytes());

        styleChecker = new StyleChecker();
        styleChecker.checkStyle(statementsPerLine, outputStreamJavaCode);

        String expected = StatementsPerLineMessage + javaCode;

        assertEquals(expected, outputStreamJavaCode.toString());
    }
    @Test
    public void testShouldPutWarningCommentForOpeningBracket() {
        String javaCode = "   {     ";
        InputStream openingBracket = new ByteArrayInputStream(javaCode.getBytes());

        styleChecker = new StyleChecker();
        styleChecker.checkStyle(openingBracket, outputStreamJavaCode);

        String expected = OpeningBracketsMessage + javaCode;

        assertEquals(expected, outputStreamJavaCode.toString());
    }
    @Test
    public void testShouldPutWarningMessageForLengthLine(){
        String javaCode = " asdasdasdadasdasdasdasdasdasdasdlpasdkpoasdfp0isjdfpiajspfiajwpfaj9edrfasdadasdasdasdadasdadaaaadadaaadadqweqweqweqeqeqweqweqwe ";
        InputStream lineLength = new ByteArrayInputStream(javaCode.getBytes());

        styleChecker = new StyleChecker();
        styleChecker.checkStyle(lineLength, outputStreamJavaCode);

        String expected = LengthLineMessage + javaCode;

        assertEquals(expected, outputStreamJavaCode.toString());
    }
}
