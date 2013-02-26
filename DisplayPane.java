import javax.swing.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.*;

public class DisplayPane extends JPanel {
    
    private static final long serialVersionUID = 1L;
    private Graph graph;
    private static final int RADIUS = 20;
    private static final Color DEFAULT_NODE_COLOR = Color.BLUE;
    private static final Color FOCUS_NODE_COLOR = Color.GREEN;
    
    private Graphics bufferGraphics;
    private Image bufferImage;
    
    public void paint(Graphics g){
        super.paint(g);
        g.setColor(DEFAULT_NODE_COLOR);
        
        if (graph != null){
            drawGraph(g, graph);
        }
    }
    
    public void drawGraph(Graphics g, Graph graph)
    {
    	Node node;
    	Edge edge;
    	
    	//Display Graph nodes
        for (int i = 0; i < graph.nodes.length; i++)
        {
        	node = graph.nodes[i];
        	drawCircle(g, node.getCenter().getX(), node.getCenter().getY(), RADIUS);
        }
        //Display Graph connections
        for (int i = 0; i < graph.edges.length; i++)
        {
        	edge = graph.edges[i];
        	g.drawLine((int)edge.node1.getCenter().getX(), (int)edge.node1.getCenter().getY(),
        			(int)edge.node2.getCenter().getX(), (int)edge.node2.getCenter().getY());
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
