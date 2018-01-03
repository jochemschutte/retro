package retro;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonNode;

public class Sprint{
	
	int id;
	String name;
	Date start;
	Date end;
	
	public Sprint(JsonNode json) throws ParseException{
		this.id = json.get("id").asInt();
		this.name = json.get("name").asText();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		this.start = format.parse(json.get("startDate").asText());
		this.end = format.parse(json.get("endDate").asText());
	}
	
	public Sprint(int id, String name, Date start, Date end) {
		this.id = id;
		this.name = name;
		this.start = start;
		this.end = end;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName(){
		return name;
	}
	
	public Date getStart() {
		return start;
	}
	
	public Date getEnd() {
		return end;
	}
	
}