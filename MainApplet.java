import javax.swing.*;


public class MainApplet extends JApplet{

    private static final long serialVersionUID = 1L;
    
    private static JSplitPane mainPanel;
    private static DisplayPane displayPane;
    private static InfoPane infoPane;
    
    @Override
    public void init(){
        displayPane = new DisplayPane();
        infoPane = new InfoPane();
        
        mainPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, displayPane, infoPane);
        mainPanel.setOneTouchExpandable(true);
        this.add(mainPanel);
    }
}
