package bg.sofia.uni.fmi.jira.issues;

import bg.sofia.uni.fmi.jira.Component;
import bg.sofia.uni.fmi.jira.User;
import bg.sofia.uni.fmi.jira.ValidateNullValues;
import bg.sofia.uni.fmi.jira.enums.IssuePriority;
import bg.sofia.uni.fmi.jira.issues.exceptions.InvalidReporterException;

import java.time.LocalDateTime;

public abstract class ScheduledIssue extends Issue {
    private final LocalDateTime dueTime;

    public ScheduledIssue(IssuePriority priority, Component component, User reporter, String description, LocalDateTime dueTime) throws InvalidReporterException {
        super(priority, component, reporter, description);
        ValidateNullValues.validatePar(new Object[]{ dueTime });
        this.dueTime = dueTime;
    }

    public LocalDateTime getDueTime() {
        return dueTime;
    }
}
