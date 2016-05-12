package part2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class DataParser{

	private int type;
	private ArrayList<Tweet> datasets;
	private ArrayList<String> idList;
	
	public DataParser(){
		datasets = new ArrayList<Tweet>();
		idList = new ArrayList<String>();
	}
	
	public void parse(String in){
		// get file extension	
		String exten = in.substring(in.lastIndexOf(".")+1);
		if (exten.equals("json"))
			type = 0;
		else 
			type = 1;
		
		
		// read file
		try{
		BufferedReader reader = new BufferedReader(new FileReader(in));
		String line;
		
		// JSON file
		if(type == 0){
			System.out.println("Parsing JSON file");
			while(reader.ready()){
				line = reader.readLine();
				datasets.add(new Tweet(line));
			}	
		}
		// id file
		else{
			System.out.println("Parsing id file");
			while(reader.ready()){
				line = reader.readLine();
				if(line.contains(","))
					line = line.substring(0, line.indexOf(","));
				idList.add(line);
			}
		}
		reader.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public Tweet[] getData(){
		if(!datasets.isEmpty())
			return datasets.<Tweet>toArray(new Tweet[datasets.size()]);
		else
			System.out.println("Jason File has not been parsed yet");
		return null;
	}
	
	public String[] getInitID(){
		if(!idList.isEmpty())
			return idList.<String>toArray(new String[idList.size()]);
		else
			System.out.println("Initial id file has not been parsed yet");
		return null;
	}
}
