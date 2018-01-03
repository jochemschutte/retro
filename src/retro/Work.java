package retro;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonNode;

public class Work{
	
	int id;
	int secondsSpent;
	Date dateUpdated;
	
	public Work(JsonNode node) throws ParseException{
		this.id = node.get("id").asInt();
		this.secondsSpent = node.get("timeSpentSeconds").asInt();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		this.dateUpdated = format.parse(node.get("updated").asText());
	}

	public int getId() {
		return id;
	}

	public int getSecondsSpent() {
		return secondsSpent;
	}

	public Date getDateUpdated() {
		return dateUpdated;
	}
	
}