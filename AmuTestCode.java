import java.util.Iterator;


public class AmuTestCode {
    private static Node[] nodes;
    
    // Make Edge
    private static Edge me(int i, int j){
        return new Edge(1.0, nodes[i], nodes[j]);
    }
    
    public static void makeTestGraph(){
        int numNodes = 5;
        nodes = new Node[numNodes];
        for (int i=0; i<numNodes; i++) nodes[i] = new Node();
        Edge[] edges = {me(0,1), me(1,2), me(2,3), me(3,4), me(4,0)};
        Graph graph = Spectral.createGraph(nodes, edges);
        
        MainApplet.displayPane.setGraph(graph);
        Rating.rateGraph(graph);
        printGraph(graph);
    }
    
    public static void printGraph(Graph graph){
        Iterator<Node> it = graph.nodes.iterator();
        while (it.hasNext()){
            Node node = it.next();
            System.out.printf("%d  %d\n", (int)node.getCenter().x, (int)node.getCenter().y);
        }
        System.out.println();
    }
}
