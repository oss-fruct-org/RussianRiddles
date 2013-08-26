package org.fruct.oss.russianriddles;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.ItemStateListener;
import javax.microedition.lcdui.StringItem;
import javax.microedition.lcdui.TextField;

public class GameForm extends Form implements ItemStateListener {

	public static int MAX_TIME = 100;
	
	protected TextField fld = null;
	protected String solution = null;
	protected String currentTip = null;
	
	protected StringItem textMsg = null;
	protected StringItem errorMsg = null;
	public StringItem timeMsg = null;
	protected Timer gameTimer = null;
	protected TimerTask gameTask = null;
	public int elapsedTime = 0;
	
	public static GameForm game;
	
	private Vector questions = null;
	private int questionsLength = 0;
	Random rnd = new Random();
	
	int numSuccessQuestions = 0;
	
	public GameForm() {
		super(null);

		game = this;
		
		loadQuestions();
		
		timeMsg = new StringItem(null, "�������� XX ���.");
		this.append(timeMsg);
		
		gameTimer = new Timer();
		gameTask = new TimerTask() {
			public void run() {
				System.err.println("�������� " + game.elapsedTime + " ���.");
				game.elapsedTime--;
				game.timeMsg.setText("�������� " + game.elapsedTime + " ���.");
				if (game.elapsedTime <= 0) {
					this.cancel();
					game.stopGame();
				}
				if (game.elapsedTime == Math.abs(MAX_TIME/2))
					game.showTip();
			}
		};
		
		textMsg = new StringItem(null, "������");
		textMsg.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
		this.append(textMsg);
		
		fld = new TextField(null, null, 20, TextField.ANY);
		this.append(fld);
		fld.setInitialInputMode("UCB_CYRILLIC");
		//fld.setItemCommandListener(this);
		this.setItemStateListener(this);
		
		errorMsg = new StringItem(null, null);
		this.append(errorMsg);
}
	
	private void nextQuestion() {
		int num = Math.abs(rnd.nextInt()) % questionsLength;
		textMsg.setText(((String[])questions.elementAt(num))[0]);
		solution = ((String[])questions.elementAt(num))[1];
		elapsedTime = MAX_TIME;
		timeMsg.setText("�������� " + elapsedTime + " ���.");
		gameTimer.schedule(gameTask, 1000, 1000);
		currentTip = null;
		fld.setConstraints((fld.getConstraints() & TextField.CONSTRAINT_MASK));
	}

	protected void checkSolution()
	{
		if (fld.getString().equalsIgnoreCase(this.solution))
		{
			System.err.println("good");
			errorMsg.setText("���������!");
			fld.setConstraints((fld.getConstraints() & TextField.CONSTRAINT_MASK)|TextField.UNEDITABLE);
			this.numSuccessQuestions++;
			gameTimer.schedule(new TimerTask(){public void run() {game.nextQuestion();}}, 50);
		}
		else {
			System.err.println("bad");
			errorMsg.setText("�����������!" + (this.currentTip != null ? " (" + this.currentTip : ")"));
		}
	}

	public void itemStateChanged(Item item) {
		// TODO Auto-generated method stub
		if (item == fld) {
			if (fld.getString().endsWith("\n")) {
				fld.setString(fld.getString().substring(0, fld.getString().length() - 1));
				checkSolution();
			} else {
				System.err.println("empty string");
				errorMsg.setText((this.currentTip != null ? this.currentTip + " " : "") + "��� �������� ������� ����");
			}
		}
	}
	
	public void stopGame() {
		System.err.println("stop");
		fld.setConstraints((fld.getConstraints() & TextField.CONSTRAINT_MASK)|TextField.UNEDITABLE);
	}
	
	public void startGame() {
		this.numSuccessQuestions = 0;
		this.nextQuestion();
		errorMsg.setText("��� �������� ������� ����");
	}
	
	public void showTip() {
		char[] tip = solution.toCharArray();
		for(int i = 0; i < tip.length; i++) {
			if (Math.abs(rnd.nextInt() % 2) > 0) {
				tip[i] = '*';
			}
		}
		currentTip = new String(tip);
		errorMsg.setText("���������: \"" + currentTip + "\".");
	}
	
	private void loadQuestions() {
		questions = new Vector();
		questions.addElement(new String[]{"��� ������, ��� �����, �� �������� �������.", "�������"});
		questions.addElement(new String[]{"���� ��� ���� � ��� �������. ��� ��� ����� ������?", "����"});
		questions.addElement(new String[]{"�� �����, �� ���, � ����� �������.", "����"});
		questions.addElement(new String[]{"�� �����, �� ����� ����� ���� �� ������.", "����"});
		questions.addElement(new String[]{"��������� ������ �����, ������������� ����, �� � ��� � ���� ��������, ���� �������� ����.", "�����"});
		questions.addElement(new String[]{"������ � ����� �����:-��������, ��� ���������!", "�������"});
		questions.addElement(new String[]{"��� �� ����� �������� �����, �� ��������� ������ - ��������.", "�������"});
		questions.addElement(new String[]{"����� ��������� ����� �� �������������?", "���������"});
		//questions.addElement(new String[]{"", ""});
		//questions.addElement(new String[]{"", ""});
		//questions.addElement(new String[]{"", ""});
		//questions.addElement(new String[]{"", ""});
		//questions.addElement(new String[]{"", ""});
		//questions.addElement(new String[]{"", ""});
		//questions.addElement(new String[]{"", ""});
		//questions.addElement(new String[]{"", ""});
		//questions.addElement(new String[]{"", ""});
		//questions.addElement(new String[]{"", ""});
		
		questionsLength = questions.size();
		System.err.println("questions:" + questionsLength);
	}
}
