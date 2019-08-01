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

    // override because of contains function in repository class
    // files are same if name is same
    @Override
    public boolean equals(Object obj) {
        File otherFile = (File) obj;
        return this.fileName.equals(otherFile.getName());
    }

    // we need to make sure that hashcode will return same value for equal() objects
    @Override
    public int hashCode() {
        return fileName.hashCode();
    }
}
