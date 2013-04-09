import javax.swing.*;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.text.AttributedString;
import java.util.*;

import javax.imageio.ImageIO;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DisplayPane extends JPanel {
    
    private static final long serialVersionUID = 1L;
    private Graph graph;
    private static final Color DEFAULT_NODE_COLOR = Color.BLACK;
    private static final Color HOVER_NODE_COLOR = new Color(62, 194, 255);
    private static BufferedImage regularNodeImage;
    private static BufferedImage focusNodeImage;
    private static BufferedImage hoverNodeImage;
    
    private Graphics bufferGraphics;
    private Image bufferImage;
    private static int imgHeight;
    private static int imgWidth;
    
    private Mouse mouse;
    public int offsetX;
    public int offsetY;
    
    //Used to determine which nodes get displayed
    private static final int MAX_NODES = 11;
    private static final double DECAY = 0.8;
    
    private static Node focusNode = null;
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
    		regularNodeImage = ImageIO.read(new File("newnode.png"));
    		focusNodeImage = ImageIO.read(new File("focusnode.png"));
    		hoverNodeImage = ImageIO.read(new File("selectednode.png"));
    		imgHeight = regularNodeImage.getHeight();
    		imgWidth = regularNodeImage.getWidth();
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
    
    public Node getFocusNode(){
        return focusNode;
    }
    
    public void setFocusNode(Node focus, boolean doAnimation){
        
        // If an animation is running, DON'T let the user set a new focus node
        // until the animation is complete.
        if (Animation.isRunning){
            return;
        }
        
        focusNode = focus;
        hoverNode = null;
        offsetX = offsetY = 0;
        
        Node[] nodes = DisplayPane.determineNodes(focus);
        Arrays.sort(nodes);
        
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
        
        if (doAnimation) {
            // Record the old positions of nodes (for animations)
            for (Node node : nodes) {
                if (node.getCenter() == null || graph == null || MathUtils.search(graph.nodes, node) == -1) {
                    node.setCenter(new Point(getWidth() / 2, getHeight() / 2));
                }
                node.oldCenter = new Point(node.getCenter().x, node.getCenter().y);
            }
        }
        
        // Generate the graph
        Graph graph = Spectral.createGraph(nodes, usedEdges);
        Repair.repair(graph);
        setGraph(graph);
        
        if (doAnimation){
            // Record the new positions of nodes (for animations)
            for (Node node : nodes){
                node.newCenter = new Point(node.getCenter().x, node.getCenter().y);
            }
            // Execute animation
            (new Animation(nodes)).start();
        }
    }
    
    // We need to avoid having nodes added to a list more than once
    private static void addNode(List<Node> nodes, Node node, double weight){
    	int index = nodes.indexOf(node);
    	
        // If the node isn't in the list, then add it
        if (index == -1){
        	node.setWeight(weight);
            nodes.add(node);
        }
        // If the node is on the list, but the weight is lower, then modify the weight
        else
        {
        	if (nodes.get(index).getWeight() < weight)
        		nodes.get(index).setWeight(weight);
        }
    }
    
    //Performs a quicksort on a list of nodes
    public static List<Node> sortNodes(List<Node> list)
    {
    	Node pivotNode;
    	List<Node> less, greater, newList;
    	int pivotSize;
    	
    	//Base case:  Only 0 or 1 elements
        if (list.size() <= 1)
            return list;
        
        pivotSize = list.size() / 2;
        pivotNode = list.get(pivotSize);
        
        less = new ArrayList<Node>();
        greater = new ArrayList<Node>();
        newList = new ArrayList<Node>();
        
        //Adds smaller weights to 'less', and larger weights to 'greater'
        for (int i = 0; i < list.size(); i++)
        {
        	//Ignores the pivot node
        	if (i != pivotSize)
        	{
	        	if (list.get(i).getWeight() > pivotNode.getWeight())
	        		greater.add(list.get(i));
	        	else
	        		less.add(list.get(i));
        	}
        }
        
        //Recursively calls sortNodes with the sub-lists
        less = sortNodes(less);
        greater = sortNodes(greater);
        
        //Adds everything back together
        newList.addAll(greater);
        newList.add(pivotNode);
        newList.addAll(less);
        
        return newList;
    }
    
    //Returns the nodes that will be displayed with a given focus node
    public static Node[] determineNodes(Node focus)
    {
    	Node[] oldNodes = MainApplet.nodes;
    	Node[] usedNodes;
    	Edge[] oldEdges = MainApplet.edges;
    	List<Node> newNodes = new ArrayList<Node>();
    	Node temp;
    	double weight;
    	int traversed = 0;
    	
    	newNodes.add(focus);
    	newNodes.get(0).setWeight(1.0);
    	
    	//Continue until every node has been accounted for
    	while (newNodes.size() < oldNodes.length)
    	{
    		int oldSize = newNodes.size();
    	    for (int i = traversed; i < newNodes.size() && newNodes.size() < oldNodes.length; i++)
    		{
    			temp = newNodes.get(i);
    			for (int j = 0; j < oldEdges.length; j++)
    			{
	    			if (oldEdges[j].hasNode(temp))
	        		{
	        			if (oldEdges[j].node1 == temp)
	        			{
	        				weight = (oldEdges[j].weightAB * temp.getWeight() * DECAY);
                            addNode(newNodes, oldEdges[j].node2, weight);
	        			}
	        			else if (oldEdges[j].node2 == temp)
	        			{
	        				weight = (oldEdges[j].weightBA * temp.getWeight() * DECAY);
                            addNode(newNodes, oldEdges[j].node1, weight);
	        			}
	        		}
	        		if (newNodes.size() >= oldNodes.length)
	        		{
	        			break;
	        		}
    			}
    			//Avoids repeating nodes that have already been checked
    			traversed++;
    		}
    	    
    	    // If we didn't add any new nodes this iteration, then 
    	    // stop. This keeps us from getting stuck in the while loop.
    	    if (newNodes.size() == oldSize){
    	        break;
    	    }
    	}
    	
    	newNodes = sortNodes(newNodes);
    	
    	if (newNodes.size() > MAX_NODES)
    	{
    		newNodes = newNodes.subList(0, MAX_NODES);
    		usedNodes = new Node[MAX_NODES];
    	}
    	else
    		usedNodes = new Node[newNodes.size()];
    	
    	usedNodes = newNodes.toArray(usedNodes);
    	return usedNodes;
    }
    
    public void drawGraph(Graphics g, Graph graph)
    {
        Graphics2D g2 = (Graphics2D) g;
    	Node node;
    	Edge edge;
    	
    	// Make the edges and text look less jagged
    	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    	g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    	
        //Display Graph connections
        g2.setStroke(new BasicStroke(5));
        for (int i = 0; i < graph.edges.length; i++)
        {
            edge = graph.edges[i];
            if (edge.node1 == hoverNode || edge.node2 == hoverNode){
                g2.setColor(HOVER_NODE_COLOR);
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
        	BufferedImage imageToDraw;
        	if (node == hoverNode){
        	    imageToDraw = hoverNodeImage;
        	} else if (node == focusNode){
        	    imageToDraw = focusNodeImage;
        	} else {
        	    imageToDraw = regularNodeImage;
        	}
        	drawCircle(g, node.getCenter().getX() - offsetX, node.getCenter().getY() - offsetY, imageToDraw);
        	drawText(g2, node.getName(), node.getCenter().getX() - offsetX, node.getCenter().getY() - offsetY);
        }
        
    }
    
    public Graph getGraph(){
        return graph;
    }
    
    public void setGraph(Graph graph){
    	this.graph = graph;
    }
    
    private static void drawCircle(Graphics g, double xCenter, double yCenter, BufferedImage imageToDraw){
        g.drawImage(imageToDraw, (int)xCenter - imgWidth/2, (int)yCenter - imgHeight/2, null);
    }
    
    private static final int MAX_STRING_WIDTH = 150;
    private static final int MAX_STRING_WIDTH_TWO_LINES = 135;
    
    private void drawText(Graphics2D g2, String name, double xCenter, double yCenter){
        int x, y;
        
        // Set the font
        g2.setColor(DEFAULT_NODE_COLOR);
        Font font = new Font(g2.getFont().getFamily(), Font.BOLD, 14);
        g2.setFont(font);
        
        // Create the attributed string (which handles subscripts/superscripts)
        AttributedString attributedName = Text.applyStringStyles(name);
        int length = attributedName.getIterator().getEndIndex();
        
        // Get the dimensions of the string
        FontMetrics metrics = getFontMetrics(font);
        Rectangle2D box = metrics.getStringBounds(attributedName.getIterator(), 0, length, g2);
        int width = (int)box.getWidth();
        int height = (int)box.getHeight();
        
        if (width < MAX_STRING_WIDTH){
            // We can fit the text on one line, so draw it
            x = (int)(xCenter - 0.5*width);
            y = (int)(yCenter + 0.3*height);
            g2.drawString(attributedName.getIterator(), x, y);
        } else {
            // We need to try drawing the text on two separate lines
            AttributedString[] twoLines = null;
            int colonIndex = name.indexOf(':');
            if (colonIndex != -1){
                // There is a ':' in the text, so we should try splitting the text based on the ':' position
                twoLines = splitTextTwoLines(metrics, g2, name, colonIndex);
            }
            if (twoLines == null){
                // We still haven't split the text into two lines, so try splitting at various space characters
                int spaceIndex = name.length();
                while ((twoLines == null) && (spaceIndex = name.lastIndexOf(' ', spaceIndex-1)) != -1){
                    twoLines = splitTextTwoLines(metrics, g2, name, spaceIndex);
                }
            }
            if (twoLines != null){
                int firstAsLength = twoLines[0].getIterator().getEndIndex();
                int firstWidth = (int)metrics.getStringBounds(twoLines[0].getIterator(), 0, firstAsLength, g2).getWidth();
                x = (int)(xCenter - 0.5*firstWidth);
                y = (int)(yCenter - 0.2*height);
                g2.drawString(twoLines[0].getIterator(), x, y);
                int secondAsLength = twoLines[1].getIterator().getEndIndex();
                int secondWidth = (int)metrics.getStringBounds(twoLines[1].getIterator(), 0, secondAsLength, g2).getWidth();
                x = (int)(xCenter - 0.5*secondWidth);
                y = (int)(yCenter + 0.8*height);
                g2.drawString(twoLines[1].getIterator(), x, y);
            }
        }
    }
    
    // Tries to split the given string at the splitIndex into two AttributedString objects that fit in the node.
    // Returns array of two AttributedString objects if successful, null if it couldn't fit.
    private AttributedString[] splitTextTwoLines(FontMetrics metrics, Graphics2D g2, String name, int splitIndex){
        AttributedString firstAs = Text.applyStringStyles(name.substring(0, splitIndex+1));
        int firstAsLength = firstAs.getIterator().getEndIndex();
        int firstWidth = (int)metrics.getStringBounds(firstAs.getIterator(), 0, firstAsLength, g2).getWidth();
        if (firstWidth < MAX_STRING_WIDTH_TWO_LINES){
            AttributedString secondAs = Text.applyStringStyles(name.substring(splitIndex+1));
            AttributedString[] twoLines = {firstAs, secondAs};
            return twoLines;
        }
        return null;
    }
    
    //problem when using a node.getName() that is NULL, error check for this
    //need to manage it so words don't get cut in half by a new line
    //not centered correctly
    private void drawText2(Graphics g, String name, double xCenter, double yCenter)
    {
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
		g.setFont(new Font(g.getFont().getFamily(), Font.BOLD, 14));
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
            double scaledX = (x - (node.getCenter().getX()-offsetX)) / (0.9*imgWidth/2);
            double scaledY = (y - (node.getCenter().getY()-offsetY)) / (0.8*imgHeight/2);
            if (scaledX*scaledX + scaledY*scaledY <= 1.0){
                return node;
            }
        }
        return null;
    }
}
