package Branch;

import Drives.Drive;
import Files.File;
import bg.sofia.uni.fmi.mjt.git.Commit;
import Drives.Stage;

import java.util.ArrayList;
import java.util.List;

public class Branch {
    private String name;
    private Commit currentCommit = null;
    private Stage stage;
    private Drive drive;
    private List<Commit> commits = new ArrayList<>();

    public Branch(Branch other, String name) {
        this.name = name;
        this.currentCommit = (other.currentCommit == null) ? null : new Commit(other.currentCommit);
        this.stage = new Stage(other.stage);
        this.drive = new Drive(other.drive);
        for (Commit commit: other.commits) {
            this.commits.add(new Commit(commit));
        }
    }

    public Branch(String name) {
        this.name = name;
        this.stage = new Stage();
        this.drive = new Drive();
    }

    public String getName() {
        return name;
    }

    public Commit getCurrentCommit() {
        return currentCommit;
    }

    public Stage getStage() {
        return stage;
    }

    public List<Commit> getCommits() {
        return commits;
    }

    public Drive getDrive() {
        return drive;
    }

    public void clearStage() {
        // GarbColl will do the rest
        this.stage = new Stage();
    }
    public void addCommit(Commit commit) {
        currentCommit = commit;
        commits.add(commit);
    }
    public void setCurrentCommit(Commit commit) {
        currentCommit = commit;
    }
}
