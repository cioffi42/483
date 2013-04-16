import java.util.Arrays;


public class Rating {
    
    /**
     * Abbreviations:
     * 
     * se: shortest edge
     * ntn: node to node (for two unconnected nodes)
     * nte: node to edge (for a node not connected to an edge)
     * ntw: node to wall (where wall means the boundary of the DisplayPane)
     */
    
    public static enum Category {
        SE(0, 170.0, 250.0, false), NTN(1, 170.0, 300.0, false), 
        NTE(2, 100.0, 200.0, true), NTW(3, 90.0, 100.0, true);
        
        public int index;
        
        // The minimum acceptable value (weighted to 1.0)
        public final double MIN;
        
        // The desired value (weighted to 2.0)
        public final double DESIRED;
        
        // If true, any value above DESIRED gets weighted to 2.0
        public final boolean CAP;
        
        private Category(int index, double min, double desired, boolean cap){
            this.index = index;
            this.MIN = min;
            this.DESIRED = desired;
            this.CAP = cap;
        }
    }
    
    public double[] worstDistances;
    public double[] sumDistances;
    public double[] weightedRatings;
    
    public double overallRating;
    
    public Rating(Graph graph){
        
        worstDistances = new double[Category.values().length];
        sumDistances = new double[Category.values().length];
        Arrays.fill(worstDistances, Double.MAX_VALUE);
        Arrays.fill(sumDistances, 0.0);
        
        int numNodes = graph.nodes.length;
        
        // For each node
        for (int i=0; i<numNodes; i++){
            Node node = graph.nodes[i];
            
            // check node-wall distances
            int width = MainPanel.displayPane.getWidth();
            int height = MainPanel.displayPane.getHeight();
            Point point = node.getCenter();
            double distance = MathUtils.min(point.x, width-point.x, point.y, height-point.y);
            if (distance < worstDistances[Category.NTW.index]){
                worstDistances[Category.NTW.index] = distance;
            }
        }
        
        // For each pair of nodes
        for (int i=0; i<numNodes; i++){
            for (int j=i+1; j<numNodes; j++){
                
                // If the nodes are not connected
                if (graph.matrix[i][j] == 0){
                    // check distance between unconnected nodes
                    double distance = MathUtils.dist(graph.nodes[i], graph.nodes[j]);
                    if (distance < worstDistances[Category.NTN.index]){
                        worstDistances[Category.NTN.index] = distance;
                    }
                    sumDistances[Category.NTN.index] += Math.min(1.0, MathUtils.weight(distance, Category.NTN));
                }
            }
        }
        
        // For each edge
        for (int i=0; i<graph.edges.length; i++){
            Edge edge = graph.edges[i];
            
            // check edge lengths
            double length = MathUtils.length(edge);
            if (length < worstDistances[Category.SE.index]){
                worstDistances[Category.SE.index] = length;
            }
            sumDistances[Category.SE.index] += Math.min(1.0, MathUtils.weight(length, Category.SE));
            
            // check node-edge distances
            for (int j=0; j<graph.nodes.length; j++){
                Node node = graph.nodes[j];
                if (node != edge.node1 && node != edge.node2){
                    double distance = MathUtils.dist(node, edge);
                    if (distance < worstDistances[Category.NTE.index]){
                        worstDistances[Category.NTE.index] = distance;
                    }
                    sumDistances[Category.NTE.index] += Math.min(1.0, MathUtils.weight(distance, Category.NTE)); 
                }
            }
        }
        
        // Compute the overall rating
        weightedRatings = new double[Category.values().length];
        for (Category cat : Category.values()){
            weightedRatings[cat.index] = MathUtils.weight(worstDistances[cat.index], cat);
        }
        int worstCategory = MathUtils.argmin(weightedRatings);
        overallRating = weightedRatings[worstCategory];
        
        for (double number : weightedRatings){
            overallRating += 0.001*Math.min(1.4, number);
        }
        for (Category cat : Category.values()){
            overallRating += 0.00001*sumDistances[cat.index];
        }
    }
    
    // Returns the Rating object with the highest overallRating
    public static Rating best(Rating...ratings){
        Rating result = ratings[0];
        for (int i=0; i<ratings.length; i++){
            if (ratings[i].overallRating > result.overallRating){
                result = ratings[i];
            }
        }
        return result;
    }
    
    public boolean isUnacceptable(){
        return (overallRating < 1.0);
    }
    
    public void print(){
        System.out.println("GRAPH RATING");
        System.out.printf("Shortest Edge: %.3f\n" +
                "Shortest Non-Edge: %.3f\n" +
                "Node-Edge Distance: %.3f\n" +
                "Node-Wall Distance: %.3f\n", 
                weightedRatings[Category.SE.index], weightedRatings[Category.NTN.index], 
                weightedRatings[Category.NTE.index], weightedRatings[Category.NTW.index]);
        System.out.printf("OVERALL: %.3f\n\n", overallRating);
    }
}
