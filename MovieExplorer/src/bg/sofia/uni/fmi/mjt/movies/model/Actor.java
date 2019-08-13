package bg.sofia.uni.fmi.mjt.movies.model;

public class Actor {
    private String firstName;
    private String lastName;

    public Actor(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Actor(String nickName) {
        this.firstName = nickName;
        this.lastName = ""; // not sure what needs to happen here
    }
    @Override
    public boolean equals(Object obj) {
        Actor actor = (Actor) obj;
        return firstName.equals(actor.firstName) && lastName.equals(actor.lastName);
    }

    @Override
    public int hashCode() {
        return firstName.hashCode() + lastName.hashCode();
    }
}
