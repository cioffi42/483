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
 
    public NodeListPanel()
    {
        super(new BorderLayout());
        
        String[] nodearray = new String[MainApplet.nodes.length];
        for (int i = 0; i < nodearray.length; i++)
        {
        	nodearray[i] = MainApplet.nodes[i].getName();
        }
        Arrays.sort(nodearray);
 
        listModel = new DefaultListModel();
        
        for (int i = 0; i < nodearray.length; i++)
        	listModel.addElement(nodearray[i]);
 
        //Create the list and put it in a scroll pane.
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
        buttonPane.add(searchButton);
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
                MainApplet.displayPane.setFocusNode(node);
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
            
            /* Manual search algorithm
            for (int i=0; i < list.getModel().getSize(); i++)
            {
                String str = ((String)list.getModel().getElementAt(i)).toLowerCase();
               	if (str.startsWith(name))
                {
                	int j = list.getSelectedIndex() + 1;
                	// if the current selected value already starts with search name, go to the next value
                	if (!(((String)list.getModel().getElementAt(j)).toLowerCase().startsWith(name)))
                		j = i;
                	//if (!((String)list.getModel().getElementAt(j)).toLowerCase().startsWith(name))
                	//	j = i;
                    list.setSelectedIndex(j); 
                    list.ensureIndexIsVisible(j); 
                    break;
                }
                else if (i+1 == list.getModel().getSize())
                {
                    Toolkit.getDefaultToolkit().beep();
                    nodeNameTextField.requestFocusInWindow();
                    nodeNameTextField.selectAll();
                }
            }*/
            
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
 
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Node List Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Create and set up the content pane.
        JComponent newContentPane = new NodeListPanel();
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
