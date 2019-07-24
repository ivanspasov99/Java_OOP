package bg.sofia.uni.fmi.jira.issues;

import bg.sofia.uni.fmi.jira.GenerateId;
import bg.sofia.uni.fmi.jira.User;
import bg.sofia.uni.fmi.jira.Component;
import bg.sofia.uni.fmi.jira.ValidateNullValues;
import bg.sofia.uni.fmi.jira.enums.IssuePriority;
import bg.sofia.uni.fmi.jira.enums.IssueResolution;
import bg.sofia.uni.fmi.jira.enums.IssueStatus;
import bg.sofia.uni.fmi.jira.enums.IssueType;
import bg.sofia.uni.fmi.jira.issues.exceptions.InvalidReporterException;
import bg.sofia.uni.fmi.jira.interfaces.IIssue;

import java.time.LocalDateTime;
import java.util.InvalidPropertiesFormatException;

public abstract class Issue implements IIssue {
    private IssuePriority priority; // could be final, if not changing at all
    private Component component;
    private IssueStatus status = IssueStatus.OPEN;
    private IssueResolution resolution = IssueResolution.UNRESOLVED;
    private User reporter;
    private String description;
    private String ID;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    Issue(IssuePriority priority, Component component, User reporter, String description) throws InvalidReporterException {
        if(reporter == null) {
            throw new InvalidReporterException("Please enter notNull value (reporter) ");
        }
        ValidateNullValues.validatePar(new Object[] { priority, component, reporter, description }); // it will work also without it, but cool pattern
        this.priority = priority;
        this.component = component;
        this.reporter = reporter;
        this.description = description;
        this.ID = component.getShortName() + GenerateId.generateId();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now(); // not sure if needed
    }
    public abstract IssueType getType();
    @Override
    public void resolve(IssueResolution resolution) {
        this.resolution = resolution;
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public void setStatus(IssueStatus status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public LocalDateTime getLastModifiedAt() {
        return updatedAt;
    }
    public Component getComponent() {
        return this.component;
    }
    public IssueStatus getStatus() {
        return this.status;
    }
    public IssuePriority getPriority() {
        return this.priority;
    }
    public IssueResolution getResolution() {
        return this.resolution;
    }
}
