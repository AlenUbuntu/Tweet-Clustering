package part2;
import part2.Commands;

public class Launcher {

	public static void main(String[] args){
		Commands cmd = new Commands(Integer.parseInt(args[0]),args[1],args[2],args[3]);	
		DataParser parser = new DataParser();
		parser.parse(cmd.getInUrl());
		parser.parse(cmd.getKUrl());
		
		KMeans km = new KMeans(parser.getInitID(),parser.getData());
		km.start();
		km.print(cmd.getOutUrl());
	}
}
