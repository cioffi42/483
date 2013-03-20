public class Edge 
{
    public double weightAB, weightBA;
	public Node node1, node2;
	
	public Edge(double weightAB, double weightBA, Node node1, Node node2)
	{
		this.weightAB = weightAB;
		this.weightBA = weightBA;
		this.node1 = node1;
		this.node2 = node2;
	}
	
	public boolean hasNode(Node node)
	{
		return (node == node1 || node == node2);
	}
	
	public boolean includesNodes(Node[] nodes)
	{
		boolean temp1 = false, temp2 = false;
		
		for (int i = 0; i < nodes.length; i++)
		{
			if (node1 == nodes[i])
				temp1 = true;
			if (node2 == nodes[i])
				temp2 = true;
		}
		
		return (temp1 && temp2);
	}
}
