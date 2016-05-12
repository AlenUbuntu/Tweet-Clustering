package part2;

import java.util.HashMap;

public class Tweet {

	private HashMap<String,String> dict;
	
	public Tweet(String line){
		dict = new HashMap<>();
		this.parse(line);
	}
	
	private void parse(String line){
		line = line.substring(line.indexOf("{")+1,line.lastIndexOf("}"));
		String text = line.substring(line.indexOf(": ")+1,line.indexOf("\",")+1);
		dict.put("text", text);
		String id = line.substring(line.indexOf("\"id\": ")+6,line.indexOf(", \"iso_language_code\""));
		dict.put("id",id);
	}
	
	public String getText(){
		return dict.get("text");
	}
	
	public String getId(){
		return dict.get("id");
	}
}
