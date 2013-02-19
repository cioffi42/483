import javax.swing.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.*;

public class DisplayPane extends JPanel {
    
    private static final long serialVersionUID = 1L;
    private Graph graph;
    private Node[] nodes;
    private Connection[] connections;
    private Edge[] edges;
    private Point point;
    private Item item;
    private static final int NUM_NODES = 5;
    private static final int RADIUS = 20;
    private static final Color DEFAULT_NODE_COLOR = Color.BLUE;
    private static final Color FOCUS_NODE_COLOR = Color.GREEN;
    
    private Graphics bufferGraphics;
    private Image bufferImage;
    
    public void init(){
        nodes = new Node[NUM_NODES];
    	connections = new Connection[NUM_NODES];
    	edges = new Edge[NUM_NODES];
        for (int i = 0; i < NUM_NODES; i++){
        	item = new Item("a", "b", "c");
        	point = new Point(Math.random()*1100, Math.random()*500);
            nodes[i] = new Node(point, item);
            
        }
        for (int i = 0; i < NUM_NODES - 1; i++){
        	connections[i] = new Connection(edges[i], nodes[i], nodes[i+1]);
        }
        connections[4] = new Connection(edges[4], nodes[4], nodes[0]);
        graph = new Graph(nodes, connections);
        //bufferImage = createImage(getWidth(), getHeight());
        //bufferGraphics = bufferImage.getGraphics();
    }
    
    public void paint(Graphics g){
        super.paint(g);
        g.setColor(DEFAULT_NODE_COLOR);
        
        drawGraph(g, graph);
    }
    
    public void drawGraph(Graphics g, Graph graph)
    {
    	List<Node> nodeList;
    	List<Connection> connectionList;
    	Node node;
    	Connection connection;
    	
    	//Display Graph nodes
        nodeList = graph.getNodes();
        for (int i = 0; i < nodeList.size(); i++)
        {
        	node = nodeList.get(i);
        	drawCircle(g, node.getCenter().getX(), node.getCenter().getY(), RADIUS);
        }
        //Display Graph connections
        connectionList = graph.getConnections();
        for (int i = 0; i < connectionList.size(); i++)
        {
        	connection = connectionList.get(i);
        	g.drawLine((int)connection.getFirstNode().getCenter().getX(), (int)connection.getFirstNode().getCenter().getY(),
        			(int)connection.getSecondNode().getCenter().getX(), (int)connection.getSecondNode().getCenter().getY());
        }
        //g.drawImage(bufferImage, 0, 0, this);
    }
    
    public void setGraph(Graph graph){
    	this.graph = graph;
    }
    
    private static void drawCircle(Graphics g, double xCenter, double yCenter, int r){
        g.fillOval((int)xCenter-r, (int)yCenter-r, 2*r, 2*r);
    }
    
    public void update(Graphics g){
    	paint(g);
    }
    
}
