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

	public static int MAX_TIME = 90;
	
	protected TextField fld = null;
	protected String solution = null;
	protected String solutionTransl = null;
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
	private Menu menu;
	
	int numSuccessQuestions = 0;
	
	public GameForm(Menu curMenu) {
		super(null);

		game = this;
		menu = curMenu;
		
		loadQuestions();
		
		timeMsg = new StringItem(null, "Осталось XX сек.");
		this.append(timeMsg);
		
		gameTimer = new Timer();
		
		textMsg = new StringItem(null, "Вопрос");
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
		solutionTransl = ((String[])questions.elementAt(num)).length > 2 ? ((String[])questions.elementAt(num))[2]: "";
		elapsedTime = MAX_TIME;
		timeMsg.setText("Осталось " + elapsedTime + " сек.");
		fld.setString("");

		if (gameTask != null)
			try {
				gameTask.cancel();
			} catch (Exception ex) {};
			
		gameTask = new TimerTask() {
			public void run() {
				System.err.println("Осталось " + game.elapsedTime + " сек.");
				game.elapsedTime--;
				game.timeMsg.setText("Осталось " + game.elapsedTime + " сек.");
				if (game.elapsedTime <= 0) {
					this.cancel();
					game.menu.manager.back();
					game.stopGame();
				}
				if (game.elapsedTime == Math.abs(MAX_TIME/2))
					game.showTip();
			}
		};
		gameTimer.schedule(gameTask, 1000, 1000);
		currentTip = null;
		fld.setConstraints((fld.getConstraints() & TextField.CONSTRAINT_MASK));
	}

	protected void checkSolution()
	{
		if (fld.getString().equalsIgnoreCase(this.solution))
		{
			System.err.println("good");
			errorMsg.setText("Правильно!");
			fld.setConstraints((fld.getConstraints() & TextField.CONSTRAINT_MASK)|TextField.UNEDITABLE);
			this.numSuccessQuestions++;
			gameTimer.schedule(new TimerTask(){public void run() {game.nextQuestion();}}, 50);
		}
		else
			if (fld.getString().equalsIgnoreCase(this.solutionTransl) && this.solutionTransl.length() > 0)
			{
				System.err.println("good");
				errorMsg.setText("Правильно!");
				fld.setConstraints((fld.getConstraints() & TextField.CONSTRAINT_MASK)|TextField.UNEDITABLE);
				this.numSuccessQuestions++;
				gameTimer.schedule(new TimerTask(){public void run() {game.nextQuestion();}}, 50);
			} else {
				System.err.println("bad");
				errorMsg.setText("Неправильно!" + (this.currentTip != null ? " (" + this.currentTip +")" : ""));
		}
	}

	public void itemStateChanged(Item item) {
		if (item == fld) {
			if (fld.getString().indexOf("\n") >= 0) {
				String st = fld.getString().substring(0, fld.getString().indexOf("\n")) + 
						fld.getString().substring(fld.getString().indexOf("\n") + 1, fld.getString().length());
				fld.setString(st);
				checkSolution();
			} else {
				System.err.println("empty string");
				errorMsg.setText((this.currentTip != null ? this.currentTip + " " : "") + "Для проверки нажмите ввод");
			}
		}
	}
	
	public void stopGame() {
		System.err.println("stop");
		fld.setConstraints((fld.getConstraints() & TextField.CONSTRAINT_MASK)|TextField.UNEDITABLE);
		gameTask.cancel();
		if (this.numSuccessQuestions > 0 || this.elapsedTime <= 0)
			menu.showResult();
		else
			menu.hideResult();
	}
	
	public void startGame() {
		this.numSuccessQuestions = 0;
		this.nextQuestion();
		errorMsg.setText("Для проверки нажмите ввод");
	}
	
	public void showTip() {
		char[] tip = solution.toCharArray();
		for(int i = 0; i < tip.length; i++) {
			if (Math.abs(rnd.nextInt() % 2) > 0) {
				tip[i] = '*';
			}
		}
		currentTip = new String(tip);
		
		errorMsg.setText("Подсказка: \"" + currentTip + "\".");
	}
	
	private void loadQuestions() {
		questions = new Vector();
		questions.addElement(new String[]{"Два кольца, два конца, в середине гвоздик.", "Ножницы", "Nozhnicy"});
		questions.addElement(new String[]{"Шубу два раза в год снимает. Кто под шубою гуляет?", "Овца", "Ovca"});
		questions.addElement(new String[]{"Не прядёт, не ткёт, а людей одевает.", "Овца", "Ovca"});
		questions.addElement(new String[]{"По горам, по долам ходит шуба да кафтан.", "Овца", "Ovca"});
		questions.addElement(new String[]{"Заплелись густые травы, закудрявились рога, да и сам я весь кудрявый, даже завитком рога.", "Баран", "Baran"});
		questions.addElement(new String[]{"Явился в жёлтой шубке:-Прощайте, две скорлупки!", "Цыплёнок", "Cyplyonok"});
		questions.addElement(new String[]{"Она на белых камушках сидит, не подходите близко - закричит.", "Наседка", "Nasedka"});
		questions.addElement(new String[]{"Каким гребешком никто не причёсывается?", "Петушиным", "Petushinym"});
		questions.addElement(new String[]{"Молчан-собака весь дом стережет.", "Замок", "Zamok"});
		questions.addElement(new String[]{"Маленький мальчик всем под ноги смотрит.", "Порог", "Porog"});
		questions.addElement(new String[]{"Ни на меру, ни на вес, а у всех людей есть.", "Ум", "Um"});
		questions.addElement(new String[]{"Мету, мету — не вымету; несу, несу — не вынесу; пора придет, сама уйдет.", "Тень", "Ten'"});
		questions.addElement(new String[]{"Черная корова весь мир поборола.", "Ночь","Noch'"});
		questions.addElement(new String[]{"Шел долговяз, во сыру землю увяз.", "Дождь", "Dozhd'"});
		questions.addElement(new String[]{"Из окна в окно золотое веретено.", "Луч", "Luch"});
		questions.addElement(new String[]{"Заря заряница, красная девица, врата запирала, по полю гуляла, ключи потеряла, месяц видел, а солнце крало.", "Роса", "Rosa"});
		questions.addElement(new String[]{"Выгляну в окошко: лежит долгий Антошка. Кабы он встал — до неба достал; Сам не ходит, а других водит", "Дорога", "Doroga"});
		questions.addElement(new String[]{"Сам не видит, а другому указывает.", "Столб", "Stolb"});
		questions.addElement(new String[]{"В новой стене, в круглом окне днем стекло разбито, за ночь вставлено.", "Прорубь", "Prorub'"});
		questions.addElement(new String[]{"На улице столбом, в избе скатертью.", "Дым", "Dym"});
		questions.addElement(new String[]{"Горенка нова, головка черна, шапочка золоченая.", "Свеча", "Svecha"});
		questions.addElement(new String[]{"Сам худ, голова с пуд.", "Молот", "Molot"});
		questions.addElement(new String[]{"Кто в избе рогат?", "Ухват", "Uxvat"});
		questions.addElement(new String[]{"Черный конь прыгает в огонь.", "Кочерга", "Kocherga"});
		questions.addElement(new String[]{"Скоро ест, мелко жует, сама не ест, другим не дает.", "Пила", "Pila"});
		questions.addElement(new String[]{"Кланяется, кланяется, придет домой — растянется.", "Топор", "Topor"});
		questions.addElement(new String[]{"Два брюшка, четыре ушка.", "Подушка", "Podushka"});
		questions.addElement(new String[]{"Без рук, без ног, а рубашку носит.", "Подушка", "Podushka"});
		questions.addElement(new String[]{"Четыре брата под одной шляпой стоят, одним кушаком обвязаны.", "Стол", "Stol"});
		questions.addElement(new String[]{"Идут, идут, а с места не сойдут.", "Часы", "Chasy"});
		questions.addElement(new String[]{"Зелененький, маленький, по полу елозит, себя не занозит. Обежал весь теремок и опять встал в уголок.", "Веник", "Venik"});
		questions.addElement(new String[]{"Маленький Ерофейка, подпоясан коротенько, по полу скок-скок, по лавкам скок-скок и сел в уголок.", "Веник", "Venik"});
		questions.addElement(new String[]{"Стоит толстячок, подбоченивши бочок, шипит и кипит, всем пить чай велит.", "Самовар", "Samovar"});
		questions.addElement(new String[]{"Умный Ивашка, красная рубашка, где пройдет—коснется, там след остается.", "Карандаш", "Karandash"});
		questions.addElement(new String[]{"Книги читают, а грамоты не знают. Своих глаз нет, а видеть помогают свет.", "Очки", "Ochki"});
		questions.addElement(new String[]{"Маленький Ерофейка утонул до шейки.", "Гвоздь", "Gvozd'"});
		questions.addElement(new String[]{"В брюхе баня, в носу решето, на голове пупок, всего одна рука, и та на спине.", "Чайник", "Chajnik"});
		questions.addElement(new String[]{"Маленький, кругленький, а за хвост не поймаешь.", "Клубок", "Klubok"});
		questions.addElement(new String[]{"Скорчится в кошку, растянется в дорожку.", "Клубок", "Klubok"});
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
