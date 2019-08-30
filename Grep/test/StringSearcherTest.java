import bg.sofia.uni.fmi.mjt.grep.CustomExceptions.EmptyFileException;
import bg.sofia.uni.fmi.mjt.grep.CustomExceptions.InvalidInputException;
import bg.sofia.uni.fmi.mjt.grep.CustomExceptions.NoSuchCommandException;
import bg.sofia.uni.fmi.mjt.grep.StringSearcher;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;

public class StringSearcherTest {

    private static StringSearcher stringSearcher;

    @Before
    public void setUp() {
        stringSearcher = new StringSearcher();
    }

    @Test(expected = EmptyFileException.class)
    public void testShouldThrowEmptyFileException() throws Throwable {
        InputStream inputStream = new ByteArrayInputStream("".getBytes());
        stringSearcher.stringSearch(inputStream);
    }

    @Test(expected = NoSuchCommandException.class)
    public void testShouldThrowNoSuchCommandExceptionExceptionType1() throws Throwable {
        InputStream inputStream = new ByteArrayInputStream("gre p stra / 123 `f5156f23".getBytes());
        stringSearcher.stringSearch(inputStream);
    }

    @Test(expected = NoSuchCommandException.class)
    public void testShouldThrowNoSuchCommandExceptionExceptionType2() throws Throwable {
        InputStream inputStream = new ByteArrayInputStream("       grep .".getBytes());
        stringSearcher.stringSearch(inputStream);
    }

    @Test(expected = NoSuchCommandException.class)
    public void testShouldThrowNoSuchCommandExceptionExceptionType3() throws Throwable {
        InputStream inputStream = new ByteArrayInputStream("grep123".getBytes());
        stringSearcher.stringSearch(inputStream);
    }

    @Test(expected = InvalidInputException.class)
    public void testShouldThrowInvalidInputExceptionType1() throws Throwable {
        InputStream inputStream = new ByteArrayInputStream("grep -i -w word directory".getBytes());
        stringSearcher.stringSearch(inputStream);
    }

    @Test(expected = InvalidInputException.class)
    public void testShouldThrowInvalidInputExceptionType2() throws Throwable {
        InputStream inputStream = new ByteArrayInputStream("grep word directory".getBytes());
        stringSearcher.stringSearch(inputStream);
    }

    @Test(expected = InvalidInputException.class)
    public void testShouldThrowInvalidInputExceptionType3() throws Throwable {
        InputStream inputStream = new ByteArrayInputStream("grep -i -w directory".getBytes());
        stringSearcher.stringSearch(inputStream);
    }

    @Test(expected = InvalidInputException.class)
    public void testShouldThrowInvalidInputExceptionType4() throws Throwable {
        InputStream inputStream = new ByteArrayInputStream("grep  directory".getBytes());
        stringSearcher.stringSearch(inputStream);
    }

    @Test(expected = InvalidInputException.class)
    public void testShouldThrowInvalidInputExceptionType5() throws Throwable {
        InputStream inputStream = new ByteArrayInputStream("grep -iw directory 2".getBytes());
        stringSearcher.stringSearch(inputStream);
    }

    @Test(expected = InvalidInputException.class)
    public void testShouldThrowInvalidInputExceptionType6() throws Throwable {
        InputStream inputStream = new ByteArrayInputStream("grep -wi directory 2 testing".getBytes());
        stringSearcher.stringSearch(inputStream);
    }

    @Test(expected = InvalidInputException.class)
    public void testShouldThrowInvalidInputExceptionType7() throws Throwable {
        InputStream inputStream = new ByteArrayInputStream("grep -i -w directory 2 testing".getBytes());
        stringSearcher.stringSearch(inputStream);
    }

    @Test
    public void testShouldReturnNoFiles() throws Throwable {
        String expected = "No Files to search!";
        InputStream inputStream = new ByteArrayInputStream("grep word C:\\\\Java\\Java_OOP\\Grep\\resources\\fol3 2".getBytes());
        assertNull(stringSearcher.stringSearch(inputStream));

    }

