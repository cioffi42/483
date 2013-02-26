
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





public class sidePanel  extends Canvas implements MouseListener{

	JTextField _fileNameTF = new JTextField(15);
    JFileChooser _fileChooser = new JFileChooser();
    
	private BufferStrategy strategy;
	public boolean running,oval1,oval2,oval3;
	public double xpos,ypos;
	
	//public JTextArea text ;
	public JTextPane text;
	public JPanel info;
	public Point xy;
	public String txt;
	public BufferedImage image;
	public String pth1, pth2;
	public sidePanel(){
		

		JFrame window= new JFrame("Physics Map");
		
		JPanel panel = (JPanel) window.getContentPane();
		panel.setPreferredSize(new Dimension(1000,800));
		panel.setLayout(new BorderLayout());
		
		info = new JPanel();
		info.setBounds(0,0,500,400);
		info.setPreferredSize(new Dimension(400,800));
		info.setBackground(Color.GRAY);
		info.setBorder(BorderFactory.createLineBorder(Color.black));
		
		
		/*
		text = new JTextArea();
		text.setWrapStyleWord(true);
		text.setLineWrap(true);
		text.setText("sample");
		text.setPreferredSize(new Dimension(345,795));
		text. setEditable(false);
		JScrollPane jsp = new JScrollPane(text);
		info.add(text);
		
		*/
		
		
		panel.add(info, BorderLayout.EAST);
		setBounds(0,0,1000,800);
		window.add(this);
			
		
		//Build the menu
		menu(window);
		
		
		text = new JTextPane();
		JScrollPane scroll = new JScrollPane(text);
		HTMLEditorKit kit = new HTMLEditorKit();
		HTMLDocument doc = new HTMLDocument();
		text.setEditorKit(kit);
		text.setDocument(doc);
		
		
		text.setPreferredSize(new Dimension(345,795));
		info.add(scroll,BorderLayout.EAST);
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

		scroll.validate();
		window.validate();
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIgnoreRepaint(true);
		window.pack();
	    window.setVisible(true);
	    window.setResizable(false);
			
		createBufferStrategy(2);
		strategy = getBufferStrategy();
	    addMouseListener(this);  
	    requestFocus();
	    initWin();
	    
	}
	
	class OpenAction implements ActionListener
{
			public void actionPerformed(ActionEvent ae)
{
            //... Open a file dialog.
            int retval = _fileChooser.showOpenDialog(sidePanel.this);
            if (retval == JFileChooser.APPROVE_OPTION)
            {
                //... The user selected a file, get it, use it.
                File file = _fileChooser.getSelectedFile();

                //... Update user interface.
                _fileNameTF.setText(file.getName());
            }
        }
	
	
}
	public void programLoop()
	{
		while (running) {
			xy =  MouseInfo.getPointerInfo().getLocation();
			xpos = xy.getX();
			xpos = xy.getY();
			
			updateInfoPanel();
			drawNodes();
			// 100 fps 
			try { Thread.sleep(10); } catch (Exception e) {}
		}
	}
	public void updateInfoPanel() 
	{
		Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
		
		if(oval1)
		{
			 text.setText("oval 1");
			
		}
		else if (oval2)
		{
			 
		     text.setText(txt);
			
			//try
			{
				 //image = ImageIO.read(new File("images/velocity.jpg"));
				}
			// catch (IOException e)
			{
				 //System.out.println("Error: Image not found");
			}

			  //JLabel img = new JLabel(new ImageIcon(image));
			  //info.add(img); 
		}
		else if(oval3)
		{
			 text.setText("oval 3");
		}
		g.dispose();
		strategy.show();
	
	}
	public void drawNodes()
	{
		Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
		g.setColor(Color.black);
		g.fillOval(100,100,50,50);
		g.fillOval(200,200,50,50);
		g.fillOval(50,50,50,50);
		g.dispose();
		strategy.show();
		
	}
	
	//Initialize the window with default dummy nodes
	public void initWin()
	{
		Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
		g.setColor(Color.black);
		g.fillOval(0,0,50,50);
		
		g.dispose();
		strategy.show();
		
		running = true ;
		
		txt = new fileParser().fileToString("test.txt");
		//try
		//{
   		//	image= new imgWrapper(ImageIO.read(new File("images/velocity.jpg")));
		//	}
		// catch (IOException e)
		//{
		//	 System.out.println("Error: Image not found");
		//}
		programLoop();
	}
	
