import java.util.*;

public class Graph 
{
	Node[] nodes;
	Edge[] edges;
	
	public double[][] matrix;
	
	public Graph(List<Node> nodes, List<Edge> edges)
	{
	    this.nodes = nodes.toArray(this.nodes);
	    this.edges = edges.toArray(this.edges);
	    createLaplacianMatrix();
	}
	
	public Graph(Node[] nodes, Edge[] edges)
	{
		this.nodes = nodes;
		this.edges = edges;
		createLaplacianMatrix();
	}
	
	private static final int NOT_FOUND = -1;
	
	private void createLaplacianMatrix(){
	    matrix = new double[this.nodes.length][this.nodes.length];
	    for (Edge edge : edges){
            int first = NOT_FOUND;
            int second = NOT_FOUND;
            for (int i=0; i<this.nodes.length; i++){
                if (nodes[i] == edge.node1){
                    first = i;
                } else if (nodes[i] == edge.node2){
                    second = i;
                }
            }
            if (first != NOT_FOUND && second != NOT_FOUND){
                matrix[first][second] = matrix[second][first] = -1.0;
                matrix[first][first] += edge.weightBA;
                matrix[second][second] += edge.weightAB;
            }
        }
	}
}
