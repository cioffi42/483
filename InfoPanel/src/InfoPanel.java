import java.sql.*;
import java.util.Properties;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleContext;
import javax.swing.text.StyleConstants;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

public class InfoPanel extends JFrame
{
	
	JTextField   _fileNameTF  = new JTextField(15);
    JFileChooser _fileChooser = new JFileChooser();
    
	public InfoPanel()
	{
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String query = "";
		
		GridLayout infoLayout = new GridLayout(0,1);
		
		//JFrame frame = new JFrame("Info Panel");
		this.setVisible(true);
		this.setTitle("Info Panel");
		this.setSize(500,500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
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
		//        "The only menu in this program that has menu items");
		menuBar.add(menu);

		//a group of JMenuItems
		menuItem = new JMenuItem("Open File...",
		                         KeyEvent.VK_O);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_1, ActionEvent.ALT_MASK));
		//menuItem.getAccessibleContext().setAccessibleDescription(
		//        "This doesn't really do anything");
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

		this.setJMenuBar(menuBar);
		//-------end menu------------------------------------
		
		try
		{
			conn = getConnection();
			stmt = conn.createStatement();
			
			query = "select * from data";
			rs = stmt.executeQuery(query);
			/*while (rs.next()) {
                System.out.println(rs.getString(1));
                System.out.println(rs.getString(2));
             }*/
			
			rs.next();
			String desc = rs.getString(1);
			String desc2 = rs.getString(2);
			String path1 = rs.getString(3);
			String path2 = rs.getString(4);
			
			/*JPanel panel = new JPanel();
			panel.setLayout(infoLayout);*/
			
			JTextPane text = new JTextPane(); 
			JScrollPane scroll = new JScrollPane(text);
			HTMLEditorKit kit = new HTMLEditorKit();
			HTMLDocument doc = new HTMLDocument();
			text.setEditorKit(kit);
			text.setDocument(doc);
			this.add(scroll);
			
			path1 = this.getClass().getResource(path1).toString();
			path2 = this.getClass().getResource(path2).toString();
			
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
			
			//Style style = doc.addStyle("StyleName", null);
			
			//java.net.URL imgurl = getClass().getResource("imagename");
			
			/*try
			{
			    doc.insertString(0, "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
						+  "<b>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum." +
						"</b>", null );
			    doc.insertIcon("http://www.jennyskuali.com/images/logo.png");
			    doc.insertString(doc.getLength(), "\nEnd of text", null);
			}
			catch(Exception e) { System.out.println(e); }*/
	
			/*text.setText("<table border='0' align='right' cellpadding='5' cellspacing='0'> <tr> <td align='center' valign='top'>Caption Area<br> <img src='http://www.jennyskuali.com/images/logo.png' align='left'/> <br> Caption Area</td><td><b>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</b></td> </tr> </table>"
					+  "<b>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum." +
					"</b>");*/
			 
			
			System.out.println(path1);
			
			/*try { Lorem ipsu
				doc.insertString(doc.getLength(), "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.", style);
				StyleConstants.setIcon(style, new ImageIcon("imagefile"));
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
			text.setVisible(true);
			text.setEditable(false);
			
			
		/*	JScrollPane scroll = new JScrollPane(panel);
			this.add(scroll);
			panel.add(label);
			panel.add(image1);
			panel.add(label2);
			panel.add(image2);*/
			scroll.validate();
			this.validate();
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{ // close db resources
			try
			{
		        rs.close();
		        stmt.close();
		        conn.close();
		    }
			catch (Exception e){}

		}
	}
	public static Connection getConnection()
	{
	    // load the MySQL JDBC Driver
		Connection conn = null;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			
			String url = "jdbc:mysql://localhost:3306/infopaneldata";
			String usr = "root";
			String pwd = "";
			conn = DriverManager.getConnection(url, usr, pwd);
			System.out.println("Connected to the database");

		}
		catch (ClassNotFoundException e)
		{
			System.out.print("Class not found\n");
			System.out.print(e.getMessage());
		}
		catch (SQLException e)
		{
			System.out.print("SQLException\n");
			e.printStackTrace();
		}
		
	    // define database connection parameters
	    return conn;
	}
	
	public static String getURL(String path)
	{
		String filename = new InfoPanel().getClass().getClassLoader().getResource(path).toString();
		return filename;
	}
	
	private Image loadImage (String pad){
        return Toolkit.getDefaultToolkit().getImage(getClass().getResource(pad));
    }
	
	class OpenAction implements ActionListener
	{
		public void actionPerformed(ActionEvent ae)
		{
            //... Open a file dialog.
            int retval = _fileChooser.showOpenDialog(InfoPanel.this);
            if (retval == JFileChooser.APPROVE_OPTION)
            {
                //... The user selected a file, get it, use it.
                File file = _fileChooser.getSelectedFile();

                //... Update user interface.
                _fileNameTF.setText(file.getName());
            }
        }
    }
	
	public static void main (String[] args)
	{
		JFrame window = new InfoPanel();
        window.setVisible(true);
	}
}
