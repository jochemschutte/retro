package retro;

import java.text.ParseException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.codehaus.jackson.JsonNode;

public class Issue{
	
	public static final int SECONDS_IN_POINT = 60*60*8;
	
	final int id;
	final String key;
	final String title;
	//nr story points
	final double estimate;
	List<Work> worklog = new LinkedList<>();
	
	public Issue(JsonNode node) throws ParseException{
		this.id = node.get("id").asInt();
		this.key = node.get("key").asText();
		this.title = node.get("description").asText();
		//TODO set estimate
		this.estimate = 0;
		Iterator<JsonNode> workIter = node.get("worklogs").getElements();
		while(workIter.hasNext()){
			this.worklog.add(new Work(workIter.next()));
		}
	}
	
	public int getId(){
		return this.id;
	}
	
	public List<Work> getWorklog(){
		return this.worklog;
	}
	
	public double getEstimate(){
		return this.estimate;
	}
	
	public String getKey() {
		return key;
	}

	public String getTitle() {
		return title;
	}

	public void setWorklog(List<Work> worklog){
		this.worklog = worklog;
	}
	
	public String toCsv(){
		double pointsSpent = this.calcPointsSpent();
		return String.join(",", key, title, estimate+"", pointsSpent+"", (estimate-pointsSpent)+"");
	}
	
	public double calcPointsSpent(){
		return this.worklog.stream().mapToInt(Work::getSecondsSpent).sum() / SECONDS_IN_POINT;
	}
}