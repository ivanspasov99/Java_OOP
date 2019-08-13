package bg.sofia.uni.fmi.mjt.movies.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Movie {
    private static final int TITLE_AND_YEAR_POSITION = 0;
    private static final int YEAR_AND_MONTH_POSITION = 1;
    private static final int YEAR_POSITION = 0;
    private static final int ACTORS_POSITION_STARTING = 1;
    private static final int ACTOR_FIRST_NAME = 1;
    private static final int ACTOR_LAST_NAME = 0;
    private String title;
    private int year;
    // could be int
    // better set if you use contains operation(constant)
    private Set<Actor> actorSet;

    private Movie(String title, int year, Collection<Actor> actorSet) {
        this.title = title;
        this.year = year;
        // i dont like it
        this.actorSet = (Set<Actor>) actorSet;
    }

    public static Movie createMovie(String line) {
        // tokens
        String[] parts = line.split("/");

        String title = parseTitle(parts[TITLE_AND_YEAR_POSITION]);
        int year = parseYear(parts[TITLE_AND_YEAR_POSITION]);
        Set<Actor> actorSet = parseActors(parts);

        return new Movie(title, year, actorSet);
    }

    private static String parseTitle(String str) {
        return str.substring(0, str.lastIndexOf(" (")).trim();
    }

    private static int parseYear(String str) {
        return Integer.parseInt(str.substring(str.lastIndexOf(" (")).replaceAll("[(]|[)]", "").split(",")[YEAR_POSITION].trim());
    }

    private static Set<Actor> parseActors(String[] parts) {
        Set<Actor> tempActorSet = new HashSet<>();
        for (int i = ACTORS_POSITION_STARTING; i < parts.length; i++) {
            String firstName;
            String lastName;
            if (parts[i].contains(",")) {
                String[] tokens = parts[i].split(", ");
                firstName = tokens[ACTOR_FIRST_NAME];
                lastName = tokens[ACTOR_LAST_NAME];
                tempActorSet.add(new Actor(firstName, lastName));
            } else {
                String nickName = parts[i];
                tempActorSet.add(new Actor(nickName));
            }
        }
        return tempActorSet;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public Collection<Actor> getActors() {
        return actorSet;
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        Movie movie = (Movie) obj;
        return title.equals(movie.getTitle());
    }
}
