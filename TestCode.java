import java.util.ArrayList;
import java.util.List;


public class TestCode {
    private static Node[] nodes;
    
    public static int test = 0;
    
    // Make Edge
    private static Edge me(int i, int j){
        return new Edge(1.0, 1.0, nodes[i], nodes[j]);
    }
    
    public static void testNodeDeterminer(){
        nodes = DisplayPane.determineNodes(MainApplet.nodes[0]);
        List<Edge> newEdges = new ArrayList<Edge>();
        
        for (int i = 0; i < MainApplet.edges.length; i++)
        {
            if (MainApplet.edges[i].includesNodes(nodes))
        	{
        		newEdges.add(MainApplet.edges[i]);
        	}
        }
        Edge[] usedEdges = new Edge[newEdges.size()];
    	usedEdges = newEdges.toArray(usedEdges);
        Graph graph = Spectral.createGraph(nodes, usedEdges);
        
        new Rating(graph).print();
        Repair.repair(graph);
        new Rating(graph).print();
        printGraph(graph);
        
        MainApplet.displayPane.setGraph(graph);
    }
    
    public static void makeTestGraph(){
        int numNodes = 5;
        
        nodes = new Node[numNodes];
        for (int i=0; i<numNodes; i++) nodes[i] = new Node();
        Edge[] edges = {me(0,1), me(0,2), me(0,3), me(0,4), me(1,2)};
        Graph graph = Spectral.createGraph(nodes, edges);
        
        
        //Graph graph = createRandomGraph(numNodes);
        //graph = Spectral.createGraph(graph.nodes, graph.edges);
        
        //Graph graph = new Graph(nodes, edges);
        //setRandomGraph(graph);
        
        /*Graph graph = new Graph(nodes, edges);
        graph.nodes[0].setCenter(new Point(100, 100));
        graph.nodes[1].setCenter(new Point(700, 100));
        graph.nodes[2].setCenter(new Point(700, 500));
        graph.nodes[3].setCenter(new Point(100, 500));
        graph.nodes[4].setCenter(new Point(500, 500));*/
        
        new Rating(graph).print();
        Repair.repair(graph);
        new Rating(graph).print();
        printGraph(graph);
        
        MainApplet.displayPane.setGraph(graph);
    }
    
    public static void printGraph(Graph graph){
        for (int i=0; i<graph.nodes.length; i++){
            Node node = graph.nodes[i];
            System.out.printf("%d  %d\n", (int)node.getCenter().x, (int)node.getCenter().y);
        }
        System.out.println();
    }
    
    public static Graph createRandomGraph(int numNodes){
        Node[] nodes = new Node[numNodes];
        Edge[] edgesFull = new Edge[(numNodes*(numNodes-1))/2];
        int edgeIndex = 0;
        
        for (int i=0; i<numNodes; i++){
            nodes[i] = new Node();
        }
        
        for (int i=0; i<numNodes; i++){
            for (int j=i+1; j<numNodes; j++){
                if (Math.random() > 0.5){
                    edgesFull[edgeIndex++] = new Edge(1.0, 1.0, nodes[i], nodes[j]);
                }
            }
        }
        
        Edge[] edges = new Edge[edgeIndex];
        for (int i=0; i<edges.length; i++){
            edges[i] = edgesFull[i];
        }
        
        return new Graph(nodes, edges);
    }
    
    public static void setRandomGraph(Graph graph){
        int width = MainApplet.displayPane.getWidth();
        int height = MainApplet.displayPane.getHeight();
        for (Node node : graph.nodes){
            node.setCenter(new Point(Math.random()*width, Math.random()*height));
        }
    }
}
