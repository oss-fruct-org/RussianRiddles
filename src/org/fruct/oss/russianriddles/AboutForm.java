package org.fruct.oss.russianriddles;

import javax.microedition.lcdui.Form;

public class AboutForm extends Form{
	
	public AboutForm() {
		super("О программе");
		
		this.append("Приложение реализовано в Лаборатории ПетрГУ FRUCT (с) 2013");
		this.append("Основной разработчик: Кирилл Кулаков");
		this.append("Лицензия: GPL2");
		this.append("Исходный код: https://github.com/seekerk/RussianRiddles");
	}
}
