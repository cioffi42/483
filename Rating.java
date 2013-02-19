import java.util.Iterator;


public class Rating {
    
    // TODO: should we have to do so much work just to see if nodes are connected?
    private static boolean areConnected(Graph graph, Node a, Node b){
        Iterator<Edge> it = graph.edges.iterator();
        while (it.hasNext()){
            Edge edge = it.next();
            if ((edge.node1 == a && edge.node2 == b) || (edge.node1 == b && edge.node2 == a)){
                return true;
            }
        }
        return false;
    }
    
    public static int rateGraph(Graph graph){
        
        double shortestEdge = Double.MAX_VALUE;
        double longestEdge = 0.0;
        double shortestNonEdge = Double.MAX_VALUE;
        
        double lowestNodeEdgeDistance = Double.MAX_VALUE;
        
        double lowestDistFromWall = Double.MAX_VALUE;
        
        // For each node
        int numNodes = graph.nodes.size();
        for (int i=0; i<numNodes; i++){
            
            // check node-node distances
            Iterator<Node> itNode = graph.nodes.listIterator(i);
            Node firstNode = itNode.hasNext() ? itNode.next() : null;
            if (firstNode != null){
                while (itNode.hasNext()){
                    Node secondNode = itNode.next();
                    double distance = MathUtils.dist(firstNode, secondNode);
                    if (areConnected(graph, firstNode, secondNode)){
                        if (distance < shortestEdge){
                            shortestEdge = distance;
                        }
                        if (distance > longestEdge){
                            longestEdge = distance;
                        }
                    } else {
                        if (distance < shortestNonEdge){
                            shortestNonEdge = distance;
                        }
                    }
                }
            }
            
            // check node-wall distances
            int width = MainApplet.displayPane.getWidth();
            int height = MainApplet.displayPane.getHeight();
            double wallDistX = Math.min(firstNode.getCenter().x, width-firstNode.getCenter().x);
            double wallDistY = Math.min(firstNode.getCenter().y, height-firstNode.getCenter().y);
            lowestDistFromWall = Math.min(wallDistX, wallDistY);
        }
        
        // For each edge
        Iterator<Edge> itCon = graph.edges.iterator();
        while (itCon.hasNext()){
            
            // check node-edge distances
            Edge edge = itCon.next();
            Iterator<Node> itNode = graph.nodes.iterator();
            while (itNode.hasNext()){
                Node node = itNode.next();
                if (node != edge.node1 && node != edge.node2){
                    double distance = MathUtils.dist(node, edge);
                    if (distance < lowestNodeEdgeDistance){
                        lowestNodeEdgeDistance = distance;
                    }
                }
            }
        }
        
        System.out.printf("Shortest Edge: %.3f\n" +
        		"Longest Edge: %.3f\n" +
        		"Shortest Non-Edge: %.3f\n" +
        		"Node-Edge Distance: %.3f\n" +
        		"Node-Wall Distance: %.3f\n", 
                shortestEdge, longestEdge, shortestNonEdge, 
                lowestNodeEdgeDistance, lowestDistFromWall);
        return 0;
    }
}
