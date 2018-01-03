package retro;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;

public class Job{
	
	public static void main(String[] args){
		if(args.length == 0){
			throw new IllegalArgumentException("No arguments given. Please supply a sprint ID");
		}
		int sprintId = Integer.parseInt(args[0]);
		execute(sprintId);
	}
	
	public static void execute(int sprintId){
		try {
			Sprint sprint = getSprint(sprintId);
			List<Issue> issues = getIssuesFromSprint(sprint);
			filterWorklogOnDate(issues, sprint.getStart(), sprint.getEnd());
			write(issues, new File ("retro.csv"));
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}
	}
	
	private static Sprint getSprint(int sprintId) throws ParseException{
		JiraCaller caller = new JiraCaller("sprint/"+sprintId).setApiVersion("1.0");
		JsonNode sprintNode = caller.call();
		return new Sprint(sprintNode);
	}
	
	private static List<Issue> getIssuesFromSprint(Sprint sprint) throws ParseException{
		JiraCaller issueCaller = new JiraCaller("search");
		issueCaller.addParam("maxResults", "100");
		issueCaller.addJqlParam("sprint", Integer.toString(sprint.getId()));
		JsonNode root = issueCaller.call();
		ArrayNode issues = (ArrayNode)root.get("issues");
		Iterator<JsonNode> issueIter = issues.getElements();
		List<Issue> result = new LinkedList<>();
		while(issueIter.hasNext()){
			JsonNode issueJson = issueIter.next();
			Issue issue = new Issue(issueJson);
			
			result.add(issue);
		}
		return result;
	}
	
	private static void filterWorklogOnDate(List<Issue> issues, Date startDate, Date endDate){
		for(Issue issue : issues){
			issue.setWorklog(issue.getWorklog().stream()
					.filter(work -> startDate.before(work.getDateUpdated()) && work.getDateUpdated().before(endDate))
					.collect(Collectors.toList()));
		}
	}
	
	private static void write(List<Issue> issues, File outputFile) throws IOException{
		BufferedWriter out = new BufferedWriter(new FileWriter(outputFile));
		out.write("ID,Title,Estimate,Actual,Diff\n");
		for(Issue issue : issues){
			out.write(issue.toCsv()+'\n');
		}
		double estimateTotal = issues.stream().mapToDouble(Issue::getEstimate).sum();
		double acutalTotal = issues.stream().mapToDouble(Issue::calcPointsSpent).sum();
		out.write(String.join(",", "", "", estimateTotal+"", acutalTotal+"", (estimateTotal-acutalTotal)+""));
		out.flush();
		out.close();
	}
	
}