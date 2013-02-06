import java.util.*;

public class Graph 
{
	List<Node> nodes;
	List<Connection> connections;
	
	public Graph()
	{
		this.nodes = new ArrayList<Node>();
		this.connections = new ArrayList<Connection>();
	}
	
	public Graph(List<Node> nodes, List<Connection> connections)
	{
		this.nodes = nodes;
		this.connections = connections;
	}
	
	public Graph(Node[] nodes, Connection[] connections)
	{
		this.nodes = Arrays.asList(nodes);
		this.connections = Arrays.asList(connections);
	}
	
	public void addNode(Node node)
	{
		nodes.add(node);
	}
	
	public void addConnection(Connection connection)
	{
		connections.add(connection);
	}
	
	public void removeNode(Node node)
	{
		nodes.remove(node);
	}
	
	public void removeConnection(Connection connection)
	{
		connections.remove(connection);
	}
}
