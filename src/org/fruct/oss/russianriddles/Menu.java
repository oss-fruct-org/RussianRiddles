package org.fruct.oss.russianriddles;

import javax.microedition.lcdui.Form;
import org.fruct.oss.russianriddles.elements.*;

public class Menu extends Form {
	
    private static final int LIST_ROW_HEIGHT = 40;
	
	public Menu() {
		super ("������� �������");
		MenuItem gameItem = new MenuItem("����", this.getWidth(), LIST_ROW_HEIGHT);
		append(gameItem);
		MenuItem helpItem = new MenuItem("�������", this.getWidth(), LIST_ROW_HEIGHT);
		append(helpItem);
		MenuItem aboutItem = new MenuItem("� ���������", this.getWidth(), LIST_ROW_HEIGHT);
		append(aboutItem);
	}
}
