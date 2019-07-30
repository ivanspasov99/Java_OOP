package Drives;

import Files.File;

import java.util.ArrayList;
import java.util.List;

public class Stage {
    private List<File> filesStagedForCommit = new ArrayList<File>();
    private List<File> filesStagedForRemoval = new ArrayList<File>();


    public List<File> getFilesStagedForCommit() {
        return filesStagedForCommit;
    }

    public List<File> getFilesForRemoval() {
        return filesStagedForRemoval;
    }
}
