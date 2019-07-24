package com.jetbrains;
import bg.sofia.uni.fmi.jira.Component;
import bg.sofia.uni.fmi.jira.User;
import bg.sofia.uni.fmi.jira.enums.IssuePriority;
import bg.sofia.uni.fmi.jira.enums.IssueResolution;
import bg.sofia.uni.fmi.jira.enums.IssueStatus;
import bg.sofia.uni.fmi.jira.issues.Bug;
import bg.sofia.uni.fmi.jira.issues.NewFeature;
import bg.sofia.uni.fmi.jira.issues.Task;
import bg.sofia.uni.fmi.jira.issues.exceptions.InvalidReporterException;

import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) throws InvalidReporterException {
	    //User ivan = new User(null);
        User ivan = new User("Ivan");
        Component com = new Component("Kirisoblqnica", "kiri", ivan);

        Bug bug = new Bug(IssuePriority.MINOR, com, ivan, "We are fucking idiots");
        bug.resolve(IssueResolution.WONT_FIX);
        bug.setStatus(IssueStatus.IN_PROGRESS);
        LocalDateTime dueDate = LocalDateTime.now().plusMonths(3);

        Task task = new Task(IssuePriority.MAJOR, com, ivan, "We are NOT fucking idiots", dueDate);
        NewFeature newFissue = new NewFeature(IssuePriority.MAJOR, com, ivan, "We are NOT fucking idiots", dueDate);

    }
}
