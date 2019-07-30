package Branch;

import Drives.Drive;
import bg.sofia.uni.fmi.mjt.git.Commit;
import Drives.Stage;

import java.util.ArrayList;
import java.util.List;

public class Branch {
    private String name;
    private Commit currentCommit;
    private Stage stage;
    private Drive drive;
    private List<Commit> commits = new ArrayList<Commit>();

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
}
