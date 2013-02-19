import java.util.*;

public class Graph 
{
	List<Node> nodes;
	List<Edge> edges;
	
	public Graph()
	{
		this.nodes = new ArrayList<Node>();
		this.edges = new ArrayList<Edge>();
	}
	
	public Graph(List<Node> nodes, List<Edge> edges)
	{
		this.nodes = nodes;
		this.edges = edges;
	}
	
	public Graph(Node[] nodes, Edge[] edges)
	{
		this.nodes = Arrays.asList(nodes);
		this.edges = Arrays.asList(edges);
	}
	
	public void addNode(Node node)
	{
		nodes.add(node);
	}
	
	public void addEdge(Edge edge)
	{
		edges.add(edge);
	}
	
	public void removeNode(Node node)
	{
		nodes.remove(node);
	}
	
	public void removeEdge(Edge edge)
	{
		edges.remove(edge);
	}
	
	public List<Node> getNodes()
	{
		return nodes;
	}
	
	public List<Edge> getEdges()
	{
		return edges;
	}
}
