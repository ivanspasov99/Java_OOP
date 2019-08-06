package bg.sofia.uni.fmi.mjt.stylechecker;

public enum StyleProperties {
    WILDCARD_IMPORT("wildcard.import.check.active"),
    STATEMENTS_PER_LINE("statements.per.line.check.active"),
    OPENING_BRACKET("opening.bracket.check.active"),
    LENGTH_OF_LINE("length.of.line.check.active"),
    LINE_LENGTH_LIMIT("line.length.limit");

    private final String checkName;

    StyleProperties(String checkName) {
        this.checkName = checkName;
    }

    public String getCheckName() {
        return checkName;
    }
}
