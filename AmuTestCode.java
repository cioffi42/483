import java.util.Iterator;


public class AmuTestCode {
    private static Item[] items;
    
    // Make Edge
    private static Edge me(int i, int j){
        return new Edge(1.0, items[i], items[j]);
    }
    
    public static void makeTestGraph(){
        int numNodes = 5;
        items = new Item[numNodes];
        for (int i=0; i<numNodes; i++) items[i] = new Item();
        Edge[] edges = {me(0,1), me(1,2), me(2,3), me(3,4), me(4,0)};
        Graph graph = Spectral.createGraph(items, edges);
        
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
    
    public static void runTests(){
        for (int numNodes=3; numNodes<=5; numNodes++){
            items = new Item[numNodes];
            items[0] = new Item();
            for (int i=1; i<numNodes; i++){
                items[i] = new Item();
            }
            Edge[] possibleEdges = new Edge[((numNodes-2)*(numNodes-1))/2];
            int index = 0;
            for (int i=0; i<numNodes-2; i++){
                for (int j=i+2; j<numNodes; j++){
                    possibleEdges[index++] = me(i,j);
                }
            }
            int numCombinations = (int)Math.pow(2.0, (double)possibleEdges.length);
            Edge[] edges = new Edge[numNodes - 1 + possibleEdges.length];
            for (int i=0; i<numCombinations; i++){
                int k = 0;
                for (int j=1; j<numNodes; j++){
                    edges[k++] = me(j-1,j);
                }
                for (int j=0; j<possibleEdges.length; j++){
                    if (((i >> j) % 2) == 1){
                        edges[k++] = possibleEdges[j];
                    }
                }
                Edge[] edgesArray = new Edge[k];
                for (int j=0; j<k; j++) edgesArray[j] = edges[j];
                System.out.println("-" + numNodes + "-");
                Graph graph = Spectral.createGraph(items, edgesArray);
                System.out.println(isGood(graph) ? "good graph" : "bad graph");
                System.out.println();
            }
        }
    }
    
    public static boolean isGood(Graph graph){
        Node[] nodes = new Node[graph.nodes.size()];
        for (int i=0; i<nodes.length; i++){
            nodes[i] = graph.nodes.get(i);
        }
        for (int i=0; i<nodes.length; i++){
            for (int j=i+1; j<nodes.length; j++){
                if (MathUtils.dist(nodes[i], nodes[j]) < 5.0){
                    return false;
                }
            }
        }
        return true;
    }
}
