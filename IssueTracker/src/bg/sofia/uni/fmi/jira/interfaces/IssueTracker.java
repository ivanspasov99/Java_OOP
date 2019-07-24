package bg.sofia.uni.fmi.jira.interfaces;

import bg.sofia.uni.fmi.jira.Component;
import bg.sofia.uni.fmi.jira.enums.IssuePriority;
import bg.sofia.uni.fmi.jira.enums.IssueResolution;
import bg.sofia.uni.fmi.jira.enums.IssueStatus;
import bg.sofia.uni.fmi.jira.enums.IssueType;
import bg.sofia.uni.fmi.jira.issues.Issue;

import java.time.LocalDateTime;

public interface IssueTracker {
    Issue[] findAll(Component component, IssueStatus status);
    Issue[] findAll(Component component, IssuePriority priority);
    Issue[] findAll(Component component, IssueType type);
    Issue[] findAll(Component component, IssueResolution resolution);
    Issue[] findAllIssuesCreatedBetween(LocalDateTime startTime, LocalDateTime endTime);
    Issue[] findAllBefore(LocalDateTime dueTime);
}
