import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;


public class Parser {
	public static void parse(){
		
		ArrayList<Node> nodes = new ArrayList<Node>();
		ArrayList<Edge> edges = new ArrayList<Edge>();
		
		try {
			Scanner sc = new Scanner(new FileReader("relationships.csv"));
			sc.nextLine(); // skip first line
			while (sc.hasNextLine()){
				String line = sc.nextLine();
				String[] entries = line.split(",");
				Node nodeA = search(nodes, entries[0]);
				if (nodeA == null){
					nodeA = new Node("", entries[0], "");
					nodes.add(nodeA);
				}
				Node nodeB = search(nodes, entries[3]);
				if (nodeB == null){
					nodeB = new Node("", entries[3], "");
					nodes.add(nodeB);
				}
				edges.add(new Edge(Double.parseDouble(entries[1]), Double.parseDouble(entries[2]), nodeA, nodeB));
			}
			
			MainApplet.nodes = new Node[nodes.size()];
			for (int i=0; i<MainApplet.nodes.length; i++){
				MainApplet.nodes[i] = nodes.get(i);
			}
			MainApplet.edges = new Edge[edges.size()];
			for (int i=0; i<MainApplet.edges.length; i++){
				MainApplet.edges[i] = edges.get(i);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public static Node search(ArrayList<Node> list, String name){
		Iterator<Node> it = list.iterator();
		while (it.hasNext()){
			Node node = it.next();
			if (node.getName().compareTo(name) == 0){
				return node;
			}
		}
		return null;
	}
}
