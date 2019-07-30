package Files;

public class File {
    private String fileName;

    public File(String fileName) {
        this.fileName = fileName;
    }

    public File(File other) {
        this.fileName = other.fileName;
    }

    public String getName() {
        return fileName;
    }

    // override because of contains in repo
    // files are same if name is same
    @Override
    public boolean equals(Object obj) {
        File otherFile = (File) obj;
        return this.fileName.equals(otherFile.getName());
    }
}
