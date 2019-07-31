package Drives;

import Files.File;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Stage {
    private List<File> filesStagedForCommit = new ArrayList<File>();
    private List<File> filesStagedForRemoval = new ArrayList<File>();

    public Stage() {

    }

    public Stage(Stage other) {
        // it will not work with collection because of limited capacity
        for (File file : other.filesStagedForCommit) {
            this.filesStagedForCommit.add(new File(file));
        }
        for (File file : other.filesStagedForRemoval) {
            this.filesStagedForRemoval.add(new File(file));
        }
    }

    public List<File> getFilesStagedForCommit() {
        return filesStagedForCommit;
    }

    public List<File> getFilesForRemoval() {
        return filesStagedForRemoval;
    }
}
