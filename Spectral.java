
public class Spectral {
    
    private static final int NOT_FOUND = -1;
    
    private static Point scale(Point original, int width, int height){
        original.x = (original.x + 1) * ((double)width/2.0);
        original.y = (original.y + 1) * ((double)height/2.0);
        return original;
    }
    
    public static Graph createGraph(Item[] items, Edge[] edges){
        // Create Jacobian matrix
        int n = items.length;
        double[][] matrix = new double[n][n];
        for (Edge edge : edges){
            int first = NOT_FOUND;
            int second = NOT_FOUND;
            for (int i=0; i<n; i++){
                if (items[i] == edge.item1){
                    first = i;
                } else if (items[i] == edge.item2){
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
        Node[] nodes = new Node[items.length];
        Connection[] connections = new Connection[edges.length];
        int width = MainApplet.displayPane.getWidth();
        int height = MainApplet.displayPane.getHeight();
        for (int i=0; i<nodes.length; i++){
            nodes[i] = new Node(scale(new Point(ed.V[i][1], ed.V[i][2]), width, height), items[i]);
        }
        for (int i=0; i<connections.length; i++){
            // TODO: get rid of repeated calculation
            int first = NOT_FOUND;
            int second = NOT_FOUND;
            for (int j=0; j<n; j++){
                if (items[j] == edges[i].item1){
                    first = j;
                } else if (items[j] == edges[i].item2){
                    second = j;
                }
            }
            connections[i] = new Connection(edges[i], nodes[first], nodes[second]);
        }
        
        return new Graph(nodes, connections);
    }
}
