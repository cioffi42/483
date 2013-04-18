import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.*;

@SuppressWarnings("serial")
public class MainPanel extends JPanel {
    
    private static final String APP_TITLE = "Concept Map";
    
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 600;
    
    // The width of the DisplayPane relative to the applet width
    private static final double DISPLAY_WIDTH = 0.7;
    
    private static JSplitPane splitPane;
    public static DisplayPane displayPane;
    public static sidePanel sidePane;
    public static NodeListPanel nodeListPane;
    public static NodeListPanel tagListPane;
    
    // Contains all nodes from file
    public static Node[] allNodes;
    public static Edge[] allEdges;
    
    // Contains all nodes selected by the current tags
    public static Node[] nodes;
    public static Edge[] edges;
    
    public static String[] tags = {};
    public static String[] selectedTags;
    
    public static void main(String[] args){
        JFrame app = new JFrame();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setSize(WIDTH, HEIGHT);
        app.setTitle(APP_TITLE);
        MainPanel panel = new MainPanel();
        app.add(panel);
        app.setVisible(true);
        panel.startup();
        app.setResizable(false);
        app.validate();
    }
    
    public void startup(){
    	
        // Parse the provided CSV file to get nodes and edges
    	Parser.parse();
    	
    	applyTags(null, false);
    	
        displayPane = new DisplayPane();
        JPanel infoPane = new JPanel(new BorderLayout());
        sidePane = new sidePanel();
        nodeListPane = new NodeListPanel(false);
        tagListPane = new NodeListPanel(true);
        
        displayPane.setMinimumSize(new Dimension(WIDTH/2, HEIGHT));
        infoPane.setMinimumSize(new Dimension(WIDTH/4, HEIGHT));
        
        JTabbedPane tabbedPane = new JTabbedPane();
        
        tabbedPane.addTab("Topics", null, nodeListPane, null);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        tabbedPane.addTab("Tags", null,  tagListPane, null);
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
        
        
        
        infoPane.add(sidePane, BorderLayout.CENTER);
        infoPane.add( tabbedPane, BorderLayout.SOUTH);
        
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, displayPane, infoPane);
        splitPane.setOneTouchExpandable(true);
        
        setSize(WIDTH, HEIGHT);
        setLayout(new BorderLayout());
        this.add(splitPane, BorderLayout.CENTER);
          
        validate();
        splitPane.setDividerLocation(DISPLAY_WIDTH);
        
        
        
        splitPane.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, 
            new PropertyChangeListener(){
                @Override
                public void propertyChange(PropertyChangeEvent event) {
                    validate();
                    // This code will be executed whenever the slider moves
                    displayPane.setFocusNode(displayPane.getFocusNode(), true);
                }
            }
        );
        
        validate();

        sidePane.connect();
        
        final File htmlFile = new File("Info.htm");
        final String path1 = htmlFile.getAbsolutePath();
        sidePane.setFile(path1);
        
        displayPane.setFocusNode(nodes[0], false);

        // This is for testing purposes only
        /*String[] tests = TestCode.tryAllGraphs();
        System.out.println("TESTS:");
        for (String result : tests){
            System.out.println(result);
        }*/
    }
    
    // Applies the given tags and alters MainPanel.nodes and MainPanel.edges
    // If tags is null or empty, then all nodes and edges will be included.
    public static void applyTags(String[] tags, boolean resetGraph){
        if (tags == null || tags.length == 0){
            // Don't apply any tags, just include all nodes/edges
            nodes = new Node[allNodes.length];
            for (int i=0; i<allNodes.length; i++){
                nodes[i] = allNodes[i];
            }
            edges = new Edge[allEdges.length];
            for (int i=0; i<allEdges.length; i++){
                edges[i] = allEdges[i];
            }
        } else {
            // Only include nodes with at least one of the tags
            ArrayList<Node> newNodes = new ArrayList<Node>();
            for (Node node : allNodes) {
                for (String tag : tags) {
                    if (node.tags.contains(tag)) {
                        newNodes.add(node);
                        break;
                    }
                }
            }
            // Only include edges where BOTH nodes are included
            ArrayList<Edge> newEdges = new ArrayList<Edge>();
            for (Edge edge : allEdges){
                if (newNodes.contains(edge.node1) && newNodes.contains(edge.node2)){
                    newEdges.add(edge);
                }
            }
            // Set MainPanel.nodes and MainPanel.edges
            MainPanel.nodes = new Node[newNodes.size()];
            for (int i=0; i<MainPanel.nodes.length; i++){
                MainPanel.nodes[i] = newNodes.get(i);
            }
            MainPanel.edges = new Edge[newEdges.size()];
            for (int i=0; i<MainPanel.edges.length; i++){
                MainPanel.edges[i] = newEdges.get(i);
            }
        }
        
        if (resetGraph) {
            // After applying tags, we need to reset the focus node
            Node oldFocusNode = displayPane.getFocusNode();
            Node newFocusNode;
            if (oldFocusNode == null || MathUtils.search(nodes, oldFocusNode) == -1) {
                // The old focus node does NOT have the appropriate tags (or doesn't exist)
                newFocusNode = nodes[0];
            } else {
                // The current focus node has the appropriate tags
                newFocusNode = displayPane.getFocusNode();
            }
            displayPane.setFocusNode(newFocusNode, true);
        }
    }
    
	public static Node findNodeByName(String name){
        for (int i=0; i<allNodes.length; i++){
            if (allNodes[i].getName().compareTo(name) == 0){
                return allNodes[i];
            }
        }
        return null;
    }
}
