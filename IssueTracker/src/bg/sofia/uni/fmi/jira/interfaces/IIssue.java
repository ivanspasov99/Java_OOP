package bg.sofia.uni.fmi.jira.interfaces;

import java.time.LocalDateTime;
import bg.sofia.uni.fmi.jira.enums.IssueResolution;
import bg.sofia.uni.fmi.jira.enums.IssueStatus;

public interface IIssue {
    void resolve(IssueResolution resolution);
    void setStatus(IssueStatus status);
    String getId();
    LocalDateTime getCreatedAt();
    LocalDateTime getLastModifiedAt();
}
