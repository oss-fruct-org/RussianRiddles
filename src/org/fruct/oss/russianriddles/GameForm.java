package org.fruct.oss.russianriddles;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.ItemCommandListener;
import javax.microedition.lcdui.TextField;

public class GameForm extends Form implements ItemCommandListener {

	protected TextField fld = null;
	protected String solution = null; 
	
	public GameForm() {
		super(null);

		String str = "До конца осталось чч сек.";
		this.append(str);
		
		fld = new TextField(null, null, 50, TextField.ANY);
		this.append(fld);
		fld.setInitialInputMode("UCB_CYRILLIC");
		fld.setItemCommandListener(this);
		this.nextQuestion();
	}
	
	private void nextQuestion() {
		fld.setLabel("Два кольца, два конца, по середине гвоздик");
		solution = "123";
	}

	public void commandAction(Command c, Item item) {
		// TODO Auto-generated method stub
		System.err.println("test");
		if (item == fld) {
			if (fld.getString().charAt(fld.getCaretPosition() - 1) == '\n') {
				fld.setString(fld.getString().substring(0, fld.getCaretPosition() - 1));
				checkSolution();
			}
		}
	}
	
	protected void checkSolution()
	{
		if (fld.getString().equalsIgnoreCase(this.solution))
			System.err.println("good");
		else
			System.err.println("bad");
	}
}
