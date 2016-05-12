package part2;

import part2.Tweet;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;


public class KMeans {
	
	private Tweet[] dataset;
	private String[] idList;
	private HashMap<String,Tweet> centroids;
	private int k;
	private HashMap<Integer,ArrayList<Tweet>> groups;
	
	public KMeans(int k, Tweet[] dataset){
		this.dataset = dataset;
		this.k = k;
		randomGenerate();
		this.centroids = new HashMap<>();
		findInitCent();
		this.groups = new HashMap<>();
		for(int i=0;i<k;i++)
			this.groups.put(i, new ArrayList<Tweet>());
	}
	
	public KMeans(String[] initID, Tweet[] dataset){
		this.dataset = dataset;
		this.idList = initID;
		this.k = initID.length;
		this.centroids = new HashMap<>();
		findInitCent();
		this.groups = new HashMap<>();
		for(int i=0;i<this.k;i++)
			this.groups.put(i, new ArrayList<Tweet>());
	}
	
	private void randomGenerate(){
		this.idList = new String[this.k];
		
		Random r = new Random();
		ArrayList<Integer> tmp = new ArrayList<>();
		
		for(int i=0;i<this.k;){
			int rand = r.nextInt(this.dataset.length);
			if(!tmp.contains(rand)){
				tmp.add(rand);
				this.idList[i] = this.dataset[rand].getId();
				i++;
			}
		}
	}
	
	private void findInitCent(){
		for(int i=0;i<this.dataset.length;i++){
			for(int j=0;j<this.idList.length;j++){
				if(this.dataset[i].getId().equals(this.idList[j])){
					this.centroids.put(this.idList[j], this.dataset[i]);
				}
			}
		}
		if(this.centroids.size()<this.idList.length)
			throw new RuntimeException("Jason file does not contains some tweets specified by the initial id file");
	}
	
	private double jacDist(String id, Tweet other){
		Tweet cent;
		if(id!=null)
		{
			cent = this.centroids.get(id);
			return jacDist(cent,other);
		}
		else{
			return Double.MAX_VALUE;
		}		
	}
	
	private double jacDist(Tweet cent, Tweet other){
		double dist = 0;
		
		HashSet<String> centText = new HashSet<>();
		HashSet<String> otherText = new HashSet<>();
		
		String[] tmp1 = cent.getText().split(" ");
		String[] tmp2 = other.getText().split(" ");
		
		for(int i=0;i<tmp1.length;i++)
			centText.add(tmp1[i]);
		
		for(int i=0;i<tmp2.length;i++)
			otherText.add(tmp2[i]);
		
		int common = 0;
		int union = 0;
		
		Iterator<String> iter = centText.iterator();
		while(iter.hasNext()){
			String tmp = iter.next();
			if(otherText.contains(tmp)){
				common++;
				union++;
				otherText.remove(tmp);
			}
			else{
				union++;
			}
		}
		union += otherText.size();
		
		dist = 1-common/union;
		return dist;	
	}
	
	private int checkGroup(Tweet tweet){
		int gId = 0;
		double lastDist = jacDist(this.idList[0],tweet);
		
		for(int i=1;i<this.idList.length;i++){
			double dist = jacDist(this.idList[i],tweet);
			if(dist<lastDist){
				gId = i;
				lastDist = dist;
			}
		}
		
		return gId;
	}
	
	private void groupData(){
		
		// clear the content of lists
		for(int i=0;i<this.k;i++){
			this.groups.get(i).clear();
		}
		
		// check group for each tweet and update group
		for(int i=0;i<this.dataset.length;i++){
			int gId = checkGroup(this.dataset[i]);
			this.groups.get(gId).add(this.dataset[i]);
		}
		
		// update means
		for(int i=0;i<this.idList.length;i++){
			ArrayList<Tweet> tmp = this.groups.get(i);
			double minDist = Double.MAX_VALUE;
			Tweet minT = null;
			for(int j=0;j<tmp.size();j++){
				double dist = 0;
				for(int k=0;k<tmp.size();k++){
					dist += jacDist(tmp.get(j),tmp.get(k));
				}
				if(dist<minDist){
					minDist = dist;
					minT = tmp.get(j);
				}
			}
			if(minT != null){
				this.idList[i]=minT.getId();
				this.centroids.put(this.idList[i], minT);
			}
			else{
				this.idList[i]=null;
			}
		}
	}
	
	public double calSSE(){
		double sum = 0;
		for(int i=0;i<this.idList.length;i++){
			ArrayList<Tweet> tmp = groups.get(i);
			for(int j=0;j<tmp.size();j++){
				sum += Math.pow(jacDist(this.idList[i],tmp.get(j)),2);
			}
		}
		return sum;
	}
	
	public void start(){
		int iter = 0;
		String[] lastIdList = Arrays.<String>copyOf(this.idList,this.idList.length);
		while(iter<25){
			iter++;
			groupData();
			if(Arrays.deepEquals(lastIdList, this.idList))
				break;
			else
				lastIdList = Arrays.<String>copyOf(this.idList,this.idList.length);
		}
	}	
	
	public void print(String out){
		try
		{
		
		// start processing
		BufferedWriter writer = new BufferedWriter(new FileWriter(out));
		StringBuilder builder = new StringBuilder();
		for(int i=0;i<this.idList.length;i++){
			builder.append(i);
			builder.append(" ");
			ArrayList<Tweet> tmp = this.groups.get(i);
			for(int j=0;j<tmp.size();j++){
				builder.append(tmp.get(j).getId());
				if(j!=tmp.size()-1)
					builder.append(",");
			}
			builder.append("\n");
		}
		builder.append(calSSE());
		writer.write(builder.toString());
		System.out.println(builder.toString());
		writer.flush();
		writer.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
