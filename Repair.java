
public class Repair {
    
    private static final int SMALL_INCREMENT = 5;
    private static final int MAX_ITERATIONS = 500;
    
    public static void repair(Graph graph){
        
        System.out.println("-- Repair function start --\n");
        System.out.printf("Before Repair: %.3f\n", new Rating(graph).overallRating);
        
        if (new Rating(graph).isUnacceptable()) {
            // Start tweaking the nodes to improve the graph's Rating
            moveAllNodes(graph, SMALL_INCREMENT);
            System.out.printf("After first repair: %.3f\n", new Rating(graph).overallRating);
        }
        
        // If the graph is still bad, then we need to keep repairing
        if (new Rating(graph).isUnacceptable()){
            // TODO: work on next level of repair
            
            // TODO: This is probably temporary
            double scalar = 1.0 / new Rating(graph).overallRating;
            if (scalar > 1.1 && scalar < 3.0){
                //System.out.printf("Scaling graph by factor of %.3f\n", scalar);
                //graph.setBorder(scalar, scalar);
            }
        }
        
        System.out.println();
    }
    
    private static void moveAllNodes(Graph graph, int increment){
        boolean changed = true;
        for (int iterations=0; changed && iterations<MAX_ITERATIONS; iterations++) {
            changed = false;
            for (int i = 0; i < graph.nodes.length; i++) {
                changed |= moveNodeOnce(graph, i, increment);
            }
        }
    }
    
    private static boolean moveNodeOnce(Graph graph, int index, int increment){
        Rating current = new Rating(graph);
        Rating left, right, up, down;
        Point point = graph.nodes[index].getCenter();
        
        // Calculate ratings
        point.x += increment;
        right = new Rating(graph);
        point.x -= 2*increment;
        left = new Rating(graph);
        point.x += increment;
        point.y += increment;
        down = new Rating(graph);
        point.y -= 2*increment;
        up = new Rating(graph);
        point.y += increment;
        
        // Use the best rating
        Rating best = Rating.best(current, right, left, down, up);
        if (best == right) point.x += increment;
        else if (best == left) point.x -= increment;
        else if (best == down) point.y += increment;
        else if (best == up) point.y -= increment;
        else {
            return false;
        }
        return true;
    }
    
    private static void cleanup(Graph graph){
        
    }
}
