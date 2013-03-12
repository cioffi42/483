import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
 
import javax.swing.JFrame;
import javax.swing.JPanel;
 
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
 
/**
 * A simple canvas that encapsulates a SWT Browser instance.
 * Add it to a AWT or Swing container and call "connect()" <b>after</b>
 * the container has been made visible.
 */
public class BrowserCanvas extends Canvas {
 
	public BrowserCanvas()
	{
		Display display = new Display();
        final Shell shell = new Shell(display);
        final Browser browser = new Browser(shell, SWT.NONE);
        browser.setBounds(5, 75, 500, 500);
        
        shell.open();
        String path1 = this.getClass().getResource("asdfasdfadf.htm").toString();
        System.out.print("fail");
        System.out.print(path1);
        browser.setUrl(path1);
        while (!shell.isDisposed())
        {
        	if (!display.readAndDispatch())
        		display.sleep();
        }
        display.dispose();
	}
}