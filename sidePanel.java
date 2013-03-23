/* Muneeb Ul Haq
*  
*  A browser canvas that loads an HTML file into a browser
*  
*  IMPORTANT NOTE: The panel will not display until its "go" 
*                  function is called. DO NOT call the function
*				   before adding the sidePanel Object to a frame.
*				   
*				   Otherwise, the program will report an error and
*				   will not run.
*/



import java.awt.Canvas;


import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.FillLayout;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class sidePanel extends Canvas {

	static Display display;
	static boolean exit;
	String path1;

	public  sidePanel() {
		
	}
	public void  go(){
		//path1 = this.getClass().getResource("New Microsoft Word Document.htm").toString();
				//SWT_AWT.embeddedFrameClass = "sun.lwawt.macosx.CEmbeddedFrame";
						display = new Display();
						
						
						final Canvas canvas = this;
					
						display.asyncExec(new Runnable() {

							@Override
							public void run() {
								Shell shell = SWT_AWT.new_Shell(display, canvas);
								shell.setLayout(new FillLayout());
								Browser browser = new Browser(shell, SWT.NONE);
								browser.setUrl("www.google.com");
								shell.setFullScreen(true);
								shell.pack();
								shell.open();
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
