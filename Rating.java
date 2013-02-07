import java.util.Iterator;


public class Rating {
    public static int rateGraph(Graph graph){
        double nodeNodeDistance = Double.MAX_VALUE;
        
        // For each node
        int numNodes = graph.nodes.size();
        for (int i=0; i<numNodes; i++){
            
            // check node-node distances
            Iterator<Node> itNode = graph.nodes.listIterator(i);
            Node firstNode = itNode.next();
            while (itNode.hasNext()){
                Node secondNode = itNode.next();
                double distance = MathUtils.dist(firstNode, secondNode);
                if (distance < nodeNodeDistance){
                    nodeNodeDistance = distance;
                }
            }
            
            // check node-edge distances
            
            // check edge-edge angles
        }
        
        System.out.println(nodeNodeDistance);
        return 0;
    }
}
