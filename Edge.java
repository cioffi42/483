public class Edge 
{
    public double weight;
	public Node node1, node2;
	
	public Edge(double weight, Node node1, Node node2)
	{
		this.weight = weight;
		this.node1 = node1;
		this.node2 = node2;
	}
}
