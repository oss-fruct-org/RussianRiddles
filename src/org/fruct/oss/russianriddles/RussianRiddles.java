package org.fruct.oss.russianriddles;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class RussianRiddles extends MIDlet implements CommandListener {

	private Display display;
	private Splash  splash;
	private Command exit;
	
	public RussianRiddles() {
		this.display = Display.getDisplay(this);
		this.splash = new Splash("/company-logo.png", 0x000000);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.microedition.midlet.MIDlet#startApp()
	 */
	protected void startApp() throws MIDletStateChangeException {
		this.exit = new Command("Exit", Command.EXIT, 0x01);
		
		Displayable main = this.getMainScreen();
		main.setCommandListener(this);
		main.addCommand(this.exit);
		
		this.splash.show(this.display, main, 3000);
	}

	/**
	 * Gets the application main screen.
	 * 
	 * @return main screen.
	 */
	private Displayable getMainScreen() {
		//TODO: Implement the main screen here.
//		return new Canvas() {
//			protected void paint(Graphics g) {
//			}
//		};
		return new Menu(this.display);
	}

	/* (non-Javadoc)
	 * @see javax.microedition.lcdui.CommandListener#commandAction(javax.microedition.lcdui.Command, javax.microedition.lcdui.Displayable)
	 */
	public void commandAction(Command command, Displayable displayable) {
		if (command == this.exit) {
			try {
				this.destroyApp(true);
			} catch (MIDletStateChangeException e) {
				e.printStackTrace();
			} finally {				
				this.notifyDestroyed();
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.microedition.midlet.MIDlet#destroyApp(boolean)
	 */
	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.microedition.midlet.MIDlet#pauseApp()
	 */
	protected void pauseApp() {}
}
