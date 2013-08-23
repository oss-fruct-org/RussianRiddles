package org.fruct.oss.russianriddles;

import javax.microedition.lcdui.Form;
import org.fruct.oss.russianriddles.elements.*;

public class Menu extends Form {
	
    private static final int LIST_ROW_HEIGHT = 40;
    
    private MenuItem gameItem;
    private MenuItem helpItem;
    private MenuItem aboutItem;
	
	public Menu() {
		super ("������� �������");
		gameItem = new MenuItem("����", this.getWidth(), LIST_ROW_HEIGHT);
		gameItem.setMenu(this);
		append(gameItem);
		helpItem = new MenuItem("�������", this.getWidth(), LIST_ROW_HEIGHT);
		helpItem.setMenu(this);
		append(helpItem);
		aboutItem = new MenuItem("� ���������", this.getWidth(), LIST_ROW_HEIGHT);
		aboutItem.setMenu(this);
		append(aboutItem);
	}
	
	public void itemClicked(MenuItem itm) {
		if (itm == gameItem) {
			System.err.println("Start game");
		}else if (itm == helpItem) {
			System.err.println("Show help");
		}else
			System.err.println("Show about");
	}
}
