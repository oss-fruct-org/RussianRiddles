package org.fruct.oss.russianriddles;

import java.io.IOException;

import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.ImageItem;

public class AboutForm extends Form{
	
	public AboutForm() {
		super("О программе");
		try {
			this.append(new ImageItem(null, Image.createImage("/podkova.png"), ImageItem.LAYOUT_CENTER, null));
		} catch (IOException ex) {}
		this.append("Приложение Riddles \"Русские загадки\" реализовано в Лаборатории ПетрГУ FRUCT \"PetrSU FRUCT lab.\" (с) 2013");
		this.append("Версия: 1.0.0");
		this.append("Основной разработчик: Кирилл Кулаков");
		this.append("Лицензия: GPL2");
		this.append("Исходный код: https://github.com/seekerk/RussianRiddles");
	}
}
