package Drives;

import Files.File;

import java.util.ArrayList;
import java.util.List;

public class Drive {
    private List<File> drive = new ArrayList<>();

    public Drive() {

    }

    public Drive(Drive other) {
        for (File file: other.drive) {
            this.drive.add(new File(file));
        }
    }

    public List<File> getFilesInDrive() {
        return drive;
    }
}
