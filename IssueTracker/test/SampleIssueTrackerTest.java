
import static org.junit.Assert.assertEquals;

import bg.sofia.uni.fmi.jira.issues.Bug;
import bg.sofia.uni.fmi.jira.issues.Task;
import org.junit.BeforeClass;
import org.junit.Test;

import bg.sofia.uni.fmi.jira.Component;
import bg.sofia.uni.fmi.jira.Jira;
import bg.sofia.uni.fmi.jira.User;
import bg.sofia.uni.fmi.jira.enums.IssuePriority;
import bg.sofia.uni.fmi.jira.enums.IssueResolution;
import bg.sofia.uni.fmi.jira.enums.IssueStatus;
import bg.sofia.uni.fmi.jira.enums.IssueType;
import bg.sofia.uni.fmi.jira.interfaces.IssueTracker;
import bg.sofia.uni.fmi.jira.issues.Issue;
import bg.sofia.uni.fmi.jira.issues.exceptions.InvalidReporterException;

import java.time.LocalDateTime;

public class SampleIssueTrackerTest {

	private static Issue[] issues;

	private static IssueTracker issueTracker;

	private static User ivan;

	private static User pesho;

	private static Component peshosComponent;

	@BeforeClass
	public static void setUp() throws InvalidReporterException {
		ivan = new User("Ivan");
		pesho = new User("Pesho");
		peshosComponent = new Component("Pesho", "pc", pesho);

		issues = new Issue[4];
		issues[0] = new Bug(IssuePriority.MAJOR, peshosComponent, ivan, "Big Bug");
		issues[0].setStatus(IssueStatus.IN_PROGRESS);
		issues[1] = null;
		issues[2] = new Task(IssuePriority.TRIVIAL, peshosComponent, ivan, "Bigger Bug", LocalDateTime.now().plusDays(10));
		issues[3] = new Task(IssuePriority.TRIVIAL, peshosComponent, ivan, "Bigger Bug", LocalDateTime.now().plusDays(20));
		issueTracker = new Jira(issues);
	}
	@Test
	public void testFindAllBetween() {
		Issue[] result = issueTracker.findAllIssuesCreatedBetween(LocalDateTime.now().minusDays(1),LocalDateTime.now().plusDays(1));

		assertEquals(3, TestUtils.countIssues(result));
	}
	@Test
	public void tesFindAllBeforeDueDate() {
		Issue[] result = issueTracker.findAllBefore(LocalDateTime.now().plusDays(30));

		assertEquals(2, TestUtils.countIssues(result));
	}
	@Test
	public void testFindAllBeforeDueDate2() {
		Issue[] result = issueTracker.findAllBefore(LocalDateTime.now().plusDays(15));

		assertEquals(1, TestUtils.countIssues(result));
	}
	@Test
	public void testFindAllByStatus() {

		Issue[] result = issueTracker.findAll(peshosComponent, IssueStatus.OPEN);
		assertEquals(2, TestUtils.countIssues(result));
	}

	@Test
	public void testFindAllByResolution() {
		Issue[] result = issueTracker.findAll(peshosComponent, IssueResolution.UNRESOLVED);
		assertEquals(3, TestUtils.countIssues(result));
	}

	@Test
	public void testFindAllByType() {
		Issue[] result = issueTracker.findAll(peshosComponent, IssueType.TASK);
		assertEquals(2, TestUtils.countIssues(result));
	}
	@Test
	public  void testFianAllByPriority() {
		Issue[] result = issueTracker.findAll(peshosComponent, IssuePriority.TRIVIAL);
		assertEquals(2, TestUtils.countIssues(result));
	}
}
