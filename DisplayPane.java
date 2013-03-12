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
    private int offsetX;
    private int offsetY;
    
    public Node focusNode = null;
    
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
            if (edge.node1 == focusNode || edge.node2 == focusNode){
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
        	drawCircle(g, node.getCenter().getX() - offsetX, node.getCenter().getY() - offsetY, (node == focusNode));
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
    
    public void update(Graphics g){
    	paint(g);
    }
    
    public void changeOffset(int dx, int dy){
        offsetX += dx;
        offsetY += dy;
    }
    
    public Node getMouseNode(int x, int y){
        for (Node node : graph.nodes){
            double scaledX = (x - node.getCenter().getX()) / (imgWidth/2);
            double scaledY = (y - node.getCenter().getY()) / (imgHeight/2);
            if (scaledX*scaledX + scaledY*scaledY <= 1.0){
                return node;
            }
        }
        return null;
    }
}
