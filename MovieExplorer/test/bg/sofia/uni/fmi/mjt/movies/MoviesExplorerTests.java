package bg.sofia.uni.fmi.mjt.movies;

import bg.sofia.uni.fmi.mjt.movies.model.Actor;
import bg.sofia.uni.fmi.mjt.movies.model.Movie;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MoviesExplorerTests {
    private static MoviesExplorer explorer;
    private static List<Movie> movieList;

    @Before
    public void setUp() {
        InputStream explorerStream = MoviesStreamInitializer.initMovieStream();
        explorer = new MoviesExplorer(explorerStream);

        //  You can wrap an InputStream and turn it into a Reader by using the InputStreamReader class
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(MoviesStreamInitializer.initMovieStream()));

        // or you can use Bytes
        //BufferedInputStream bufferedReader = new BufferedInputStream(MoviesStreamInitializer.initMovieStream());

        // lambda exp for map(movie->Movie.createMovie(movie))
        movieList = bufferedReader.lines().map(Movie::createMovie).collect(Collectors.toList());
    }

    @Test
    public void testShouldInitMovieExplorerCorrectly() {
        int movieListSize = movieList.size();
        int movieExplorerSize = explorer.getMovies().size();

        assertEquals(movieListSize, movieExplorerSize);
    }

    @Test
    public void testShouldReturnCorrectCountOfMovies() {
        final int RELEASE_YEAR = 2003;
        final int EXPECTED = 2;

        assertEquals(EXPECTED, explorer.countMoviesReleasedIn(RELEASE_YEAR));
    }

    @Test
    public void testShouldReturnCorrectMovieWithTitle() {
        final String TITLE = "Harry Potter and the Prisoner of ";
        final String expectedTitle = "Harry Potter and the Prisoner of Azkaban";

        assertEquals(expectedTitle, explorer.findFirstMovieWithTitle(TITLE).getTitle());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testShouldThrowExceptionForTitleIsNotFound() {
        final String TITLE = "123Har4ry Pot5ter and t67e Prison8er of ";
        explorer.findFirstMovieWithTitle(TITLE).getTitle();
    }

    @Test
    public void testShouldGetAllActors() {
        final int ACTORS_COUNT = 43;
        assertEquals(ACTORS_COUNT, explorer.getAllActors().size());
    }

    @Test
    public void testShouldGetMinYearOfRelease() {
        final int MIN_YEAR = 1978;
        assertEquals(MIN_YEAR, explorer.getFirstYear());
    }

    @Test
    public void testShouldNotReturnMovieWithActor() {
        final Actor ACTOR = new Actor("Ivan", "Spasov");

        assertTrue(explorer.getAllMoviesBy(ACTOR).isEmpty());
    }

    @Test
    public void testShouldReturnSortedAscendingYearCollection() {
        movieList.sort(Comparator.comparing(Movie::getYear));

        Collection<Movie> movies = explorer.getMoviesSortedByReleaseYear();

        assertTrue(movieList.containsAll(movies));
    }

    @Test
    public void testShouldReturnYearWithLeastNumberOfReleasedMovies() {
        List<Integer> expectedYears = Arrays.asList(1996, 2004);

        assertTrue(expectedYears.contains(explorer.findYearWithLeastNumberOfReleasedMovies()));
    }

    @Test
    public void testMovieWithGreatestNumberOfActors() {
        List<String> expectedMovies = Arrays.asList("Lord of the Rings: The Fellowship of the Ring, The",
                "Lord of the Rings: The Return of the King, The", "Lord of the Rings: The Two Towers, The");

        assertTrue(expectedMovies.contains(explorer.findMovieWithGreatestNumberOfActors().getTitle()));
    }
}
