
public class Spectral {
    
    private static Point scale(Point original, int width, int height){
        original.x = (original.x + 1) * ((double)width/2.0);
        original.y = (original.y + 1) * ((double)height/2.0);
        return original;
    }
    
    public static Graph createGraph(Node[] nodes, Edge[] edges){
        
        Graph graph = new Graph(nodes, edges);
        
        // Get eigenvectors
        EigenvalueDecomposition ed = new EigenvalueDecomposition(graph.matrix);
        
        // Create Graph object
        int width = MainApplet.displayPane.getWidth();
        int height = MainApplet.displayPane.getHeight();
        for (int i=0; i<graph.nodes.length; i++){
            graph.nodes[i].setCenter(scale(new Point(ed.V[i][1], ed.V[i][2]), width, height));
        }
        
        return graph;
    }
}
