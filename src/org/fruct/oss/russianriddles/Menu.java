package org.fruct.oss.russianriddles;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import org.fruct.oss.russianriddles.elements.*;

public class Menu extends Form implements CommandListener{
	
    private static final int LIST_ROW_HEIGHT = 40;
    
    private MenuItem gameItem;
    private MenuItem helpItem;
    private MenuItem aboutItem;

    private DisplayManager manager;
	private Command	 back;
    
    AboutForm aboutForm = null;
    
    GameForm gameForm = null;
	
	public Menu(Display displ) {
		super ("Русские загадки");

		this.manager = new DisplayManager(displ);
		gameItem = new MenuItem("Новая игра", this.getWidth(), LIST_ROW_HEIGHT);
		gameItem.setMenu(this);
		append(gameItem);
		helpItem = new MenuItem("Справка", this.getWidth(), LIST_ROW_HEIGHT);
		helpItem.setMenu(this);
		append(helpItem);
		aboutItem = new MenuItem("О программе", this.getWidth(), LIST_ROW_HEIGHT);
		aboutItem.setMenu(this);
		append(aboutItem);
		
		this.back = new Command("Back", Command.BACK, 1);
		this.manager.add(this);
	}
	
	public void itemClicked(MenuItem itm) {
		if (itm == gameItem) {
			System.err.println("Start game");
			if (gameForm == null) {
				gameForm = new GameForm();
				gameForm.setCommandListener(this);
				gameForm.addCommand(this.back);
			}
			this.manager.next(gameForm);
		}else if (itm == helpItem) {
			System.err.println("Show help");
		}else {
			if (aboutForm == null){
				aboutForm = new AboutForm();
				aboutForm.setCommandListener(this);
				aboutForm.addCommand(this.back);
			}
			this.manager.next(aboutForm);
		}
			
	}

	public void commandAction(Command c, Displayable d) {
		// TODO Auto-generated method stub
		if (c == this.back) {
			this.manager.back();
		}

	}
}