    // WHEN YOU READ THE STREAM YOUR FILE MARKER POSITION WILL BE AT THE END SO YOU CANNOT READ ONE FILES TWO TIMES
    @Test
    public void testShouldReturnTwoResultsWithNoOptions() throws Throwable {
        final int EXPECTED_LIST_SIZE = 1;
        InputStream inputStream = new ByteArrayInputStream("grep ivan C:\\\\Java\\Java_OOP\\Grep\\resources 2".getBytes());
        assertEquals(EXPECTED_LIST_SIZE, stringSearcher.stringSearch(inputStream).size());

    }

    @Test
    public void testShouldReturnNoResultsWithNoOptions() throws Throwable {
        final int EXPECTED_LIST_SIZE = 0;
        InputStream inputStream = new ByteArrayInputStream("grep IVAN C:\\\\Java\\Java_OOP\\Grep\\resources 2".getBytes());

        assertEquals(EXPECTED_LIST_SIZE, stringSearcher.stringSearch(inputStream).size());
    }

    @Test
    public void testShouldReturnResultsWithOneOption() throws Throwable {
        final int EXPECTED_LIST_SIZE = 2;
        InputStream inputStream = new ByteArrayInputStream("grep -i petar C:\\\\Java\\Java_OOP\\Grep\\resources 2".getBytes());
        assertEquals(EXPECTED_LIST_SIZE, stringSearcher.stringSearch(inputStream).size());
    }

    @Test
    public void testShouldReturnResultsWithTwoOption() throws Throwable {
        final int EXPECTED_LIST_SIZE = 2;
        InputStream inputStream = new ByteArrayInputStream("grep -iw petar C:\\\\Java\\Java_OOP\\Grep\\resources 2".getBytes());
        assertEquals(EXPECTED_LIST_SIZE, stringSearcher.stringSearch(inputStream).size());
    }

    @Test
    public void testShouldReturnNoResultsWithTwoOption() throws Throwable {
        final int EXPECTED_LIST_SIZE = 0;
        InputStream inputStream = new ByteArrayInputStream("grep -iw PetA C:\\\\Java\\Java_OOP\\Grep\\resources 2".getBytes());
        assertEquals(EXPECTED_LIST_SIZE, stringSearcher.stringSearch(inputStream).size());
    }

    @Test
    public void testShouldReturnNoResultsWithOneOption() throws Throwable {
        final int EXPECTED_LIST_SIZE = 0;
        InputStream inputStream = new ByteArrayInputStream("grep -w PetAr C:\\\\Java\\Java_OOP\\Grep\\resources 2".getBytes());
        assertEquals(EXPECTED_LIST_SIZE, stringSearcher.stringSearch(inputStream).size());
    }
    @Test
    public void testShouldReturnResultsWithOneOptionType2() throws Throwable {
        final int EXPECTED_LIST_SIZE = 4;
        InputStream inputStream = new ByteArrayInputStream("grep -i A C:\\\\Java\\Java_OOP\\Grep\\resources 2".getBytes());
        assertEquals(EXPECTED_LIST_SIZE, stringSearcher.stringSearch(inputStream).size());
    }
    @Test
    public void testShouldReturnResultsWithOneOptionType3() throws Throwable {
        final int EXPECTED_LIST_SIZE = 2;
        InputStream inputStream = new ByteArrayInputStream("grep -i K C:\\\\Java\\Java_OOP\\Grep\\resources 2".getBytes());
        assertEquals(EXPECTED_LIST_SIZE, stringSearcher.stringSearch(inputStream).size());
    }
    @Test
    public void testShouldReturnResultsWithTwoOptionType4() throws Throwable {
        final int EXPECTED_LIST_SIZE = 2;
        InputStream inputStream = new ByteArrayInputStream("grep -i -w kircho C:\\\\Java\\Java_OOP\\Grep\\resources 2".getBytes());
        assertEquals(EXPECTED_LIST_SIZE, stringSearcher.stringSearch(inputStream).size());
    }
    @Test
    public void testShouldReturnNoResultsWithOneOptionType4() throws Throwable {
        final int EXPECTED_LIST_SIZE = 0;
        InputStream inputStream = new ByteArrayInputStream("grep -w kiRcho C:\\\\Java\\Java_OOP\\Grep\\resources 2".getBytes());
        assertEquals(EXPECTED_LIST_SIZE, stringSearcher.stringSearch(inputStream).size());
    }
}
