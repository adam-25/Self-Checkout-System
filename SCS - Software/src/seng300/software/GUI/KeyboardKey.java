package seng300.software.GUI;

public enum KeyboardKey
{
	A('A'),
	B('B'),
	C('C'),
	D('D'),
	E('E'),
	F('F'),
	G('G'),
	H('H'),
	I('I'),
	J('J'),
	K('K'),
	L('L'),
	M('M'),
	N('N'),
	O('O'),
	P('P'),
	Q('Q'),
	R('R'),
	S('S'),
	T('T'),
	U('U'),
	V('V'),
	W('W'),
	X('X'),
	Y('Y'),
	Z('Z'),
	ENTER('0'),
	SPACE(' '),
	CLEAR('2'),
	BACK('3');
	
	private char value;
	
	private KeyboardKey(char value)
	{
		this.value = value;
	}

	public char getValue() {
		return value;
	}
}