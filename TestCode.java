
public class TestCode {
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
        new Rating(graph).print();
        printGraph(graph);
    }
    
    public static void printGraph(Graph graph){
        for (int i=0; i<graph.nodes.length; i++){
            Node node = graph.nodes[i];
            System.out.printf("%d  %d\n", (int)node.getCenter().x, (int)node.getCenter().y);
        }
        System.out.println();
    }
}
