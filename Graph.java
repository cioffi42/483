
public class Graph 
{
	public Node[] nodes;
	public Edge[] edges;
	
	// If the graph fits on the screen, borderX and borderY will be 1.0
	// (DisplayPane width) * (borderX) == (graph's true width)
	// (DisplayPane height) * (borderY) == (graph's true height)
	private double borderX;
	private double borderY;
	
	public double[][] matrix;
	
	public Graph(Node[] nodes, Edge[] edges)
	{
		this.nodes = nodes;
		this.edges = edges;
		borderX = 1.0;
		borderY = 1.0;
		createLaplacianMatrix();
	}
	
	public double getBorderX(){
	    return borderX;
	}
	
	public double getBorderY(){
	    return borderY;
	}
	
	public void setBorder(double x, double y){
	    if (borderX == 1.0 && borderY == 1.0){
	        borderX = x;
	        borderY = y;
	        for (Node node : nodes){
	            node.getCenter().x *= borderX;
	            node.getCenter().y *= borderY;
	        }
	    }
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
