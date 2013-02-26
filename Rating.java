
public class Rating {
    
    /**
     * Abbreviations:
     * 
     * se: shortest edge
     * ntn: node to node (for two unconnected nodes)
     * nte: node to edge (for a node not connected to an edge)
     * ntw: node to wall (where wall means the boundary of the DisplayPane)
     */
    
    private Graph graph;
    
    private double seLength;
    private int seEdge;
    
    private double longestEdge;
    
    private double ntnLength;
    private int ntnNode1;
    private int ntnNode2;
    
    private double nteDistance;
    private int nteNode;
    private int nteEdge;
    
    private double ntwDistance;
    private int ntwNode;
    
    // TODO: should we have to do so much work just to see if nodes are connected?
    private static boolean areConnected(Graph graph, Node a, Node b){
        for (int i=0; i<graph.edges.length; i++){
            Edge edge = graph.edges[i];
            if ((edge.node1 == a && edge.node2 == b) || (edge.node1 == b && edge.node2 == a)){
                return true;
            }
        }
        return false;
    }
    
    public Rating(Graph graph){
        
        this.graph = graph;
        
        seLength = Double.MAX_VALUE;
        longestEdge = 0.0;
        ntnLength = Double.MAX_VALUE;
        nteDistance = Double.MAX_VALUE;
        ntwDistance = Double.MAX_VALUE;
        
        int numNodes = graph.nodes.length;
        
        // For each node
        for (int i=0; i<numNodes; i++){
            Node node = graph.nodes[i];
            
            // check node-wall distances
            int width = MainApplet.displayPane.getWidth();
            int height = MainApplet.displayPane.getHeight();
            double wallDistX = Math.min(node.getCenter().x, width-node.getCenter().x);
            double wallDistY = Math.min(node.getCenter().y, height-node.getCenter().y);
            double distance = Math.min(wallDistX, wallDistY);
            if (distance < ntwDistance){
                ntwDistance = distance;
                ntwNode = i;
            }
        }
        
        // For each pair of nodes
        for (int i=0; i<numNodes; i++){
            for (int j=i+1; j<numNodes; j++){
                
                // If the nodes are not connected
                if (graph.matrix[i][j] == 0){
                    // check distance between unconnected nodes
                    double distance = MathUtils.dist(graph.nodes[i], graph.nodes[j]);
                    if (distance < ntnLength){
                        ntnLength = distance;
                        ntnNode1 = i;
                        ntnNode2 = j;
                    }
                }
            }
        }
        
        // For each edge
        for (int i=0; i<graph.edges.length; i++){
            Edge edge = graph.edges[i];
            
            // check edge lengths
            double length = MathUtils.length(edge);
            if (length < seLength){
                seLength = length;
                seEdge = i;
            }
            if (length > longestEdge){
                longestEdge = length;
            }
            
            // check node-edge distances
            for (int j=0; j<graph.nodes.length; j++){
                Node node = graph.nodes[j];
                if (node != edge.node1 && node != edge.node2){
                    double distance = MathUtils.dist(node, edge);
                    if (distance < nteDistance){
                        nteDistance = distance;
                        nteEdge = i;
                        nteNode = j;
                    }
                }
            }
        }
    }
    
    public void print(){
        System.out.printf("Shortest Edge: %.3f\n" +
                "Longest Edge: %.3f\n" +
                "Shortest Non-Edge: %.3f\n" +
                "Node-Edge Distance: %.3f\n" +
                "Node-Wall Distance: %.3f\n", 
                seLength, longestEdge, ntnLength, 
                nteDistance, ntwDistance);
    }
}
