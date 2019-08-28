import bg.sofia.uni.fmi.mjt.grep.CustomExceptions.NotValidInputException;
import bg.sofia.uni.fmi.mjt.grep.Main;
import bg.sofia.uni.fmi.mjt.grep.CustomExceptions.NoDirecrotyFound;
import bg.sofia.uni.fmi.mjt.grep.CustomExceptions.NoSuchCommandException;
import bg.sofia.uni.fmi.mjt.grep.CustomExceptions.NoWordFound;
import bg.sofia.uni.fmi.mjt.grep.NoThreadNumberException;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

public class MainTest {

    private static Main folderSearch;

    @Test(expected = NotValidInputException.class)
    public void testShouldReturnCommandExceptionType1() throws NoWordFound, NoSuchCommandException, NoDirecrotyFound, NotValidInputException, NoThreadNumberException {
        InputStream inputStream = new ByteArrayInputStream(("grep -wi".getBytes()));
        folderSearch = new Main(inputStream);
    }

    @Test(expected = NotValidInputException.class)
    public void testShouldReturnCommandExceptionType2() throws NoWordFound, NoSuchCommandException, NoDirecrotyFound, NotValidInputException, NoThreadNumberException {
        InputStream inputStream = new ByteArrayInputStream(("no such command".getBytes()));
        folderSearch = new Main(inputStream);
    }

    @Test(expected = NotValidInputException.class)
    public void testShouldReturnCommandExceptionType3() throws NoWordFound, NoSuchCommandException, NoDirecrotyFound, NotValidInputException, NoThreadNumberException {
        InputStream inputStream = new ByteArrayInputStream(("grep such command 12 asd !!! --".getBytes()));
        folderSearch = new Main(inputStream);
    }

    @Test(expected = NotValidInputException.class)
    public void testShouldReturnCommandExceptionType4() throws NoWordFound, NoSuchCommandException, NoDirecrotyFound, NotValidInputException, NoThreadNumberException {
        InputStream inputStream = new ByteArrayInputStream(("".getBytes()));
        folderSearch = new Main(inputStream);
    }

    @Test(expected = NoSuchCommandException.class)
    public void testShouldReturnCommandExceptionType5() throws NoWordFound, NoSuchCommandException, NoDirecrotyFound, NotValidInputException, NoThreadNumberException {
        InputStream inputStream = new ByteArrayInputStream(("baam foo /as 5 /3fqff".getBytes()));
        folderSearch = new Main(inputStream);
    }

    @Test(expected = NoSuchCommandException.class)
    public void testShouldReturnCommandExceptionType6() throws NoWordFound, NoSuchCommandException, NoDirecrotyFound, NotValidInputException, NoThreadNumberException {
        InputStream inputStream = new ByteArrayInputStream(("yes foo /bum 2".getBytes()));
        folderSearch = new Main(inputStream);
    }

    @Test(expected = NoWordFound.class)
    public void testShouldReturnWordException() throws NoWordFound, NoSuchCommandException, NoDirecrotyFound, NotValidInputException, NoThreadNumberException {
        InputStream inputStream = new ByteArrayInputStream(("grep -w /aqw /qwe".getBytes()));
        folderSearch = new Main(inputStream);
    }

    @Test(expected = NoDirecrotyFound.class)
    public void testShouldReturnDirectoryExceptionType1() throws NoWordFound, NoSuchCommandException, NoDirecrotyFound, NotValidInputException, NoThreadNumberException {
        InputStream inputStream = new ByteArrayInputStream(("grep -iw grep -wi /qwe".getBytes()));
        folderSearch = new Main(inputStream);
    }

    @Test(expected = NoDirecrotyFound.class)
    public void testShouldReturnDirectoryExceptionType2() throws NoWordFound, NoSuchCommandException, NoDirecrotyFound, NotValidInputException, NoThreadNumberException {
        InputStream inputStream = new ByteArrayInputStream(("grep word fakeDir 2".getBytes()));
        folderSearch = new Main(inputStream);
    }

    @Test(expected = NoThreadNumberException.class)
    public void testShouldReturnThreadExceptionType1() throws NotValidInputException, NoSuchCommandException, NoWordFound, NoDirecrotyFound, NoThreadNumberException {
        InputStream inputStream = new ByteArrayInputStream(("grep word /fakeDir /asd".getBytes()));
        folderSearch = new Main(inputStream);
    }

    // how to test the private method/fields??
}
