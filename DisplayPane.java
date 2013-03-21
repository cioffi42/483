import javax.swing.*;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DisplayPane extends JPanel {
    
    private static final long serialVersionUID = 1L;
    private Graph graph;
    private static final Color DEFAULT_NODE_COLOR = Color.BLACK;
    private static final Color FOCUS_NODE_COLOR = Color.RED;
    private static BufferedImage image;
    private static BufferedImage focusNodeImage;
    
    private Graphics bufferGraphics;
    private Image bufferImage;
    private static int imgHeight;
    private static int imgWidth;
    
    private Mouse mouse;
    public int offsetX;
    public int offsetY;
    
    //Used to determine which nodes get displayed
    private static final int MAX_NODES = 10;
    private static final double MIN_WEIGHT = 0.2;
    
    public static Node focusNode = null;
    public static Node hoverNode = null;
    
    public DisplayPane()
    {
        setBackground(Color.WHITE);
        offsetX = offsetY = 0;
        mouse = new Mouse();
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
    	try
    	{
    		image = ImageIO.read(new File("NewNode.gif"));
    		focusNodeImage = ImageIO.read(new File("SelectedNode.gif"));
    		imgHeight = image.getHeight();
    		imgWidth = image.getWidth();
    	}
    	catch (IOException e)
    	{
    		//handle exception
    	}
    }
    
    public void paint(Graphics g){
        super.paint(g);
        g.setColor(DEFAULT_NODE_COLOR);
        
        if (graph != null){
            drawGraph(g, graph);
        }
    }
    
    //Returns the nodes that will be displayed with a given focus node
    public static Node[] determineNodes(Node focus)
    {
    	Node[] oldNodes = MainApplet.nodes;
    	Node[] usedNodes;
    	Edge[] oldEdges = MainApplet.edges;
    	List<Node> newNodes = new ArrayList<Node>();
    	Node temp;
    	
    	newNodes.add(focus);
    	for (int i = 0; i < oldEdges.length; i++)
    	{
    		if (oldEdges[i].hasNode(focus))
    		{
    			if (oldEdges[i].node1 == focus && oldEdges[i].weightAB > MIN_WEIGHT)
    				newNodes.add(oldEdges[i].node2);
    			else if (oldEdges[i].node2 == focus && oldEdges[i].weightBA > MIN_WEIGHT)
    				newNodes.add(oldEdges[i].node1);
    		}
    		if (newNodes.size() >= MAX_NODES)
    		{
    			break;
    		}
    	}
    	
    	while (newNodes.size() < MAX_NODES)
    	{
    		for (int i = 1; i < newNodes.size() && newNodes.size() >= MAX_NODES; i++)
    		{
    			temp = newNodes.get(i);
    			for (int j = 0; j < oldEdges.length; j++)
    			{
	    			if (oldEdges[j].hasNode(temp))
	        		{
	        			if (oldEdges[j].node1 == temp && oldEdges[j].weightAB > MIN_WEIGHT)
	        				newNodes.add(oldEdges[j].node2);
	        			else if (oldEdges[j].node2 == temp && oldEdges[j].weightBA > MIN_WEIGHT)
	        				newNodes.add(oldEdges[j].node1);
	        		}
	        		if (newNodes.size() >= MAX_NODES)
	        		{
	        			break;
	        		}
    			}
    		}
    	}
    	
    	usedNodes = new Node[newNodes.size()];
    	usedNodes = newNodes.toArray(usedNodes);
    	return usedNodes;
    }
    
    public void drawGraph(Graphics g, Graph graph)
    {
        Graphics2D g2 = (Graphics2D) g;
    	Node node;
    	Edge edge;
    	
        //Display Graph connections
        g2.setStroke(new BasicStroke(10));
        for (int i = 0; i < graph.edges.length; i++)
        {
            edge = graph.edges[i];
            if (edge.node1 == hoverNode || edge.node2 == hoverNode){
                g2.setColor(FOCUS_NODE_COLOR);
            } else {
                g2.setColor(DEFAULT_NODE_COLOR);
            }
        	g2.drawLine((int)edge.node1.getCenter().getX() - offsetX, 
        	        (int)edge.node1.getCenter().getY() - offsetY,
            		(int)edge.node2.getCenter().getX() - offsetX, 
            		(int)edge.node2.getCenter().getY() - offsetY);
        }
        //g.drawImage(bufferImage, 0, 0, this);
        
    	//Display Graph nodes
        for (int i = 0; i < graph.nodes.length; i++)
        {
        	node = graph.nodes[i];
        	drawCircle(g, node.getCenter().getX() - offsetX, node.getCenter().getY() - offsetY, (node == hoverNode));
        	drawText(g, node.getName(), node.getCenter().getX() - offsetX, node.getCenter().getY() - offsetY);
        }
        
    }
    
    public void setGraph(Graph graph){
    	this.graph = graph;
    }
    
    private static void drawCircle(Graphics g, double xCenter, double yCenter, boolean isFocusNode){
        //g.fillOval((int)xCenter-r, (int)yCenter-r, 2*r, 2*r);
        BufferedImage imageToDraw = isFocusNode ? focusNodeImage : image;
        g.drawImage(imageToDraw, (int)xCenter - imgWidth/2, (int)yCenter - imgHeight/2, null);
    }
    
    //problem with text color and "red color mouse over feature"
    //problem when using a node.getName() that is NULL, error check for this
    //need to manage it so words don't get cut in half by a new line
    //not centered correctly
    private void drawText(Graphics g, String name, double xCenter, double yCenter)
    {
    	//name = "Insert This Node's Name Here";							//warning, it doesn't take the node's real name
		int length = name.length();
		int nRows = length/15 + 1;     //15 in constant here, should be variable
		int rLength = length/nRows;
		ArrayList<String> substrings = new ArrayList<String>();
		int nextStartIndex=0;
		
		for (int i = 0; i < nRows; i++) 
		{
			int thisrLength = rLength;
			String substring = new String();
			
			if (i == (nRows/2 + 1)) 
				thisrLength += length % nRows;			
			if (i != (nRows - 1)) 
				substring = name.substring(nextStartIndex, nextStartIndex + thisrLength);
			else substring = name.substring(nextStartIndex);
		
			substrings.add(substring);
			nextStartIndex += thisrLength;
		}
		
		g.setColor(DEFAULT_NODE_COLOR);
		for (int i = 0; i < nRows; i++) 
		{
			int rowheight = imgHeight/(nRows + 1);
			int rowposition = (int)yCenter - imgHeight/2 + rowheight*(i + 1) + 3;				//added plus 3 for offset, possibly from font size, hater's go'n hate
			g.drawString(substrings.get(i), (int)xCenter - (int)(length*1.6), rowposition); 	//was expecting length/2 but *1.6 did the trick o.O, what am i missing?
																								//this will have toe change everytime we change font size
    	}   	    	
    }

    public void update(Graphics g){
    	paint(g);
    }
    
    public void setOffset(int newOffsetX, int newOffsetY){
        if (newOffsetX >= 0.0 && newOffsetX < getWidth()*(graph.getBorderX() - 1.0)){
            offsetX = newOffsetX;
        }
        if (newOffsetY >= 0.0 && newOffsetY < getHeight()*(graph.getBorderY() - 1.0)){
            offsetY = newOffsetY;
        }
    }
    
    public Node getMouseNode(int x, int y){
        for (Node node : graph.nodes){
            double scaledX = (x - (node.getCenter().getX()-offsetX)) / (imgWidth/2);
            double scaledY = (y - (node.getCenter().getY()-offsetY)) / (imgHeight/2);
            if (scaledX*scaledX + scaledY*scaledY <= 1.0){
                return node;
            }
        }
        return null;
    }
}
