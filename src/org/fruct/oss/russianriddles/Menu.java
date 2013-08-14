package org.fruct.oss.russianriddles;

import javax.microedition.lcdui.Form;
import org.fruct.oss.russianriddles.elements.*;

public class Menu extends Form {
	
    private static final int LIST_ROW_HEIGHT = 40;
	
	public Menu() {
		super ("Русские загадки");
		MenuItem gameItem = new MenuItem("Игра", this.getWidth(), LIST_ROW_HEIGHT);
		append(gameItem);
		MenuItem helpItem = new MenuItem("Справка", this.getWidth(), LIST_ROW_HEIGHT);
		append(helpItem);
		MenuItem aboutItem = new MenuItem("О программе", this.getWidth(), LIST_ROW_HEIGHT);
		append(aboutItem);
	}
}
