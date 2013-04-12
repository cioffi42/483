import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;

import javax.swing.*;

public class MainApplet extends JApplet {

    private static final long serialVersionUID = 1L;
    
    // The width of the DisplayPane relative to the applet width
    private static final double DISPLAY_WIDTH = 0.7;
    
    private static JSplitPane mainPanel;
    public static DisplayPane displayPane;
    public static sidePanel sidePane;
    public static NodeListPanel nodeListPane;
    public static NodeListPanel tagListPane;
    //Contain all nodes and edges from file
    public static Node[] nodes;
    public static Edge[] edges;
    public static String [] tags = {"Midterm 1","Midterm 2","Final"}; 
  
    @Override
    public void init(){
    	
        // Parse the provided CSV file to get nodes and edges
    	Parser.parse();
    	
        displayPane = new DisplayPane();
        JPanel infoPane = new JPanel(new BorderLayout());
        sidePane = new sidePanel();
        nodeListPane = new NodeListPanel(false);
        tagListPane = new NodeListPanel(true);
        displayPane.setMinimumSize(new Dimension(400, 400));
        infoPane.setMinimumSize(new Dimension(300, 400));
        
        JTabbedPane tabbedPane = new JTabbedPane();
        
        tabbedPane.addTab("Node list", null, nodeListPane, "List of nodes");
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        tabbedPane.addTab("Tags", null,  tagListPane, "Special tags");
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
        
        
        infoPane.add(sidePane, BorderLayout.CENTER);
        infoPane.add( tabbedPane, BorderLayout.SOUTH);
      
        setSize(1200, 600);
        mainPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, displayPane, infoPane);
        mainPanel.setOneTouchExpandable(true);
        this.add(mainPanel);
          
        validate();
        mainPanel.setDividerLocation(DISPLAY_WIDTH);
        
        
        
        mainPanel.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, 
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
       
        
        final String path1 =  getCodeBase().toString();
        sidePane.getBrowser().getDisplay().asyncExec(new Runnable() {
            @Override
            public void run() {
            	String filename = "Velocity.html";
                sidePane.getBrowser().setUrl(path1+filename);
                sidePane.getBrowser().execute("document.write(\"<!DOCTYPE html>\")");
                
            }
        });

        displayPane.setFocusNode(nodes[0], false);

        // This is for testing purposes only
        /*String[] tests = TestCode.tryAllGraphs();
        System.out.println("TESTS:");
        for (String result : tests){
            System.out.println(result);
        }*/
    }
    public static void clearPanel(){
    	
    	
    	sidePane.getBrowser().getDisplay().asyncExec(new Runnable() {
            @Override
            public void run() {
            	sidePane.getBrowser().execute("var y = document.getElementsByTagName(\"h1\")[0]; " +
                		"while(y){ 	"+
                		"    if(y.nodeName.toLowerCase() == \"p\"  ||  y.nodeName.toLowerCase() == \"h1\"  ){"+
                				"y.style.display = 'none';" +
                		"	}							  "+
                		"    y = y.nextSibling;			  " +
                		"}								  ");
                
            }
        });
    	
    }
    
    public static void updatePanel (){
    	
    		clearPanel();
    	
    	
    	sidePane.getBrowser().getDisplay().asyncExec(new Runnable() {
            @Override
            public void run() {
                
            	String nodeName = displayPane.getFocusNode().getName();
            	
            
				   sidePane.getBrowser().execute(""+
						   
				  "var y = document.getElementsByTagName(\"h1\")[0];"+
				  " var found = false;"+
				  "while(y){ "+
				  "   if(y.nodeName.toLowerCase() == \"h1\"   && y.textContent ==\""+nodeName+"\" && found == false){"+
				  "       	   found = true;"+
				  " 	   y.style.display = 'block';"+  
				  "	}"+
				  "    else if (found == true && y.nodeName.toLowerCase() == \"p\"){"+
				   	 
				  " 	   y.style.display = 'block';"+
				  "        }"+
				     
				  "    else if (found == true && y.nodeName.toLowerCase() == \"h1\"){"+
				  " 		break;"+
				  "	}"+
				  "    else{}"+
				       
				      
				      " y = y.nextSibling;"+
				   "}"+
				   
				   "");
                
            }
        });
    
    }
    
	public static Node findNodeByName(String name){
        for (int i=0; i<nodes.length; i++){
            if (nodes[i].getName().compareTo(name) == 0){
                return nodes[i];
            }
        }
        return null;
    }
}

