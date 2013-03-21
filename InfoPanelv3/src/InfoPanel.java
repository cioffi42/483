
import java.awt.Canvas;
import java.awt.EventQueue;

import javax.swing.JFrame;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class InfoPanel {

static Display display;
static boolean exit;
String path1;

public InfoPanel() {
	 path1 = this.getClass().getResource("asdfasdfadf.htm").toString();
	 createAndShowBrowser();
}



public void createAndShowBrowser() {
    JFrame f = new JFrame("Main Window");
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    final Canvas canvas = new Canvas();
    f.setSize(850, 650);
    f.getContentPane().add(canvas);
    f.setVisible(true);
    display.asyncExec(new Runnable() {

        @Override
        public void run() {
            Shell shell = SWT_AWT.new_Shell(display, canvas);
            shell.setSize(800, 600);
            Browser browser = new Browser(shell, SWT.NONE);
            browser.setLayoutData(new GridData(GridData.FILL_BOTH));
            browser.setSize(800, 600);
            browser.setUrl(path1);
            shell.open();
        }
    });
}

public static void main(String args[]) {
    //SWT_AWT.embeddedFrameClass = "sun.lwawt.macosx.CEmbeddedFrame";
    display = new Display();

    EventQueue.invokeLater(new Runnable() {

        @Override
        public void run() {
            InfoPanel mySWTBrowserTest = new InfoPanel();
        }
    });

    while (!exit) {
        if (!display.readAndDispatch()) {
            display.sleep();
        }
    }
    display.dispose();
}
}