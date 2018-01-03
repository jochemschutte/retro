package retro;

import java.util.LinkedHashMap;
import java.util.Map;

import org.codehaus.jackson.JsonNode;

public class JiraCaller{
	
	public static final String username = "";
	public static final String password = "";
	public static final String urlBase = "";
	public static final String apiBase = "/rest/api/";
	
	private String apiVersion = "2";
	
	String function;
	Map<String,String> params = new LinkedHashMap<>();
	Map<String, String> jql = new LinkedHashMap<>();
	
	public JiraCaller(String function){
		this.function = function;
	}
	
	public JiraCaller addParam(String key, String value){
		params.put(key, value);
		return this;
	}
	
	public JiraCaller addJqlParam(String key, String value){
		params.put(key, value);
		return this;
	}
	
	public JiraCaller setApiVersion(String version){
		this.apiVersion = version;
		return this;
	}
	
	public JsonNode call(){
		//TODO implement
		return null;		
	}
	
}