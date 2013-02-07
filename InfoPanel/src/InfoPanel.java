import java.sql.*;
import java.util.Properties;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
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

public class InfoPanel
{
	public InfoPanel()
	{
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String query = "";
		
		GridLayout infoLayout = new GridLayout(0,1);
		
		JFrame frame = new JFrame("Info Panel");
		frame.setVisible(true);
		frame.setSize(500,500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
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
			frame.add(scroll);
			
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
			frame.add(scroll);
			panel.add(label);
			panel.add(image1);
			panel.add(label2);
			panel.add(image2);*/
			scroll.validate();
			frame.validate();
			
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
	
	public static void main (String[] args)
	{
		InfoPanel info = new InfoPanel();
	}
}
