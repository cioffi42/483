import java.awt.BorderLayout;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;


public class MainApplet extends JApplet {

    private static final long serialVersionUID = 1L;
    
    // The width of the DisplayPane relative to the applet width
    private static final double DISPLAY_WIDTH = 0.7;
    
    private static JSplitPane mainPanel;
    public static DisplayPane displayPane;
    public static sidePanel sidePane;
    public static NodeListPanel nodeListPane;
    
    @Override
    public void init(){
        displayPane = new DisplayPane();
        JPanel infoPane = new JPanel(new BorderLayout());
        sidePane = new sidePanel();
        nodeListPane = new NodeListPanel();
        
        displayPane.setMinimumSize(new Dimension(400, 400));
        infoPane.setMinimumSize(new Dimension(300, 400));
        
        infoPane.add(sidePane, BorderLayout.CENTER);
        infoPane.add(nodeListPane, BorderLayout.SOUTH);
        
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
                    TestCode.makeTestGraph();
                }
            }
        );
        
        validate();
        TestCode.makeTestGraph();
    }
}
