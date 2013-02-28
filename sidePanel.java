
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.*;
import javax.swing.*;


import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleContext;
import javax.swing.text.StyleConstants;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;





public class sidePanel  extends JPanel {

	JTextField _fileNameTF = new JTextField(15);
    JFileChooser _fileChooser = new JFileChooser();
    
	private BufferStrategy strategy;
	public boolean running,oval1,oval2,oval3;
	public double xpos,ypos;
	
	//public JTextArea text ;
	public JTextPane text;
	public JPanel info;
	public String txt;
	public BufferedImage image;
	public String pth1, pth2;
	public sidePanel(){
		
		  super(new BorderLayout());
	
		
		//----------------------------------------------------------------------
	    // Handle HTML file
		text = new JTextPane();
		JScrollPane scroll = new JScrollPane(text);
		HTMLEditorKit kit = new HTMLEditorKit();
		HTMLDocument doc = new HTMLDocument();
		text.setEditorKit(kit);
		text.setDocument(doc);
		
		
		text.setPreferredSize(new Dimension(345,795));
		
		//info.add(text, BorderLayout.EAST);
		pth1 = "images/velocity.jpg";
		pth2 = "images/velocity.jpg";
		
		File file1 = new File(pth1);
		File file2 = new File(pth2);
		String path1 = file1.getAbsolutePath();

		String path2 = file2.getAbsolutePath();

		try {
		kit.insertHTML(doc, doc.getLength(),
		"<table border='0' align='left' cellpadding='5' cellspacing='0'>" +
		"<tr>" +
		"<td align='center' valign='top'>" +
		"Caption Area" +
		"<br> " +
		"<img src='" + path1 + "' align='left'/>" +
		"<br>" +
		"Caption Area" +
		"</td>" +
		"<td>" +
		"<b>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</b>" +
		"</td>" +
		"</tr>" +
		"</table>" +
		"<img src='" + path2 + "' width=200 height=200 align='right'/>" +
		"<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>"
		, 0, 0, null);
		} catch (BadLocationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		
		text.setVisible(true);
		text.setEditable(false);
		
		
		
		//----------------------------------------------------------------------
		
	
		 add(scroll, BorderLayout.NORTH);
	   
	  
	}
	 private static void createAndShowGUI() {
	        //Create and set up the window.
	        JFrame frame = new JFrame("Info Pane");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 
	        //Create and set up the content pane.
	        JComponent newContentPane = new sidePanel();
	        newContentPane.setOpaque(true); //content panes must be opaque
	        frame.setContentPane(newContentPane);
	 
	        //Display the window.
	        frame.pack();
	        frame.setVisible(true);
	    }
	 
	 public static void main(String[] args) {
	        //Schedule a job for the event-dispatching thread:
	        //creating and showing this application's GUI.
	        javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                createAndShowGUI();
	            }
	        });
	    }
	
	
}
	
	

	
