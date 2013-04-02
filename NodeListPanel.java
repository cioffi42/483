import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.Position;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class NodeListPanel extends JPanel implements ListSelectionListener
{
	private JList list;
    private DefaultListModel listModel;
 
    private static final String searchString = "Search";
    private static final String goString = "Go to Node";
    private JButton goButton;
    private JTextField nodeNameTextField;
 
    public NodeListPanel(boolean tags)
    {
        super(new BorderLayout());
        String[] nodearray ;
        if(tags == false){
        nodearray = new String[MainApplet.nodes.length];
        }
        else{
        	 nodearray = new String[MainApplet.tags.length];
        }
        
        for (int i = 0; i < nodearray.length; i++)
        {
        	
        	if(tags == false){
        		nodearray[i] = MainApplet.nodes[i].getName();;
            }
            else{
            	nodearray[i] = MainApplet.tags[i];
             }
        }
        Arrays.sort(nodearray);
 
        listModel = new DefaultListModel();
        
        for (int i = 0; i < nodearray.length; i++)
        	listModel.addElement(nodearray[i]);
 
        //Create the list and put it in a scroll pane.
        list = new JList(listModel);
        if(tags==true){
        	list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        }
        else{
        	list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        }
        list.setSelectedIndex(0);
        
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);
 
      
        
	  
        JButton searchButton = new JButton(searchString);
	    SearchListener SearchListener = new SearchListener(searchButton);
	    searchButton.setActionCommand(searchString);
	    searchButton.addActionListener(SearchListener);
	    searchButton.setEnabled(false);
        
        goButton = new JButton(goString);
        goButton.setActionCommand(goString);
        goButton.addActionListener(new GoListener());
 
        nodeNameTextField = new JTextField(10);
        nodeNameTextField.addActionListener(SearchListener);
        nodeNameTextField.getDocument().addDocumentListener(SearchListener);
        String name = listModel.getElementAt(
                              list.getSelectedIndex()).toString();
 
        //Create a panel that uses BoxLayout.
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane,
                                           BoxLayout.LINE_AXIS));
        buttonPane.add(nodeNameTextField);
        if( tags== false){
        	buttonPane.add(searchButton);
        }
       
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(goButton);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
 
        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);
    }
 
    class GoListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String nodename = list.getSelectedValue().toString();
            Node node = MainApplet.findNodeByName(nodename);
            if (node != null){
                MainApplet.displayPane.setFocusNode(node,true);
            }
            
        }
    }
 
    //This listener is shared by the text field and the search button.
    class SearchListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;
 
        public SearchListener(JButton button) {
            this.button = button;
        }
 
        //Required by ActionListener.
        public void actionPerformed(ActionEvent e) {
            String name = nodeNameTextField.getText();
            
            
            
            // Simple search using list functions
            int index = list.getNextMatch(name, 0, Position.Bias.Forward);
            if (list.getSelectedIndex() + 1 < list.getModel().getSize())
            	index = list.getNextMatch(name, list.getSelectedIndex()+1, Position.Bias.Forward);
            
            if (index == -1)
            {
            	Toolkit.getDefaultToolkit().beep();
                nodeNameTextField.requestFocusInWindow();
                nodeNameTextField.selectAll();
            }
            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
        }
 
        // Simple test for string equality.
        protected boolean alreadyInList(String name) {
            return listModel.contains(name);
        }
 
        //Required by DocumentListener.
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }
 
        //Required by DocumentListener.
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }
 
        //Required by DocumentListener.
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }
 
        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }
 
        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }
 
    //This method is required by ListSelectionListener.
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {
 
            if (list.getSelectedIndex() == -1) {
            //No selection, disable go button.
                goButton.setEnabled(false);
 
            } else {
            //Selection, enable the go button.
                goButton.setEnabled(true);
            }
        }
    }
 
  
}
