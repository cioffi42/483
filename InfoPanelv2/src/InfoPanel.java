import javax.swing.JFrame;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.browser.Browser;


public class InfoPanel extends JFrame
{
	public InfoPanel()
	{
		this.setVisible(true);
		this.setTitle("Info Panel");
		this.setSize(500,500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		BrowserCanvas canvas = new BrowserCanvas();
		this.add(canvas);
		
		
		/*JTextPane text = new JTextPane(); 
		JScrollPane scroll = new JScrollPane(text);
		HTMLEditorKit kit = new HTMLEditorKit();
		HTMLDocument doc = new HTMLDocument();
		text.setEditorKit(kit);
		text.setDocument(doc);
		this.add(scroll);
		doc.putProperty("IgnoreCharsetDirective", new Boolean(true));
		String content = null;
		try
		{
			content = new Scanner(new File("asdfasdfadf.htm")).useDelimiter("\\Z").next();
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final CleanerProperties props = new CleanerProperties();
		final HtmlCleaner htmlCleaner = new HtmlCleaner(props);
		final SimpleHtmlSerializer htmlSerializer = 
		    new SimpleHtmlSerializer(props);

		TagNode node = htmlCleaner.clean(content);
		try {
			htmlSerializer.writeToFile(node, "new.html", "utf-8");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			kit.insertHTML(doc, doc.getLength(), content, 0, 0, null);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

	}
	
	public static void main(String[] args)
	{
		JFrame window = new InfoPanel();
        window.setVisible(true);
	}
}
