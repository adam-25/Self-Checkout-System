package seng300.software.GUI;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;

public class KeyboardButton extends JButton
{
	private KeyboardKey key;
	
	public KeyboardButton(KeyboardKey value)
	{
		super(String.valueOf(value.getValue()));
		this.key = value;
		if (value == KeyboardKey.ENTER)
		{
			setText("ENTER");
		}
		else if (value == KeyboardKey.CLEAR)
		{
			setText("CLEAR");
		}
		else if (value == KeyboardKey.SPACE)
		{
			setText("SPACE");
		}
		else if (value == KeyboardKey.BACK)
		{
			setText("BACK");
		}
		setForeground(new Color(255, 255, 255));
		setBackground(new Color(25, 25, 112));
		setFont(new Font("Tahoma", Font.BOLD, 24));
	}
	
	public KeyboardKey getKey()
	{
		return this.key;
	}
}