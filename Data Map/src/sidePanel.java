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
public class sidePanel extends Canvas {

	private Thread swtThread;
	private Browser swtBrowser;

	/**
	 * Connect this canvas to a SWT shell with a Browser component
	 * and starts a background thread to handle SWT events. This method
	 * waits until the browser component is ready.
	 */
	public void connect() {
		if (this.swtThread == null) {
			final Canvas canvas = this;
			this.swtThread = new Thread() {
				@Override
				public void run() {
					try {
						Display display = new Display();
						Shell shell = SWT_AWT.new_Shell(display, canvas);
						shell.setLayout(new FillLayout());

						synchronized (this) {
							swtBrowser = new Browser(shell, SWT.NONE);
							swtBrowser.setUrl("http://www.google.com");
							shell.setFullScreen(true);
							shell.pack();
							this.notifyAll();
						}

						shell.open();
						while (!isInterrupted() && !shell.isDisposed()) {
							if (!display.readAndDispatch()) {
								display.sleep();
							}
						}
						shell.dispose();
						display.dispose();
					} catch (Exception e) {
						interrupt();
					}
				}
			};
			this.swtThread.start();
		}

		// Wait for the Browser instance to become ready
		synchronized (this.swtThread) {
			while (this.swtBrowser == null) {
				try {
					this.swtThread.wait(100);
				} catch (InterruptedException e) {
					this.swtBrowser = null;
					this.swtThread = null;
					break;
				}
			}
		}
	}

	/**
	 * Returns the Browser instance. Will return "null"
	 * before "connect()" or after "disconnect()" has
	 * been called.
	 */
	public Browser getBrowser() {
		return this.swtBrowser;
	}

	/**
	 * Stops the swt background thread.
	 */
	public void disconnect() {
		if (swtThread != null) {
			swtBrowser = null;
			swtThread.interrupt();
			swtThread = null;
		}
	}

	/**
	 * Ensures that the SWT background thread
	 * is stopped if this canvas is removed from
	 * it's parent component (e.g. because the
	 * frame has been disposed).
	 */
	@Override
	public void removeNotify() {
		super.removeNotify();
		disconnect();
	}
}
	

