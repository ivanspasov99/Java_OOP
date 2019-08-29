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

    /*@Test
    public void testShouldReturnNoFiles() throws Throwable {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        PrintStream originalErr = System.err;

        String expected = "No Files to search!";
        InputStream inputStream = new ByteArrayInputStream("grep word C:\\\\Java\\Java_OOP\\Grep\\resources\\fol3 2".getBytes());
        stringSearcher.stringSearch(inputStream);

    }*/

    @Test
    public void testShouldReturnNoFiles() throws Throwable {
        InputStream inputStream = new ByteArrayInputStream("grep ivan C:\\\\Java\\Java_OOP\\Grep\\resources 2".getBytes());
        System.out.println(stringSearcher.stringSearch(inputStream));
    }
}
