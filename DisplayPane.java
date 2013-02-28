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
    private static final int RADIUS = 20;
    private static final Color DEFAULT_NODE_COLOR = Color.BLACK;
    private static final Color FOCUS_NODE_COLOR = Color.GREEN;
    private static BufferedImage image;
    
    private Graphics bufferGraphics;
    private Image bufferImage;
    private static int imgHeight;
    private static int imgWidth;
    
    public DisplayPane()
    {
        setBackground(Color.WHITE);
    	try
    	{
    		image = ImageIO.read(new File("NewNode.gif"));
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
        	g2.drawLine((int)edge.node1.getCenter().getX(), (int)edge.node1.getCenter().getY(),
            		(int)edge.node2.getCenter().getX(), (int)edge.node2.getCenter().getY());
        }
        //g.drawImage(bufferImage, 0, 0, this);
        
    	//Display Graph nodes
        for (int i = 0; i < graph.nodes.length; i++)
        {
        	node = graph.nodes[i];
        	drawCircle(g, node.getCenter().getX(), node.getCenter().getY(), RADIUS);
        }
        
    }
    
    public void setGraph(Graph graph){
    	this.graph = graph;
    }
    
    private static void drawCircle(Graphics g, double xCenter, double yCenter, int r){
        //g.fillOval((int)xCenter-r, (int)yCenter-r, 2*r, 2*r);
        g.drawImage(image, (int)xCenter - imgWidth/2, (int)yCenter - imgHeight/2, null);
    }
    
    public void update(Graphics g){
    	paint(g);
    }
    
}