	public void menu(JFrame window){
		//-------menu----------------------------------------
		//Where the GUI is created:
		JMenuBar menuBar;
		JMenu menu, submenu;
		JMenuItem menuItem;
		JRadioButtonMenuItem rbMenuItem;
		JCheckBoxMenuItem cbMenuItem;

		//Create the menu bar.
		menuBar = new JMenuBar();

		//Build the first menu.
		menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		//menu.getAccessibleContext().setAccessibleDescription(
		// "The only menu in this program that has menu items");
		menuBar.add(menu);

		//a group of JMenuItems
		menuItem = new JMenuItem("Open File...",
		KeyEvent.VK_O);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(
		KeyEvent.VK_1, ActionEvent.ALT_MASK));
		//menuItem.getAccessibleContext().setAccessibleDescription(
		// "This doesn't really do anything");
		menuItem.addActionListener(new OpenAction());
		menu.add(menuItem);

		menuItem = new JMenuItem("Both text and icon",
		new ImageIcon("images/middle.gif"));
		menuItem.setMnemonic(KeyEvent.VK_B);
		menu.add(menuItem);

		menuItem = new JMenuItem(new ImageIcon("images/middle.gif"));
		menuItem.setMnemonic(KeyEvent.VK_D);
		menu.add(menuItem);

		//a group of radio button menu items
		menu.addSeparator();
		ButtonGroup group = new ButtonGroup();
		rbMenuItem = new JRadioButtonMenuItem("A radio button menu item");
		rbMenuItem.setSelected(true);
		rbMenuItem.setMnemonic(KeyEvent.VK_R);
		group.add(rbMenuItem);
		menu.add(rbMenuItem);

		rbMenuItem = new JRadioButtonMenuItem("Another one");
		rbMenuItem.setMnemonic(KeyEvent.VK_O);
		group.add(rbMenuItem);
		menu.add(rbMenuItem);

		//a group of check box menu items
		menu.addSeparator();
		cbMenuItem = new JCheckBoxMenuItem("A check box menu item");
		cbMenuItem.setMnemonic(KeyEvent.VK_C);
		menu.add(cbMenuItem);

		cbMenuItem = new JCheckBoxMenuItem("Another one");
		cbMenuItem.setMnemonic(KeyEvent.VK_H);
		menu.add(cbMenuItem);

		//a submenu
		menu.addSeparator();
		submenu = new JMenu("A submenu");
		submenu.setMnemonic(KeyEvent.VK_S);

		menuItem = new JMenuItem("An item in the submenu");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(
		KeyEvent.VK_2, ActionEvent.ALT_MASK));
		submenu.add(menuItem);

		menuItem = new JMenuItem("Another item");
		submenu.add(menuItem);
		menu.add(submenu);

		//Build second menu in the menu bar.
		menu = new JMenu("Another Menu");
		menu.setMnemonic(KeyEvent.VK_N);
		menu.getAccessibleContext().setAccessibleDescription(
		"This menu does nothing");
		menuBar.add(menu);

		window.setJMenuBar(menuBar);
		//-------end menu------------------------------------	   
	}
	
	public void mouseClicked(MouseEvent e) {
		
		xpos = e.getX();
		ypos = e.getY();
		double x , y ;
		x= xpos - 100;
		y= ypos - 100;
		if  (50*50 >= x*x +y *y){ 
			oval1 = true;
			
		}
		else
		oval1 = false;
		
		x= xpos - 200; 
		y= ypos - 200;
		if (50*50 >= x*x +y *y) {
			
			oval2 = true;
		}
		else
		oval2 = false;
		
		x= xpos - 50; 
		y= ypos - 50;
		if (50*50 >= x*x +y *y) {
			
			oval3 = true;
		}
		else
		oval3 = false;
			   
	}
	
	public void mouseEntered(MouseEvent e) {}
	
	public void mouseExited(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {}
	
	public void mouseReleased(MouseEvent e) {}

}

	
