package org.fruct.oss.russianriddles;


import javax.microedition.lcdui.Form;

public class HelpForm extends Form{
	
	public HelpForm() {
		super("�������");
		
		this.append("���������� ��������� ������� �������� �������. ���������� ������� �����, ��������� �� ������ �����, �� " + GameForm.MAX_TIME + " ������.");
		this.append("���� ����� �� ������ � ������� �������� �������, �� ������������ ���������, � ������� ����� ���� �������� �� ���������.");
		this.append("����� ����� ������� �������� ������� ��� ���������� (���� 7.79-2000).");
	}
}
