
public class Spectral {
    
    private static Point scale(Point original, int width, int height){
        original.x = (original.x + 1) * ((double)width/2.0);
        original.y = (original.y + 1) * ((double)height/2.0);
        return original;
    }
    
    public static Graph createGraph(Node[] nodes, Edge[] edges){
        
        Graph graph = new Graph(nodes, edges);
        
        int width = MainPanel.displayPane.getWidth();
        int height = MainPanel.displayPane.getHeight();
        
        // If there are 2 or fewer nodes, then don't run Spectral algorithm
        if (nodes.length == 1){
            nodes[0].setCenter(scale(new Point(0,0), width, height));
        } else if (nodes.length == 2){
            nodes[0].setCenter(scale(new Point(-0.5, 0), width, height));
            nodes[1].setCenter(scale(new Point(0.5, 0), width, height));
        } else {
            // Get eigenvectors
            EigenvalueDecomposition ed = new EigenvalueDecomposition(graph.matrix);
            
            // Set points for nodes
            for (int i=0; i<graph.nodes.length; i++){
                graph.nodes[i].setCenter(scale(new Point(ed.V[i][1], ed.V[i][2]), width, height));
            }
        }
        
        return graph;
    }
}
