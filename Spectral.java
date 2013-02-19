
public class Spectral {
    
    private static final int NOT_FOUND = -1;
    
    private static Point scale(Point original, int width, int height){
        original.x = (original.x + 1) * ((double)width/2.0);
        original.y = (original.y + 1) * ((double)height/2.0);
        return original;
    }
    
    public static Graph createGraph(Node[] nodes, Edge[] edges){
        // Create Jacobian matrix
        int n = nodes.length;
        double[][] matrix = new double[n][n];
        for (Edge edge : edges){
            int first = NOT_FOUND;
            int second = NOT_FOUND;
            for (int i=0; i<n; i++){
                if (nodes[i] == edge.node1){
                    first = i;
                } else if (nodes[i] == edge.node2){
                    second = i;
                }
            }
            if (first != NOT_FOUND && second != NOT_FOUND){
                matrix[first][second] = matrix[second][first] = -1;
                matrix[first][first]++;
                matrix[second][second]++;
            }
        }
        
        // Get eigenvectors
        EigenvalueDecomposition ed = new EigenvalueDecomposition(matrix);
        
        // Create Graph object
        int width = MainApplet.displayPane.getWidth();
        int height = MainApplet.displayPane.getHeight();
        for (int i=0; i<nodes.length; i++){
            nodes[i].setCenter(scale(new Point(ed.V[i][1], ed.V[i][2]), width, height));
        }
        
        return new Graph(nodes, edges);
    }
}
