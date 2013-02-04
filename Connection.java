public class Connection 
{
	Edge edge;
	Node node1, node2;
	
	public Connection(Edge edge, Node node1, Node node2)
	{
		this.edge = edge;
		this.node1 = node1;
		this.node2 = node2;
	}
	
	public Edge getEdge()
	{
		return edge;
	}
	
	public void setEdge(Edge newEdge)
	{
		edge = newEdge;
	}
	
	public Node getFirstNode()
	{
		return node1;
	}
	
	public void setFirstNode(Node newNode)
	{
		node1 = newNode;
	}
	
	public Node getSecondNode()
	{
		return node2;
	}
	
	public void setSecondNode(Node newNode)
	{
		node2 = newNode;
	}
}
