package bg.sofia.uni.fmi.mjt.movies;

import bg.sofia.uni.fmi.mjt.movies.model.Actor;
import bg.sofia.uni.fmi.mjt.movies.model.Movie;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class MoviesExplorer {
    private ArrayList<Movie> movieList = new ArrayList<>();

    public MoviesExplorer(InputStream dataInput) {
        String line;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dataInput))) {
            while ((line = bufferedReader.readLine()) != null) {
                movieList.add(Movie.createMovie(line));
            }
        } catch (IOException IoEx) {
            throw new RuntimeException("Reading problem occurred");
        }
    }

    public Collection<Movie> getMovies() {
        return movieList;
    }

    public int countMoviesReleasedIn(int year) {
        // count returns long
        // not in favour of casting to int?????
        return (int) movieList.stream().filter((movie -> movie.getYear() == year)).count();
    }

    public Movie findFirstMovieWithTitle(String title) {
        Optional optional = movieList.stream().filter(movie -> movie.getTitle().contains(title)).findFirst();
        if (optional.isPresent()) {
            return (Movie) optional.get();
        }
        throw new IllegalArgumentException();
    }

    public Collection<Actor> getAllActors() {
        return movieList.stream().flatMap(movie -> movie.getActors().stream()).collect(Collectors.toSet());
    }

    public int getFirstYear() {
        // movie(s) released earliest
        // movieList is never empty
        return movieList.stream().mapToInt(Movie::getYear).min().getAsInt();

    }

    public Collection<Movie> getAllMoviesBy(Actor actor) {
        // if no movies: return empty collection
        return movieList.stream().filter(movie -> movie.getActors().contains(actor)).collect(Collectors.toList());
    }

    public Collection<Movie> getMoviesSortedByReleaseYear() {
        return movieList.stream().sorted(Comparator.comparing(Movie::getYear)).collect(Collectors.toList());
    }

    public int findYearWithLeastNumberOfReleasedMovies() {
        Map<Integer, Long> groupByYearMap = movieList.stream().collect(Collectors.groupingBy(Movie::getYear, Collectors.counting()));
        return groupByYearMap.entrySet().stream().min(Map.Entry.comparingByValue()).map(Map.Entry::getKey).get();
    }

    public Movie findMovieWithGreatestNumberOfActors() {
        return movieList.stream().sorted((m1, m2) -> m2.getActors().size() - m1.getActors().size()).findFirst().get();
    }
}
