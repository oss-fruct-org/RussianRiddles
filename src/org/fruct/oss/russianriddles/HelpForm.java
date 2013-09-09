package org.fruct.oss.russianriddles;


import javax.microedition.lcdui.Form;

public class HelpForm extends Form{
	
	public HelpForm() {
		super("Справка");
		
		this.append("Приложение реализует русские народные загадки. Необходимо угадать ответ, состоящий из одного слова, за " + GameForm.MAX_TIME + " секунд.");
		this.append("Если ответ не введен в течении половины времени, то отображается подсказка, в которой часть букв заменены на звездочки.");
		this.append("Слова можно вводить русскими буквами или транслитом (ГОСТ 7.79-2000).");
	}
}
