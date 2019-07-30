package Drives;

import Files.File;

import java.util.ArrayList;
import java.util.List;

public class Drive {
    private List<File> drive = new ArrayList<>();

    public List<File> getFilesInDrive() {
        return drive;
    }
}
